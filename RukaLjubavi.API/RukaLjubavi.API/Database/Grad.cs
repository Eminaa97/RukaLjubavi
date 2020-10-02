using System;
using System.Collections.Generic;

namespace RukaLjubavi.API.Database
{
    public partial class Grad
    {
        public Grad()
        {
            Benefiktor = new HashSet<Benefiktor>();
            Donator = new HashSet<Donator>();
        }

        public int Id { get; set; }
        public string Naziv { get; set; }

        public virtual ICollection<Benefiktor> Benefiktor { get; set; }
        public virtual ICollection<Donator> Donator { get; set; }
    }
}
