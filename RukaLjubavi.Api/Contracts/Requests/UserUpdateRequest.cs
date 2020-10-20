using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace RukaLjubavi.Api.Contracts.Requests
{
    public class UserUpdateRequest
    {
        public int KorisnikId { get; set; }
        [EmailAddress]
        public string Email { get; set; }
        [Phone]
        public string Telefon { get; set; }
        public string Adresa { get; set; }
        public bool IsVerifikovan { get; set; }
        public int MjestoPrebivalistaId { get; set; }
        public IList<int> Kategorije { get; set; }
    }
    public class DonatorUpdateRequest : UserUpdateRequest
    {
        public string Ime { get; set; }
        public string Prezime { get; set; }
    }

    public class BenefiktorUpdateRequest : UserUpdateRequest
    {
        public string NazivKompanije { get; set; }
    }
}
