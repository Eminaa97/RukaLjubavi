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

            CreateMap<DrzavaInsertRequest, Grad>();
            CreateMap<DrzavaSearchRequest, Grad>();
            CreateMap<DrzavaDto, Drzava>().ReverseMap();

            //CreateMap<UserInsertRequest, Korisnik>();
            //CreateMap<UserUpdateRequest, Korisnik>();
            CreateMap<Korisnik, UserDto>().ReverseMap();
            CreateMap<Korisnik, AuthenticatedUser>();
        }
    }
}
