using RukaLjubavi.Api.Models;
using System;

namespace RukaLjubavi.Api.Contracts.Dto
{
    public class OcjenaDonacijeDto 
    {
        public int Id { get; set; }
        public string Komentar { get; set; }
        public int Povjerljivost { get; set; }
        public int BrzinaDostavljanja { get; set; }
        public int PostivanjeDogovora { get; set; }
        public int KorisnikId { get; set; }
        public TipKorisnika Ocjenjivac { get; set; }
        public string DonatorIme { get; set; }
        public string DonatorPrezime { get; set; }
        public string BenefiktorNazivKompanije { get; set; }
    }
}
