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

            CreateMap<Donacija, DonacijaDto>()
                .ForMember(x => x.DonatorId, src => src.MapFrom(y => y.Donator.Id))
                .ForMember(x => x.DonatorIme, src => src.MapFrom(y => y.Donator.Ime))
                .ForMember(x => x.DonatorPrezime, src => src.MapFrom(y => y.Donator.Prezime))
                .ForMember(x => x.DonatorJmbg, src => src.MapFrom(y => y.Donator.Jmbg))
                .ForMember(x => x.DonatorDatumRodjenja, src => src.MapFrom(y => y.Donator.DatumRodjenja))
                .ForMember(x => x.DonatorMjestoRodjenja, src => src.MapFrom(y => y.Donator.MjestoRodjenja.Naziv))
                .ForMember(x => x.BenefiktorId, src => src.MapFrom(y => y.BenefiktorKategorije.Benefiktor.Id))
                .ForMember(x => x.BenefiktorNazivKompanije, src => src.MapFrom(y => y.BenefiktorKategorije.Benefiktor.NazivKompanije))
                .ForMember(x => x.BenefiktorPdvbroj, src => src.MapFrom(y => y.BenefiktorKategorije.Benefiktor.Pdvbroj))
                .ForMember(x => x.BenefiktorLokacija, src => src.MapFrom(y => y.BenefiktorKategorije.Benefiktor.Korisnik.MjestoPrebivalista.Naziv))
                .ForMember(x => x.NazivKategorije, src => src.MapFrom(y => y.NazivKategorije))
                .ForMember(x => x.Status, src => src.MapFrom(y => y.StatusDonacije.ToString()))
                .ReverseMap();
            CreateMap<DonacijaInsertRequest, Donacija>();
            CreateMap<DonacijaSearchRequest, Donacija>();

            CreateMap<OcjenaDonacije, OcjenaDonacijeDto>()
                .ForMember(x => x.DonatorIme, src => src.MapFrom(y => y.Donacija.Donator.Ime))
                .ForMember(x => x.Kolicina, src => src.MapFrom(y => y.Donacija.Kolicina))
                .ForMember(x => x.IsPrihvacena, src => src.MapFrom(y => y.Donacija.StatusDonacije))
                .ForMember(x => x.DonatorPrezime, src => src.MapFrom(y => y.Donacija.Donator.Prezime))
                .ForMember(x => x.DonatorJmbg, src => src.MapFrom(y => y.Donacija.Donator.Jmbg))
                .ForMember(x => x.DonatorDatumRodjenja, src => src.MapFrom(y => y.Donacija.Donator.DatumRodjenja))
                .ForMember(x => x.DonatorMjestoRodjenja, src => src.MapFrom(y => y.Donacija.Donator.MjestoRodjenja.Naziv))
                .ForMember(x => x.BenefiktorNazivKompanije, src => src.MapFrom(y => y.Donacija.BenefiktorKategorije.Benefiktor.NazivKompanije))
                .ForMember(x => x.BenefiktorPdvbroj, src => src.MapFrom(y => y.Donacija.BenefiktorKategorije.Benefiktor.Pdvbroj))
                .ForMember(x => x.NazivKategorije, src => src.MapFrom(y => y.Donacija.BenefiktorKategorije.Kategorija.Naziv))
                .ReverseMap();
            CreateMap<OcjenaDonacije, OcjenaDonacijeInsertRequest>()
                .ForMember(x => x.OcjenjivacTipKorisnika, src => src.MapFrom(y => y.Ocjenjivac))
                .ReverseMap();
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
