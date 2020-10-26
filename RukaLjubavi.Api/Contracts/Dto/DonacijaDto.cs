using RukaLjubavi.Api.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RukaLjubavi.Api.Contracts.Dto
{
    public class DonacijaDto
    {
        public int Id { get; set; }
        public string Opis { get; set; }
        public int Kolicina { get; set; }
        public bool IsPrihvacena { get; set; }
        public DateTime DatumVrijeme { get; set; }
        public string DonatorId { get; set; }
        public string DonatorIme { get; set; }
        public string DonatorPrezime { get; set; }
        public string DonatorJmbg { get; set; }
        public DateTime DonatorDatumRodjenja { get; set; }
        public string DonatorMjestoRodjenja { get; set; }
        public string BenefiktorId { get; set; }
        public string BenefiktorNazivKompanije { get; set; }
        public string BenefiktorPdvbroj { get; set; }
        public string NazivKategorije { get; set; }
    }
}
