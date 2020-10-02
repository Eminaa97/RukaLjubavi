using System;
using System.Collections.Generic;

namespace RukaLjubavi.API.Database
{
    public partial class DonatorKategorije
    {
        public int Id { get; set; }
        public int? DonatorId { get; set; }
        public int? KategorijaId { get; set; }

        public virtual Donator Donator { get; set; }
        public virtual Kategorija Kategorija { get; set; }
    }
}
