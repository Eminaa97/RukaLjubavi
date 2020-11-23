using AutoMapper;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Options;
using Microsoft.IdentityModel.Tokens;
using RukaLjubavi.Api.Configuration;
using RukaLjubavi.Api.Contracts;
using RukaLjubavi.Api.Contracts.Requests;
using RukaLjubavi.Api.Database;
using RukaLjubavi.Api.Dto;
using RukaLjubavi.Api.Models;
using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Security.Cryptography;
using System.Text;

namespace RukaLjubavi.Api.Services
{
    public class KorisnikService : IKorisnikService
    {
        protected readonly RukaLjubaviDbContext _context;
        private readonly IMapper _mapper;
        private readonly IOptions<AppSettings> _options;
        private readonly IEmailService _emailService;

        public KorisnikService(
            RukaLjubaviDbContext context,
            IMapper mapper,
            IOptions<AppSettings> options
            )
        {
            _context = context;
            _mapper = mapper;
            _options = options;
        }

        public UserDto Get(int id)
        {
            var entity = _context.Korisnici.FirstOrDefault(x => x.Id == id);
            var returns = _mapper.Map<UserDto>(entity);

            returns.KorisnikId = id;

            if (entity.TipKorisnika == TipKorisnika.Benefiktor)
            {
                var benefiktor = _context.Benefiktori.FirstOrDefault(x => x.KorisnikId == id);
                returns.Id = benefiktor.Id;
                returns.BrojDonacija = _context.Donacije.Count(x => x.BenefiktorId == benefiktor.Id && x.StatusDonacije == StatusDonacije.Zavrsena);
                returns.OcjenaPovjerljivost = (float)_context.OcjeneDonacija.Where(x => x.KorisnikId != benefiktor.KorisnikId).Average(x => x.Povjerljivost);
                returns.OcjenaBrzinaDostavljanja = (float)_context.OcjeneDonacija.Where(x => x.KorisnikId != benefiktor.KorisnikId).Average(x => x.BrzinaDostavljanja);
                returns.OcjenaPostivanjeDogovora = (float)_context.OcjeneDonacija.Where(x => x.KorisnikId != benefiktor.KorisnikId).Average(x => x.PostivanjeDogovora);
            }
            else
            {
                var donator = _context.Donatori.FirstOrDefault(x => x.KorisnikId == id);
                returns.Id = donator.Id;
                returns.BrojDonacija = _context.Donacije.Count(x => x.DonatorId == donator.Id && x.StatusDonacije == StatusDonacije.Zavrsena);
                returns.OcjenaPovjerljivost = (float)_context.OcjeneDonacija.Where(x => x.KorisnikId != donator.KorisnikId).Average(x => x.Povjerljivost);
                returns.OcjenaBrzinaDostavljanja = (float)_context.OcjeneDonacija.Where(x => x.KorisnikId != donator.KorisnikId).Average(x => x.BrzinaDostavljanja);
                returns.OcjenaPostivanjeDogovora = (float)_context.OcjeneDonacija.Where(x => x.KorisnikId != donator.KorisnikId).Average(x => x.PostivanjeDogovora);
            }

            return returns;
        }

        public DonatorDto GetDonator(int donatorId)
        {
            var donator = _context.Donatori
                .Include(x => x.Korisnik)
                .ThenInclude(x => x.MjestoPrebivalista)
                .FirstOrDefault(x => x.Id == donatorId);

            var returns = _mapper.Map<DonatorDto>(donator);

            returns.BrojDonacija = _context.Donacije.Count(x => x.DonatorId == donatorId && x.StatusDonacije == StatusDonacije.Zavrsena);
            returns.OcjenaPovjerljivost = (float)_context.OcjeneDonacija.Where(x => x.KorisnikId != donator.KorisnikId).Average(x => x.Povjerljivost);
            returns.OcjenaBrzinaDostavljanja = (float)_context.OcjeneDonacija.Where(x => x.KorisnikId != donator.KorisnikId).Average(x => x.BrzinaDostavljanja);
            returns.OcjenaPostivanjeDogovora = (float)_context.OcjeneDonacija.Where(x => x.KorisnikId != donator.KorisnikId).Average(x => x.PostivanjeDogovora);

            return returns;
        }

