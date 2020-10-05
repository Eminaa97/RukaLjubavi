using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace RukaLjubavi.Api.Models
{
    public class Donator : IEntity
    {
        [Key]
        public int Id { get; set; }
        public string Ime { get; set; }
        public string Prezime { get; set; }
        public string Jmbg { get; set; }
        public DateTime DatumRodjenja { get; set; }


        [ForeignKey(nameof(MjestoRodjenjaId))]
        public Grad MjestoRodjenja { get; set; }
        public int MjestoRodjenjaId { get; set; }


        [ForeignKey(nameof(KorisnikId))]
        public Korisnik Korisnik { get; set; }
        public int KorisnikId { get; set; }
    }
}
