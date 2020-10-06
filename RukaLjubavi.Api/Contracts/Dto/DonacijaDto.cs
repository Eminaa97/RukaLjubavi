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
        public virtual BenefiktorKategorija BenefiktorKategorije { get; set; }
        public virtual Donator Donator { get; set; }
    }
}
