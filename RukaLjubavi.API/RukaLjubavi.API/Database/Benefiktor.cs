using System;
using System.Collections.Generic;

namespace RukaLjubavi.API.Database
{
    public partial class Benefiktor
    {
        public Benefiktor()
        {
            Donacija = new HashSet<Donacija>();
        }

        public int Id { get; set; }
        public string Naziv { get; set; }
        public string Pdvbroj { get; set; }
        public string Email { get; set; }
        public string Telefon { get; set; }
        public string LozinkaSalt { get; set; }
        public string LozinkaHash { get; set; }
        public string Adresa { get; set; }
        public bool? IsVerifikovan { get; set; }
        public int? GradId { get; set; }

        public virtual Grad Grad { get; set; }
        public virtual ICollection<Donacija> Donacija { get; set; }
    }
}
