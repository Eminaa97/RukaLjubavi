using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace RukaLjubavi.Api.Models
{
    public class Korisnik : IEntity
    {
        [Key]
        public int Id { get; set; }
        public string Email { get; set; }
        public string LozinkaSalt { get; set; }
        public string LozinkaHash { get; set; }
        public DateTime DatumRegistracije { get; set; }
        public string Telefon { get; set; }
        public string Adresa { get; set; }
        public bool IsVerifikovan { get; set; }
        public TipKorisnika TipKorisnika { get; set; }

        [ForeignKey(nameof(MjestoPrebivalistaId))]
        public Grad MjestoPrebivalista { get; set; }
        public int MjestoPrebivalistaId { get; set; }
    }
}
