using AutoMapper;
using Microsoft.EntityFrameworkCore;
using RukaLjubavi.Api.Contracts.Dto;
using RukaLjubavi.Api.Contracts.Requests;
using RukaLjubavi.Api.Database;
using RukaLjubavi.Api.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RukaLjubavi.Api.Services.Implementations
{
    public class OcjenaDonacijeService : IOcjenaDonacijeService
    {
        protected readonly RukaLjubaviDbContext _context;
        private readonly IMapper _mapper;

        public OcjenaDonacijeService(
            RukaLjubaviDbContext context,
            IMapper mapper
            )
        {
            _context = context;
            _mapper = mapper;
        }

        public IList<OcjenaDonacijeDto> Get(OcjenaDonacijeSearchRequest search)
        {
            var q = _context.OcjeneDonacija
                .Include(x => x.Donacija)
                .Include(x => x.Donacija.Donator)
                .Include(x => x.Donacija.Donator.MjestoRodjenja)
                .Include(x => x.Donacija.BenefiktorKategorije.Kategorija)
                .Include(x => x.Donacija.BenefiktorKategorije.Benefiktor)
                .AsQueryable();

            if (search.DonacijaId != null)
            {
                q = q.Where(x => x.DonacijaId == search.DonacijaId);
            }
            if (search.KorisnikId != null && search.isVasaDonacija == true)
            {
                q = q.Where(x => x.KorisnikId == search.KorisnikId);
            }
            if (search.isVasaDonacija == false)
            {
                q = q.Where(x => x.KorisnikId != search.KorisnikId);
            }

            return _mapper.Map<IList<OcjenaDonacijeDto>>(q);
        }

        public OcjenaDonacijeDto Get(int id)
        {
            var entity = _context.OcjeneDonacija
                .Include(x => x.Donacija)
                .Include(x => x.Donacija.Donator)
                .Include(x => x.Donacija.Donator.MjestoRodjenja)
                .Include(x => x.Donacija.BenefiktorKategorije.Kategorija)
                .Include(x => x.Donacija.BenefiktorKategorije.Benefiktor)
                .FirstOrDefault(x => x.Id == id);

            return _mapper.Map<OcjenaDonacijeDto>(entity);
        }

        public OcjenaDonacijeDto Insert(OcjenaDonacijeInsertRequest request)
        {
            var entity = _mapper.Map<OcjenaDonacije>(request);

            _context.OcjeneDonacija.Add(entity);
            _context.SaveChanges();

            return _mapper.Map<OcjenaDonacijeDto>(entity);
        }

        public OcjenaDonacijeDto Update(int id, OcjenaDonacijeInsertRequest request)
        {
            var entity = _context.OcjeneDonacija.Find(id);
            _mapper.Map(request, entity);

            _context.SaveChanges();

            return _mapper.Map<OcjenaDonacijeDto>(entity);
        }
    }
}
