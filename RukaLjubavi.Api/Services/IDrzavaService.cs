using RukaLjubavi.Api.Contracts.Dto;
using RukaLjubavi.Api.Contracts.Requests;
using RukaLjubavi.Api.Models;

namespace RukaLjubavi.Api.Services
{
    public interface IDrzavaService : IBaseDataService<Drzava, DrzavaDto, DrzavaInsertRequest>
    {
    }
}
