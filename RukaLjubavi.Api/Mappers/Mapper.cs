using AutoMapper;
using RukaLjubavi.Api.Contracts;
using RukaLjubavi.Api.Contracts.Dto;
using RukaLjubavi.Api.Contracts.Requests;
using RukaLjubavi.Api.Dto;
using RukaLjubavi.Api.Models;

namespace RukaLjubavi.Api.Mappers
{
    public class Mapper : Profile
    {
        public Mapper()
        {
            CreateMap<GradInsertRequest, Grad>();
            CreateMap<GradSearchRequest, Grad>();
            CreateMap<GradDto, Grad>().ReverseMap();

            CreateMap<DonacijaDto, Donacija>().ReverseMap();
            CreateMap<DonacijaInsertRequest, Donacija>();
            CreateMap<DonacijaSearchRequest, Donacija>();

            CreateMap<OcjenaDonacijeDto, OcjenaDonacije>().ReverseMap();
            CreateMap<OcjenaDonacijeInsertRequest, OcjenaDonacije>();
            CreateMap<OcjenaDonacijeSearchRequest, OcjenaDonacije>();

            CreateMap<DrzavaInsertRequest, Grad>();
            CreateMap<DrzavaSearchRequest, Grad>();
            CreateMap<DrzavaDto, Drzava>().ReverseMap();
            
            CreateMap<KategorijaInsertRequest, Kategorija>();
            CreateMap<KategorijaDto, Kategorija>().ReverseMap();

            CreateMap<NotifikacijaInsertRequest, Notifikacija>();
            CreateMap<NotifikacijaSearchRequest, Notifikacija>();
            CreateMap<NotifikacijaDto, Notifikacija>().ReverseMap();

            CreateMap<UserInsertRequest, Korisnik>();
            CreateMap<UserUpdateRequest, Korisnik>();
            CreateMap<Korisnik, UserDto>()
                .ForMember(x => x.MjestoPrebivalista, src => src.MapFrom(y => y.MjestoPrebivalista.Naziv))
                .ReverseMap();
            CreateMap<Korisnik, AuthenticatedUser>();
            CreateMap<Korisnik, AuthenticatedBenefiktor>();
            CreateMap<Korisnik, AuthenticatedDonator>();
            CreateMap<BenefiktorInsertRequest, Benefiktor>().ReverseMap();
            CreateMap<DonatorInsertRequest, Donator>().ReverseMap();
            CreateMap<BenefiktorUpdateRequest, Benefiktor>().ReverseMap();
            CreateMap<DonatorUpdateRequest, Donator>().ReverseMap();
            CreateMap<Donator, DonatorDto>()
                .ForMember(x => x.MjestoRodjenja, src => src.MapFrom(y => y.MjestoRodjenja.Naziv))
                .ForMember(x => x.TipKorisnika, src => src.MapFrom(y => y.Korisnik.TipKorisnika))
                .ForMember(x => x.MjestoPrebivalistaId, src => src.MapFrom(y => y.Korisnik.MjestoPrebivalistaId))
                .ForMember(x => x.MjestoPrebivalista, src => src.MapFrom(y => y.Korisnik.MjestoPrebivalista.Naziv))
                .ForMember(x => x.IsVerifikovan, src => src.MapFrom(y => y.Korisnik.IsVerifikovan))
                .ForMember(x => x.Adresa, src => src.MapFrom(y => y.Korisnik.Adresa))
                .ForMember(x => x.Telefon, src => src.MapFrom(y => y.Korisnik.Telefon))
                .ForMember(x => x.DatumRegistracije, src => src.MapFrom(y => y.Korisnik.DatumRegistracije))
                .ForMember(x => x.Email, src => src.MapFrom(y => y.Korisnik.Email))
                .ReverseMap();

            CreateMap<Benefiktor, BenefiktorDto>()
                .ForMember(x => x.TipKorisnika, src => src.MapFrom(y => y.Korisnik.TipKorisnika))
                .ForMember(x => x.MjestoPrebivalistaId, src => src.MapFrom(y => y.Korisnik.MjestoPrebivalistaId))
                .ForMember(x => x.MjestoPrebivalista, src => src.MapFrom(y => y.Korisnik.MjestoPrebivalista.Naziv))
                .ForMember(x => x.IsVerifikovan, src => src.MapFrom(y => y.Korisnik.IsVerifikovan))
                .ForMember(x => x.Adresa, src => src.MapFrom(y => y.Korisnik.Adresa))
                .ForMember(x => x.Telefon, src => src.MapFrom(y => y.Korisnik.Telefon))
                .ForMember(x => x.DatumRegistracije, src => src.MapFrom(y => y.Korisnik.DatumRegistracije))
                .ForMember(x => x.Email, src => src.MapFrom(y => y.Korisnik.Email))
                .ReverseMap();


        }
    }
}
