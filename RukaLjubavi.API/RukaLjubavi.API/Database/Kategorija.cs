using System;
using System.Collections.Generic;

namespace RukaLjubavi.API.Database
{
    public partial class Kategorija
    {
        public Kategorija()
        {
            Donacija = new HashSet<Donacija>();
        }

        public int Id { get; set; }
        public string Naziv { get; set; }

        public virtual ICollection<Donacija> Donacija { get; set; }
    }
}
