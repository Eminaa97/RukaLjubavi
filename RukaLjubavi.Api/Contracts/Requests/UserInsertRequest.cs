using System;

namespace RukaLjubavi.Api.Contracts.Requests
{
    [Obsolete("NIJE DOVRESNOOOOOOOOOOOO")]
    public class UserInsertRequest
    {
        public string Email { get; set; }
        public string Telefon { get; set; }
        public string Adresa { get; set; }
        public int MjestoPrebivalistaId { get; set; }
        public string Password { get; set; }
        public string ConfirmPassword { get; set; }
    }
}
