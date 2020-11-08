using AutoMapper;
using Microsoft.AspNetCore.SignalR;
using Microsoft.EntityFrameworkCore;
using RukaLjubavi.Api.Contracts.Dto;
using RukaLjubavi.Api.Contracts.Requests;
using RukaLjubavi.Api.Database;
using RukaLjubavi.Api.Hubs;
using RukaLjubavi.Api.Models;
using System;
using System.Collections.Generic;
using System.Linq;

namespace RukaLjubavi.Api.Services.Implementations
{
    public class OcjenaDonacijeService : IOcjenaDonacijeService
    {
        protected readonly RukaLjubaviDbContext _context;
        private readonly IMapper _mapper;
        private readonly IHubContext<NotificationHub> _hubContext;

        public OcjenaDonacijeService(
            RukaLjubaviDbContext context,
            IMapper mapper,
            IHubContext<NotificationHub> hubContext
            )
        {
            _context = context;
            _mapper = mapper;
            _hubContext = hubContext;
        }

        public IList<OcjenaDonacijeDto> Get(OcjenaDonacijeSearchRequest search)
        {
            var q = _context.OcjeneDonacija
                .Include(x => x.Donacija)
                .Include(x => x.Donacija.Donator)
                .Include(x => x.Donacija.Donator.MjestoRodjenja)
                .Include(x => x.Donacija.BenefiktorKategorije.Kategorija)
                .Include(x => x.Donacija.BenefiktorKategorije.Benefiktor)
                .AsQueryable();

            if (search.DonacijaId != null)
            {
                q = q.Where(x => x.DonacijaId == search.DonacijaId);
            }
            if (search.KorisnikId != null && search.isVasaDonacija == true)
            {
                q = q.Where(x => x.KorisnikId == search.KorisnikId);
            }
            if (search.isVasaDonacija == false)
            {
                q = q.Where(x => x.KorisnikId != search.KorisnikId);
            }

            return _mapper.Map<IList<OcjenaDonacijeDto>>(q);
        }

        public OcjenaDonacijeDto Get(int id)
        {
            var entity = _context.OcjeneDonacija
                .Include(x => x.Donacija)
                .Include(x => x.Donacija.Donator)
                .Include(x => x.Donacija.Donator.MjestoRodjenja)
                .Include(x => x.Donacija.BenefiktorKategorije.Kategorija)
                .Include(x => x.Donacija.BenefiktorKategorije.Benefiktor)
                .FirstOrDefault(x => x.Id == id);

            return _mapper.Map<OcjenaDonacijeDto>(entity);
        }

        public OcjenaDonacijeDto Insert(OcjenaDonacijeInsertRequest request)
        {
            var donacija = _context.Donacije.Where(a => a.Id == request.DonacijaId)
                .Include(a => a.Donator)
                .Include(a => a.BenefiktorKategorije)
                .ThenInclude(a => a.Benefiktor)
                .FirstOrDefault();
            var entity = _mapper.Map<OcjenaDonacije>(request);

            _context.OcjeneDonacija.Add(entity);
            _context.SaveChanges();

            var newNotificationD = new Notifikacija
            {
                KorisnikId = donacija.Donator.KorisnikId,
                DatumSlanja = DateTime.UtcNow,
                Sadrzaj = @$"Donacija {entity.Donacija.Opis} ocijenjena sa 
Povjerljivost: {entity.Povjerljivost},
Postivanje dogovora: {entity.PostivanjeDogovora},
Brzina dostavljanja: {entity.BrzinaDostavljanja}"
            };
            _context.Add(newNotificationD);

            var newNotificationB = new Notifikacija
            {
                KorisnikId = donacija.BenefiktorKategorije.Benefiktor.KorisnikId,
                DatumSlanja = DateTime.UtcNow,
                Sadrzaj = @$"Donacija {entity.Donacija.Opis} ocijenjena sa 
Povjerljivost: {entity.Povjerljivost},
Postivanje dogovora: {entity.PostivanjeDogovora},
Brzina dostavljanja: {entity.BrzinaDostavljanja}"
            };
            _context.Add(newNotificationB);
            _context.SaveChanges();

            lock (NotificationHub.ConnectedUsers)
            {
                if (NotificationHub.ConnectedUsers.ContainsKey(donacija.Donator.KorisnikId.ToString()))
                {
                    var donatorConnection = NotificationHub.ConnectedUsers[donacija.Donator.KorisnikId.ToString()];
                    _hubContext.Clients.Client(donatorConnection).SendAsync("OnNotificationReceived", newNotificationD);
                }

                if (NotificationHub.ConnectedUsers.ContainsKey(donacija.BenefiktorKategorije.Benefiktor.KorisnikId.ToString()))
                {
                    var benefictorConnection = NotificationHub.ConnectedUsers[donacija.BenefiktorKategorije.Benefiktor.KorisnikId.ToString()];
                    _hubContext.Clients.Client(benefictorConnection).SendAsync("OnNotificationReceived", newNotificationB);
                }
            }

            return _mapper.Map<OcjenaDonacijeDto>(entity);
        }

        public OcjenaDonacijeDto Update(int id, OcjenaDonacijeInsertRequest request)
        {
            var entity = _context.OcjeneDonacija.Find(id);
            _mapper.Map(request, entity);

            _context.SaveChanges();

            return _mapper.Map<OcjenaDonacijeDto>(entity);
        }
    }
}
