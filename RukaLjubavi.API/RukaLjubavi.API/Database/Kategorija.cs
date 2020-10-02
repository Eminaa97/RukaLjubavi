using System;
using System.Collections.Generic;

namespace RukaLjubavi.API.Database
{
    public partial class Kategorija
    {
        public Kategorija()
        {
            BenefiktorKategorije = new HashSet<BenefiktorKategorije>();
            Donacija = new HashSet<Donacija>();
            DonatorKategorije = new HashSet<DonatorKategorije>();
        }

        public int Id { get; set; }
        public string Naziv { get; set; }

        public virtual ICollection<BenefiktorKategorije> BenefiktorKategorije { get; set; }
        public virtual ICollection<Donacija> Donacija { get; set; }
        public virtual ICollection<DonatorKategorije> DonatorKategorije { get; set; }
    }
}
