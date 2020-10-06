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
        public Donacija Donacija { get; set; }
    }
}