        public BenefiktorDto GetBenefiktor(int benefiktorId)
        {
            var benefiktor = _context.Benefiktori
                .Include(x => x.Korisnik)
                .ThenInclude(x => x.MjestoPrebivalista)
                .FirstOrDefault(x => x.Id == benefiktorId);

            var returns = _mapper.Map<BenefiktorDto>(benefiktor);

            returns.BrojDonacija = _context.Donacije.Count(x => x.BenefiktorId == benefiktorId && x.StatusDonacije == StatusDonacije.Zavrsena);
            returns.OcjenaPovjerljivost = (float)_context.OcjeneDonacija.Where(x => x.KorisnikId != benefiktor.KorisnikId).Average(x => x.Povjerljivost);
            returns.OcjenaBrzinaDostavljanja = (float)_context.OcjeneDonacija.Where(x => x.KorisnikId != benefiktor.KorisnikId).Average(x => x.BrzinaDostavljanja);
            returns.OcjenaPostivanjeDogovora = (float)_context.OcjeneDonacija.Where(x => x.KorisnikId != benefiktor.KorisnikId).Average(x => x.PostivanjeDogovora);

            return returns;
        }

        public IList<UserDto> Get(UserSearchRequest search)
        {
            var query = _context.Korisnici
                .Include(x => x.MjestoPrebivalista)
                .AsQueryable();

            if (search == null)
            {
                return _mapper.Map<IList<UserDto>>(query.ToList());
            }

            if (search.Id.HasValue)
            {
                query = query.Where(x => x.Id == search.Id.Value);
            }

            if (!string.IsNullOrWhiteSpace(search?.Email))
            {
                query = query.Where(x => x.Email == search.Email);
            }

            if (search.IsVerifikovan.HasValue)
            {
                query = query.Where(x => x.IsVerifikovan == search.IsVerifikovan.Value);
            }

            if (!string.IsNullOrEmpty(search.MjestoPrebivalista) && !string.IsNullOrWhiteSpace(search.MjestoPrebivalista))
            {
                query = query.Where(x => x.MjestoPrebivalista.Naziv.Contains(search.MjestoPrebivalista));
            }

            if (search.MjestoPrebivalistaId.HasValue)
            {
                query = query.Where(x => x.MjestoPrebivalistaId == search.MjestoPrebivalistaId);
            }

            var entities = query.ToList();

            var result = _mapper.Map<IList<UserDto>>(entities.Where(x => 1 == 1));

            foreach (var item in result)
            {
                item.KorisnikId = item.Id;

                if (item.TipKorisnika == TipKorisnika.Benefiktor)
                {
                    var benefiktor = _context.Benefiktori.FirstOrDefault(x => x.KorisnikId == item.Id);
                    item.Id = benefiktor.Id;
                }
                else
                {
                    var donator = _context.Donatori.FirstOrDefault(x => x.KorisnikId == item.Id);
                    item.Id = donator.Id;
                }
            }

            return result;
        }

        public UserDto Insert(UserInsertRequest request)
        {
            var entity = _mapper.Map<Korisnik>(request);

            if (request.Password != request.ConfirmPassword)
            {
                throw new Exception("Password and confirm password do not match");
            }

            entity.LozinkaSalt = GenerateSalt();
            entity.LozinkaHash = GenerateHash(entity.LozinkaSalt, request.Password);
            entity.TipKorisnika = request is DonatorInsertRequest ? TipKorisnika.Donator : TipKorisnika.Benefiktor;
            entity.DatumRegistracije = DateTime.UtcNow;

            _context.Add(entity);

            if (request is DonatorInsertRequest dir)
            {
                var req = _mapper.Map<Donator>(dir);
                req.Korisnik = entity;
                _context.Add(req);

                foreach (var item in dir.Kategorije)
                {
                    _context.DonatorKategorije.Add(new DonatorKategorija
                    {
                        KategorijaId = item,
                        Donator = req,
                        isPotrebnaKategorija = true
                    });
                }
            }
            else if (request is BenefiktorInsertRequest bir)
            {
                var req = _mapper.Map<Benefiktor>(bir);
                req.Korisnik = entity;
                _context.Add(req);

                foreach (var item in bir.Kategorije)
                {
                    _context.BenefiktorKategorije.Add(new BenefiktorKategorija
                    {
                        KategorijaId = item,
                        Benefiktor = req,
                        isPotrebnaKategorija = true
                    });
                }

                // exception for testing purpose, functionality will be added
                var pdv = _context.PdvBroj.FirstOrDefault(x => x.PDVBroj == bir.Pdvbroj);
                if (pdv == null)
                    throw new Exception("Wrong PDV number");
            }

            _context.SaveChanges();
            //_emailService.Send(entity.Email, "Successfully registrated!", @$"Postovani,
            //Uspjesno ste se registrovali kao {entity.TipKorisnika}. 
            //Hvala vam na ukazanom povjerenju.
            //Vasa Ruka Ljubavi");
            return GetRegistrationOrUpdateUser(entity.Id);
        }

