using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace RukaLjubavi.Api.Contracts.Requests
{
    public class UserInsertRequest
    {
        [EmailAddress]
        public string Email { get; set; }
        [Phone]
        public string Telefon { get; set; }
        public string Adresa { get; set; }
        public int MjestoPrebivalistaId { get; set; }
        [MinLength(5)]
        public string Password { get; set; }
        [MinLength(5)]
        public string ConfirmPassword { get; set; }
        public IList<int> Kategorije { get; set; }
    }

    public class DonatorInsertRequest : UserInsertRequest
    {
        public string Ime { get; set; }
        public string Prezime { get; set; }

        [StringLength(13)]
        public string Jmbg { get; set; }
        public DateTime DatumRodjenja { get; set; }
        public int MjestoRodjenjaId { get; set; }
    }

    public class BenefiktorInsertRequest : UserInsertRequest
    {
        public string NazivKompanije { get; set; }
        public string Pdvbroj { get; set; }
    }
}
