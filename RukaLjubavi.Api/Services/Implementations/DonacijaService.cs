using AutoMapper;
using Microsoft.EntityFrameworkCore;
using RukaLjubavi.Api.Contracts.Dto;
using RukaLjubavi.Api.Contracts.Requests;
using RukaLjubavi.Api.Database;
using RukaLjubavi.Api.Models;
using System;
using System.Collections.Generic;
using System.Linq;

namespace RukaLjubavi.Api.Services.Implementations
{
    public class DonacijaService : IDonacijaService
    {
        protected readonly RukaLjubaviDbContext _context;
        private readonly IMapper _mapper;

        public DonacijaService(
            RukaLjubaviDbContext context,
            IMapper mapper
            )
        {
            _context = context;
            _mapper = mapper;
        }

        public IList<DonacijaDto> Get(DonacijaSearchRequest search)
        {
            var q = _context.Donacije
                .Include(x => x.Donator.Korisnik)
                .Include(x => x.BenefiktorKategorije)
                .Include(x => x.Donator.MjestoRodjenja)
                .Include(x => x.BenefiktorKategorije.Benefiktor)
                .Include(x => x.BenefiktorKategorije.Kategorija)
                .Include(x => x.BenefiktorKategorije.Benefiktor.Korisnik)
                .Include(x => x.BenefiktorKategorije.Benefiktor.Korisnik.MjestoPrebivalista)
                .Include(x => x.Donator).AsQueryable();

            if (search.StatusDonacije.HasValue)
            {
                q = q.Where(x => x.StatusDonacije == search.StatusDonacije);
            }
            if (search.DonatorId.HasValue)
            {
                q = q.Where(x => x.DonatorId == search.DonatorId);
            }
            if (search.BenefiktorId.HasValue)
            {
                q = q.Where(x => x.BenefiktorId == search.BenefiktorId);
            }
            if (search.LokacijaBenefiktorId.HasValue)
            {
                q = q.Where(x => x.BenefiktorKategorije.Benefiktor.Korisnik.MjestoPrebivalistaId == search.LokacijaBenefiktorId);
            }
            if (search.LokacijaDonatorId.HasValue)
            {
                q = q.Where(x => x.Donator.Korisnik.MjestoPrebivalistaId == search.LokacijaDonatorId);
            }
            if (search.KategorijaId.HasValue)
            {
                q = q.Where(x => x.BenefiktorKategorije.KategorijaId == search.KategorijaId);
            }
            if (!string.IsNullOrWhiteSpace(search?.NazivKompanije))
            {
                q = q.Where(x => x.BenefiktorKategorije.Benefiktor.NazivKompanije.Contains(search.NazivKompanije));
            }
            if (search.isZahtjevZaDonatora != null)
            {
                q = q.Where(x => x.DonatorId == null);
            }
            if (search.isZahtjevZaBenefiktora != null)
            {
                q = q.Where(x => x.BenefiktorId == null);
            }
            q = q.OrderByDescending(x => x.Id);

            return _mapper.Map<IList<DonacijaDto>>(q);
        }

        public DonacijaDto Get(int id)
        {
            var entity = _context.Donacije
                .Include(x => x.BenefiktorKategorije)
                .Include(x => x.Donator.MjestoRodjenja)
                .Include(x => x.BenefiktorKategorije.Benefiktor)
                .Include(x => x.BenefiktorKategorije.Kategorija)
                .Include(x => x.Donator)
                .FirstOrDefault(x => x.Id == id);

            return _mapper.Map<DonacijaDto>(entity);
        }

        public DonacijaDto Insert(DonacijaInsertRequest donacija)
        {
            var entity = _mapper.Map<Donacija>(donacija);
            entity.DatumVrijeme = DateTime.UtcNow;
            entity.NazivKategorije = _context.Kategorije.Where(x => x.Id == donacija.KategorijaId).SingleOrDefault().Naziv;
            if(donacija.BenefiktorId != null && donacija.DonatorId != null)
            {
                entity.StatusDonacije = StatusDonacije.Na_cekanju;
            }
            else
            {
                entity.StatusDonacije = StatusDonacije.Aktivna;
            }
            _context.Donacije.Add(entity);
            _context.SaveChanges();

            return _mapper.Map<DonacijaDto>(entity);
        }

        public DonacijaDto Prihvati(int id, int userId)
        {
            var entity = _context.Donacije.FirstOrDefault(x => x.Id == id);
            if(entity.DonatorId == null)
            {
                entity.DonatorId = userId;
            }
            if (entity.BenefiktorId == null)
            {
                entity.BenefiktorId = userId;
            }
            entity.StatusDonacije = StatusDonacije.Prihvacena;
            _context.SaveChanges();

            return _mapper.Map<DonacijaDto>(entity);
        }

        public DonacijaDto PromjeniStatus(int id, StatusDonacije statusDonacije)
        {
            var entity = _context.Donacije.FirstOrDefault(x => x.Id == id);
            entity.StatusDonacije = statusDonacije;
            _context.SaveChanges();

            return _mapper.Map<DonacijaDto>(entity);
        }
    }
}