        public UserDto Update(int id, UserUpdateRequest request)
        {
            var entity = _context.Korisnici.Find(id);
            _mapper.Map(request, entity);

            if (request is DonatorUpdateRequest dir)
            {
                var donator = _context.Donatori.FirstOrDefault(x => x.KorisnikId == dir.KorisnikId);
                donator.Ime = dir.Ime;
                donator.Prezime = dir.Prezime;
                _context.Update(donator);
            }
            else if (request is BenefiktorUpdateRequest bir)
            {
                var benefiktor = _context.Benefiktori.FirstOrDefault(x => x.KorisnikId == bir.KorisnikId);
                benefiktor.NazivKompanije = bir.NazivKompanije;
                _context.Update(benefiktor);
            }

            _context.SaveChanges();

            return GetRegistrationOrUpdateUser(id);
        }

        public AuthenticatedUser Login(UserLoginRequest request)
        {
            var users = _context.Korisnici.ToList();
            var entity = _context.Korisnici.FirstOrDefault(x => x.Email == request.Email);

            #region User existence check
            if (entity == null)
            {
                throw new ArgumentNullException("Wrong username or password");
            }

            var hash = GenerateHash(entity.LozinkaSalt, request.Password);

            if (hash != entity.LozinkaHash)
            {
                throw new Exception("Wrong password");
            }
            #endregion

            #region Token
            var tokenHandler = new JwtSecurityTokenHandler();
            var key = Encoding.ASCII.GetBytes(_options.Value.Secret);
            var tokenDescriptor = new SecurityTokenDescriptor
            {
                Subject = new ClaimsIdentity(new Claim[]
                {
                    new Claim(ClaimTypes.NameIdentifier, entity.Id.ToString()),
                    new Claim("user_type", entity.TipKorisnika.ToString()),
                    new Claim(ClaimTypes.Email, entity.Email),
                }),
                Expires = DateTime.UtcNow.AddDays(7),
                SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(key), SecurityAlgorithms.HmacSha256)
            };
            var token = tokenHandler.CreateToken(tokenDescriptor);
            #endregion


            if (entity.TipKorisnika == TipKorisnika.Benefiktor)
            {
                var user = _mapper.Map<AuthenticatedBenefiktor>(entity);
                user.Token = tokenHandler.WriteToken(token);

                var benefiktor = _context.Benefiktori.FirstOrDefault(x => x.KorisnikId == user.Id);

                user.BenefiktorId = benefiktor.Id;
                user.NazivKompanije = benefiktor.NazivKompanije;
                user.Pdvbroj = benefiktor.PDVbroj;

                return user;
            }
            else
            {
                var user = _mapper.Map<AuthenticatedDonator>(entity);
                user.Token = tokenHandler.WriteToken(token);

                var donator = _context.Donatori.FirstOrDefault(x => x.KorisnikId == user.Id);

                user.DonatorId = donator.Id;
                user.Jmbg = donator.Jmbg;
                user.Ime = donator.Ime;
                user.Prezime = donator.Prezime;

                return user;
            }
        }

        #region Authentication
        private string GenerateSalt()
        {
            var buf = new byte[16];
            new RNGCryptoServiceProvider().GetBytes(buf);
            return Convert.ToBase64String(buf);
        }
        private string GenerateHash(string salt, string password)
        {
            byte[] src = Convert.FromBase64String(salt);
            byte[] bytes = Encoding.Unicode.GetBytes(password);
            byte[] dst = new byte[src.Length + bytes.Length];

            Buffer.BlockCopy(src, 0, dst, 0, src.Length);
            Buffer.BlockCopy(bytes, 0, dst, src.Length, bytes.Length);

            HashAlgorithm algorithm = HashAlgorithm.Create("SHA1");
            byte[] inArray = algorithm.ComputeHash(dst);
            return Convert.ToBase64String(inArray);
        }
        #endregion

        private UserDto GetRegistrationOrUpdateUser(int id)
        {
            var lastEntity = _context.Korisnici
               .Include(x => x.MjestoPrebivalista)
               .FirstOrDefault(x => x.Id == id);

            var returns = _mapper.Map<UserDto>(lastEntity);
            returns.KorisnikId = returns.Id;

            if (returns.TipKorisnika == TipKorisnika.Benefiktor)
            {
                var benefiktor = _context.Benefiktori.FirstOrDefault(x => x.KorisnikId == returns.KorisnikId);
                returns.Id = benefiktor.Id;
            }
            else
            {
                var donator = _context.Donatori.FirstOrDefault(x => x.KorisnikId == returns.KorisnikId);
                returns.Id = donator.Id;
            }

            return returns;
        }

