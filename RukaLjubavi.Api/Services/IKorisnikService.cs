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
        IList<DonatorDto> GetDonatori();
        BenefiktorDto GetBenefiktor(int benefiktorId);
        IList<BenefiktorDto> GetBenefiktori(BenefiktorSearchRequest searchRequest);
        UserDto Insert(UserInsertRequest user);
        UserDto Update(int id, UserUpdateRequest user);
        bool ResetPasword(int userId, PasswordResetRequest request);
        AuthenticatedUser Login(UserLoginRequest user);
        void UpdateCategories(int userId, CategoryUpdateRequest request);
    }
}
