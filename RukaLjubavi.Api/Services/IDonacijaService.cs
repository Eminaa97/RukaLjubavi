using RukaLjubavi.Api.Contracts.Dto;
using RukaLjubavi.Api.Contracts.Requests;
using RukaLjubavi.Api.Dto;
using RukaLjubavi.Api.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RukaLjubavi.Api.Services
{
    public interface IDonacijaService
    {
        IList<DonacijaDto> Get(DonacijaSearchRequest search);
        DonacijaDto Get(int id);
        DonacijaDto Insert(DonacijaInsertRequest user);
        DonacijaDto Prihvati(int id);
    }
}
