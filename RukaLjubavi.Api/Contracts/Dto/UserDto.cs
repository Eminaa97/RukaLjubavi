using RukaLjubavi.Api.Models;
using System;

namespace RukaLjubavi.Api.Dto
{
    public class UserDto
    {
        public int Id { get; set; } // BenefiktorId ili DonatorId, zavisno od TipKorisnika
        public int KorisnikId { get; set; } // Id iz Korisnici tabele
        public string Email { get; set; }
        public DateTime DatumRegistracije { get; set; }
        public string Telefon { get; set; }
        public string Adresa { get; set; }
        public bool IsVerifikovan { get; set; }
        public string MjestoPrebivalista { get; set; }
        public int MjestoPrebivalistaId { get; set; }
        public TipKorisnika TipKorisnika { get; set; }
        public int BrojDonacija { get; set; }
    }
    public class DonatorDto : UserDto
    {
        public string Ime { get; set; }
        public string Prezime { get; set; }
        public string Jmbg { get; set; }
        public DateTime DatumRodjenja { get; set; }
        public string MjestoRodjenja { get; set; }
        public int MjestoRodjenjaId { get; set; }
    }
    public class BenefiktorDto : UserDto
    {
        public string NazivKompanije { get; set; }
        public string Pdvbroj { get; set; }
    }

}
