using RukaLjubavi.Api.Models;
using System;

namespace RukaLjubavi.Api.Dto
{
    public class UserDto
    {
        public int Id { get; set; }
        public string Email { get; set; }
        public DateTime DatumRegistracije { get; set; }
        public string Telefon { get; set; }
        public string Adresa { get; set; }
        public bool IsVerifikovan { get; set; }
        public string MjestoPrebivalista { get; set; }
        public int MjestoPrebivalistaId { get; set; }
        public TipKorisnika TipKorisnika { get; set; }
    }
    public class UserDtoDonator : UserDto
    {
        public string Ime { get; set; }
        public string Prezime { get; set; }
        public string Jmbg { get; set; }
        public DateTime DatumRodjenja { get; set; }
        public string MjestoRodjenja { get; set; }
        public int MjestoRodjenjaId { get; set; }
    }
    public class UserBenefiktor : UserDto
    {
        public string NazivKompanije { get; set; }
        public string Pdvbroj { get; set; }
    }

}
