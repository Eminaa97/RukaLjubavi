using AutoMapper;
using RukaLjubavi.Api.Contracts.Dto;
using RukaLjubavi.Api.Contracts.Requests;
using RukaLjubavi.Api.Database;
using RukaLjubavi.Api.Models;
using System;
using System.Linq;

namespace RukaLjubavi.Api.Services.Implementations
{
    public class NotifikacijaService : BaseDataService<Notifikacija, NotifikacijaDto, NotifikacijaInsertRequest>, INotifikacijaService
    {
        public NotifikacijaService(IMapper _mapper, RukaLjubaviDbContext _context) : base(_context, _mapper)
        {
           
        }
        public void Update(NotifikacijaUpdateRequest request)
        {
            var notifikacija = _context.Notifikacije.FirstOrDefault(a => a.Id == request.Id);
            if (notifikacija != null && !notifikacija.DatumPregleda.HasValue)
            {
                notifikacija.DatumPregleda = DateTime.UtcNow;
                _context.Update(notifikacija);
                _context.SaveChanges();
            }
        }
    }
}
