using System;

namespace RukaLjubavi.Api.Contracts.Requests
{
    [Obsolete("NIJE DOVRESNOOOOOOOOOOOOOO")]
    public class UserUpdateRequest
    {
        public int Id { get; set; }
        public string Email { get; set; }
        public string Telefon { get; set; }
        public string Adresa { get; set; }
        public bool IsVerifikovan { get; set; }
        public int MjestoPrebivalistaId { get; set; }
    }
}
