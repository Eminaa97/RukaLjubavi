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
                q = q.Where(x => x.BenefiktorId == null);
            }
            if (search.isZahtjevZaDonatora != null)
            {
                q = q.Where(x => x.DonatorId == null);
            }
            if (search.isZahtjevZaBenefiktora != null)
            {
                q = q.Where(x => x.BenefiktorId == null);
            }

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
            _context.Donacije.Add(entity);
            _context.SaveChanges();

            return _mapper.Map<DonacijaDto>(entity);
        }

        public DonacijaDto Prihvati(int id)
        {
            var entity = _context.Donacije.FirstOrDefault(x => x.Id == id);
            entity.StatusDonacije = StatusDonacije.Prihvacena;
            _context.SaveChanges();

            return _mapper.Map<DonacijaDto>(entity);
        }
    }
}
