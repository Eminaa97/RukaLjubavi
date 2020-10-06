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
        public IList<DonacijaDto> Get(DonacijaSearchRequest search);
        public DonacijaDto Get(int id);
        public DonacijaDto Insert(DonacijaInsertRequest user);
        public DonacijaDto Prihvati(int id);
    }
}
