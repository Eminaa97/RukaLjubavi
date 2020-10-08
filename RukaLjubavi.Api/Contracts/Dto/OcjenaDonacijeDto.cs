using RukaLjubavi.Api.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

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
        public int Kolicina { get; set; }
        public bool IsPrihvacena { get; set; }
        public DateTime DatumVrijeme { get; set; }
        public string DonatorIme { get; set; }
        public string DonatorPrezime { get; set; }
        public string DonatorJmbg { get; set; }
        public DateTime DonatorDatumRodjenja { get; set; }
        public string DonatorMjestoRodjenja { get; set; }
        public string BenefiktorNazivKompanije { get; set; }
        public string BenefiktorPdvbroj { get; set; }
        public string NazivKategorije { get; set; }
    }
}
