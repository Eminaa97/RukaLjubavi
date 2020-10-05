using RukaLjubavi.Api.Contracts;
using RukaLjubavi.Api.Contracts.Requests;
using RukaLjubavi.Api.Dto;
using System.Collections.Generic;

namespace RukaLjubavi.Api.Services
{
    public interface IKorisnikService
    {
        IList<UserDto> Get(UserSearchRequest search);
        UserDto Get(int id);
        DonatorDto GetDonator(int donatorId);
        BenefiktorDto GetBenefiktor(int benefiktorId);
        UserDto Insert(UserInsertRequest user);
        UserDto Update(int id, UserUpdateRequest user);
        AuthenticatedUser Login(UserLoginRequest user);
      
    }
}
