using System.ComponentModel.DataAnnotations;

namespace RukaLjubavi.Api.Contracts.Requests
{
    public class UserLoginRequest
    {
        [EmailAddress]
        public string Email { get; set; }
        public string Password { get; set; }
    }
}
