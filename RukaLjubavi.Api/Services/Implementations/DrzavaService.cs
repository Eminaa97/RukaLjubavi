using AutoMapper;
using RukaLjubavi.Api.Contracts.Dto;
using RukaLjubavi.Api.Contracts.Requests;
using RukaLjubavi.Api.Database;
using RukaLjubavi.Api.Models;
using RukaLjubavi.Api.Services;

namespace RukaLjubavi.Api.Services.Implementations
{
    public class DrzavaService : BaseDataService<Drzava, DrzavaDto, DrzavaInsertRequest>, IDrzavaService
    {
        public DrzavaService(IMapper _mapper, RukaLjubaviDbContext _context) : base(_context, _mapper)
        {

        }
    }
}
