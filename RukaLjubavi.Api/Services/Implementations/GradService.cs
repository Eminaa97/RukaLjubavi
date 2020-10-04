using AutoMapper;
using RukaLjubavi.Api.Contracts.Requests;
using RukaLjubavi.Api.Database;
using RukaLjubavi.Api.Dto;
using RukaLjubavi.Api.Models;

namespace RukaLjubavi.Api.Services
{
    public class GradService : BaseDataService<Grad, GradDto, GradInsertRequest>, IGradService
    {
        public GradService(IMapper _mapper, RukaLjubaviDbContext _context) : base(_context, _mapper)
        {

        }
    }
}
