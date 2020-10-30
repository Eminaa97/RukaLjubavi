using AutoMapper;
using Microsoft.Extensions.Options;
using RukaLjubavi.Api.Configuration;
using RukaLjubavi.Api.Contracts.Dto;
using RukaLjubavi.Api.Contracts.Requests;
using RukaLjubavi.Api.Database;
using RukaLjubavi.Api.Models;
using System;
using System.Collections.Generic;
using System.Linq;

namespace RukaLjubavi.Api.Services.Implementations
{
    public class KategorijaService : IKategorijaService
    {
        protected readonly RukaLjubaviDbContext _context;
        private readonly IMapper _mapper;

        public KategorijaService(
            RukaLjubaviDbContext context,
            IMapper mapper
            )
        {
            _context = context;
            _mapper = mapper;
        }

        public IList<KategorijaDto> Get(KategorijaSearchRequest search)
        {
            if (search.DonatorId.HasValue)
            {
                var q = _context.DonatorKategorije.AsQueryable();

                q = q.Where(x => x.DonatorId == search.DonatorId);
                q = q.Where(x => x.isPotrebnaKategorija == true);

                return _mapper.Map<IList<KategorijaDto>>(q.Select(x => x.Kategorija));
            }
            else if (search.BenefiktorId.HasValue)
            {
                var q = _context.BenefiktorKategorije.AsQueryable();

                q = q.Where(x => x.BenefiktorId == search.BenefiktorId);
                q = q.Where(x => x.isPotrebnaKategorija == true);

                return _mapper.Map<IList<KategorijaDto>>(q.Select(x => x.Kategorija));
            }
            else
            {
                var q = _context.Kategorije.AsQueryable();

                return _mapper.Map<IList<KategorijaDto>>(q);
            }
        }

        public KategorijaDto Get(int id)
        {
            var entity = _context.Kategorije
                .FirstOrDefault(x => x.Id == id);

            return _mapper.Map<KategorijaDto>(entity);
        }

        public KategorijaDto Insert(KategorijaInsertRequest user)
        {
            if (user == null)
            {
                throw new ArgumentNullException("Entity");
            }

            _context.Kategorije.Add(user);
            _context.SaveChanges();
            return _mapper.Map<KategorijaDto>(user);
        }
    }
}
