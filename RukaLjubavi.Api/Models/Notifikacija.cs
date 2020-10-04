using RukaLjubavi.API.Models;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace RukaLjubavi.Api.Models
{
    public class Notifikacija : IEntity
    {
        [Key]
        public int Id { get; set; }
        public string Sadrzaj { get; set; }
        public DateTime DatumSlanja { get; set; }
        public DateTime? DatumPregleda { get; set; }

        [ForeignKey(nameof(KorisnikId))]
        public Korisnik Korisnik { get; set; }
        public int KorisnikId { get; set; }
    }
}
