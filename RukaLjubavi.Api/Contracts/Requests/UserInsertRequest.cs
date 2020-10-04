using System;
using System.Collections.Generic;

namespace RukaLjubavi.Api.Contracts.Requests
{
    public class UserInsertRequest
    {
        public string Email { get; set; }
        public string Telefon { get; set; }
        public string Adresa { get; set; }
        public int MjestoPrebivalistaId { get; set; }
        public string Password { get; set; }
        public string ConfirmPassword { get; set; }
        public IList<int> Kategorije { get; set; }
    }

    public class DonatorInsertRequest : UserInsertRequest
    {
        public string Ime { get; set; }
        public string Prezime { get; set; }
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
