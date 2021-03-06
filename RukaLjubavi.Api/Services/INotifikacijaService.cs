using RukaLjubavi.Api.Contracts.Dto;
using RukaLjubavi.Api.Contracts.Requests;
using RukaLjubavi.Api.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RukaLjubavi.Api.Services
{
    public interface INotifikacijaService : IBaseDataService <Notifikacija, NotifikacijaDto, NotifikacijaInsertRequest>
    {
        void Update(NotifikacijaUpdateRequest request);
    }
}
