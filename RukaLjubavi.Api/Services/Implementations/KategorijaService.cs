using AutoMapper;
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
    public class KategorijaService : BaseDataService<Kategorija, KategorijaDto,KategorijaInsertRequest>, IKategorijaService
    {
        public KategorijaService(IMapper _mapper, RukaLjubaviDbContext _context) : base(_context, _mapper)
        {

        }
    }
}
