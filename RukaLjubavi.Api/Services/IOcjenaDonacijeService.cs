using RukaLjubavi.Api.Contracts.Dto;
using RukaLjubavi.Api.Contracts.Requests;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RukaLjubavi.Api.Services
{
    public interface IOcjenaDonacijeService
    {
        public IList<OcjenaDonacijeDto> Get(OcjenaDonacijeSearchRequest search);
        public OcjenaDonacijeDto Get(int id);
        public OcjenaDonacijeDto Insert(OcjenaDonacijeInsertRequest user);
        public OcjenaDonacijeDto Update(int id, OcjenaDonacijeInsertRequest user);
    }
}
