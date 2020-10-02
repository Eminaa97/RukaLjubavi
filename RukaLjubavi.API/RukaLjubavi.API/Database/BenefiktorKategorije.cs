using System;
using System.Collections.Generic;

namespace RukaLjubavi.API.Database
{
    public partial class BenefiktorKategorije
    {
        public int Id { get; set; }
        public int? BenefiktorId { get; set; }
        public int? KategorijaId { get; set; }

        public virtual Benefiktor Benefiktor { get; set; }
        public virtual Kategorija Kategorija { get; set; }
    }
}
