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
                .AsQueryable();

            return _mapper.Map<IList<OcjenaDonacijeDto>>(q);
        }

        public OcjenaDonacijeDto Get(int id)
        {
            var entity = _context.OcjeneDonacija.FirstOrDefault(x => x.Id == id);

            return _mapper.Map<OcjenaDonacijeDto>(entity);
        }

        public OcjenaDonacijeDto Insert(OcjenaDonacijeInsertRequest request)
        {
            var entity = _mapper.Map<OcjenaDonacije>(request);

            _context.OcjeneDonacija.Add(entity);
            _context.SaveChanges();

            return _mapper.Map<OcjenaDonacijeDto>(entity);
        }

    }
}
