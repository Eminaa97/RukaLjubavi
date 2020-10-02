using System;
using System.Collections.Generic;

namespace RukaLjubavi.API.Database
{
    public partial class Donator
    {
        public Donator()
        {
            Donacija = new HashSet<Donacija>();
            DonatorKategorije = new HashSet<DonatorKategorije>();
        }

        public int Id { get; set; }
        public string Ime { get; set; }
        public string Prezime { get; set; }
        public string Jmbg { get; set; }
        public DateTime? DatumRodjenja { get; set; }
        public string MjestoRodjenja { get; set; }
        public string Email { get; set; }
        public string LozinkaSalt { get; set; }
        public string LozinkaHash { get; set; }
        public string Telefon { get; set; }
        public string Adresa { get; set; }
        public bool? IsVerifikovan { get; set; }
        public int? GradId { get; set; }

        public virtual Grad Grad { get; set; }
        public virtual ICollection<Donacija> Donacija { get; set; }
        public virtual ICollection<DonatorKategorije> DonatorKategorije { get; set; }
    }
}
