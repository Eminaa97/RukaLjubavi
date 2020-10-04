using RukaLjubavi.API.Models;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;

namespace RukaLjubavi.Api.Models
{
    public enum TipKorisnika
    {
        Donator = 1 ,
        Benefiktor = 2
    }
    public  class OcjenaDonacije : IEntity
    {
        public int Id { get; set; }
        public int DonacijaId { get; set; }
        public string Komentar { get; set; }
        public int Povjerljivost { get; set; }
        public int BrzinaDostavljanja { get; set; }
        public int PostivanjeDogovora { get; set; }
        public TipKorisnika Ocjenjivac { get; set; }

        [ForeignKey(nameof(DonacijaId))]
        public  Donacija Donacija { get; set; }
    }
}