        public IList<DonatorDto> GetDonatori()
        {
            var query = _context.Donatori
                .Include(x => x.Korisnik)
                .ThenInclude(x => x.MjestoPrebivalista);

            var returns = _mapper.Map<List<DonatorDto>>(query);

            foreach (var item in returns)
            {
                item.BrojDonacija = _context.Donacije.Count(x => x.DonatorId == item.Id && x.StatusDonacije == StatusDonacije.Zavrsena);
                item.OcjenaPovjerljivost = (float)_context.OcjeneDonacija.Where(x => x.KorisnikId != item.KorisnikId).Average(x => x.Povjerljivost);
                item.OcjenaBrzinaDostavljanja = (float)_context.OcjeneDonacija.Where(x => x.KorisnikId != item.KorisnikId).Average(x => x.BrzinaDostavljanja);
                item.OcjenaPostivanjeDogovora = (float)_context.OcjeneDonacija.Where(x => x.KorisnikId != item.KorisnikId).Average(x => x.PostivanjeDogovora);
            }

            return returns;
        }

        public IList<BenefiktorDto> GetBenefiktori(BenefiktorSearchRequest searchRequest)
        {
            var query = _context.Benefiktori
                .Include(x => x.Korisnik)
                .ThenInclude(x => x.MjestoPrebivalista).AsQueryable();

            if (searchRequest.LokacijaId.HasValue)
            {
                query = query.Where(x => x.Korisnik.MjestoPrebivalistaId == searchRequest.LokacijaId);
            }

            if (!string.IsNullOrWhiteSpace(searchRequest?.nazivKompanije))
            {
                query = query.Where(x => x.NazivKompanije.ToLower().StartsWith(searchRequest.nazivKompanije.ToLower()));
            }

            var returns = _mapper.Map<List<BenefiktorDto>>(query);

            foreach (var item in returns)
            {
                item.BrojDonacija = _context.Donacije.Count(x => x.BenefiktorId == item.Id && x.StatusDonacije == StatusDonacije.Zavrsena);
                item.OcjenaPovjerljivost = (float)_context.OcjeneDonacija.Where(x => x.KorisnikId != item.KorisnikId).Average(x => x.Povjerljivost);
                item.OcjenaBrzinaDostavljanja = (float)_context.OcjeneDonacija.Where(x => x.KorisnikId != item.KorisnikId).Average(x => x.BrzinaDostavljanja);
                item.OcjenaPostivanjeDogovora = (float)_context.OcjeneDonacija.Where(x => x.KorisnikId != item.KorisnikId).Average(x => x.PostivanjeDogovora);
            }

            return returns;
        }

        public bool ResetPasword(int userId, PasswordResetRequest request)
        {
            if (request.NewPassword != request.NewPasswordConfirm)
            {
                return false;
            }

            var entity = _context.Korisnici.FirstOrDefault(a => a.Id == userId);
            if (entity == null)
            {
                return false;
            }

            var hash = GenerateHash(entity.LozinkaSalt, request.OldPassword);
            if (hash != entity.LozinkaHash)
            {
                return false;
            }

            var newHash = GenerateHash(entity.LozinkaSalt, request.NewPassword);
            entity.LozinkaHash = newHash;

            _context.Korisnici.Update(entity);
            _context.SaveChanges();

            return true;
        }

        public void UpdateCategories(int userId, CategoryUpdateRequest request)
        {
            var entity = _context.Korisnici.FirstOrDefault(a => a.Id == userId);

            if (entity.TipKorisnika == TipKorisnika.Donator)
            {
                var donator = _context.Donatori.FirstOrDefault(x => x.KorisnikId == entity.Id);

                var kategorije = _context.DonatorKategorije.Where(a => a.DonatorId == donator.Id).ToList();

                foreach (var item in kategorije)
                {
                    if (!request.Kategorije.Any(a => a == item.KategorijaId))
                    {
                        item.isPotrebnaKategorija = false;
                    }
                }
                foreach (var item in request.Kategorije)
                {
                    if (!_context.DonatorKategorije.Any(a => a.KategorijaId == item && a.DonatorId == donator.Id))
                    {
                        _context.DonatorKategorije.Add(new DonatorKategorija
                        {
                            KategorijaId = item,
                            Donator = donator,
                            isPotrebnaKategorija = true
                        });
                    }
                }
            }
            else
            {
                var benefiktor = _context.Benefiktori.FirstOrDefault(x => x.KorisnikId == entity.Id);

                var kategorije = _context.BenefiktorKategorije.Where(a => a.BenefiktorId == benefiktor.Id).ToList();

                foreach (var item in kategorije)
                {
                    if (!request.Kategorije.Any(a => a == item.KategorijaId))
                    {
                        item.isPotrebnaKategorija = false;
                    }
                }

                foreach (var item in request.Kategorije)
                {
                    if (!_context.BenefiktorKategorije.Any(a => a.KategorijaId == item && a.BenefiktorId == benefiktor.Id))
                    {
                        _context.BenefiktorKategorije.Add(new BenefiktorKategorija
                        {
                            KategorijaId = item,
                            Benefiktor = benefiktor,
                            isPotrebnaKategorija = true
                        });
                    }
                }
            }
            _context.SaveChanges();
        }
    }
}
