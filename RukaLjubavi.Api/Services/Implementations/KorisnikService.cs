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

            return _mapper.Map<UserDto>(entity);
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

            return result;
        }

        public UserDto Insert(UserInsertRequest request)
        {
            var entity = _mapper.Map<Korisnik>(request);
            _context.Add(entity);

            if (request.Password != request.ConfirmPassword)
            {
                throw new Exception("Password and confirm password do not match");
            }

            entity.LozinkaSalt = GenerateSalt();
            entity.LozinkaHash = GenerateHash(entity.LozinkaSalt, request.Password);
            _context.SaveChanges();

            return _mapper.Map<UserDto>(entity);
        }

        public UserDto Update(int id, UserUpdateRequest request)
        {
            var entity = _context.Korisnici.Find(id);

            _mapper.Map(request, entity);

            _context.SaveChanges();

            return _mapper.Map<UserDto>(entity);
        }

        public AuthenticatedUser Login(UserLoginRequest request)
        {
            var entity = _context.Korisnici.FirstOrDefault(x => x.Email == request.Email);

            #region User existence check
            if (entity == null)
            {
                throw new ArgumentNullException("Wrong username or password");
            }

            var hash = GenerateHash(entity.LozinkaSalt, request.Password);

            if (hash != entity.LozinkaHash)
            {
                throw new Exception("Wrong username or password");
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
                    new Claim(ClaimTypes.Name, entity.Email),
                }),
                Expires = DateTime.UtcNow.AddDays(7),
                SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(key), SecurityAlgorithms.HmacSha256)
            };
            var token = tokenHandler.CreateToken(tokenDescriptor);
            #endregion

            var user = _mapper.Map<AuthenticatedUser>(entity);
            user.Token = tokenHandler.WriteToken(token);
            return user;
        }
        

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

    }
}
