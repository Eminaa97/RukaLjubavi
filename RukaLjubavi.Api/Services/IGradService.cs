using RukaLjubavi.Api.Contracts.Requests;
using RukaLjubavi.Api.Dto;
using RukaLjubavi.Api.Models;

namespace RukaLjubavi.Api.Services
{
    public interface IGradService : IBaseDataService<Grad, GradDto, GradInsertRequest>
    {
    }
}
