using AutoMapper;
using RukaLjubavi.Api.Contracts.Dto;
using RukaLjubavi.Api.Contracts.Requests;
using RukaLjubavi.Api.Database;
using RukaLjubavi.Api.Models;

namespace RukaLjubavi.Api.Services.Implementations
{
    public class KategorijaService : BaseDataService<Kategorija, KategorijaDto,KategorijaInsertRequest>, IKategorijaService
    {
        public KategorijaService(IMapper _mapper, RukaLjubaviDbContext _context) : base(_context, _mapper)
        {

        }
    }
}
