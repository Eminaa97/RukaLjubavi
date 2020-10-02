using System;
using System.Collections.Generic;

namespace RukaLjubavi.API.Database
{
    public partial class Donacija
    {
        public Donacija()
        {
            OcjenaDonacije = new HashSet<OcjenaDonacije>();
        }

        public int Id { get; set; }
        public int? DonatorId { get; set; }
        public int? BenefiktorId { get; set; }
        public int? KategorijaId { get; set; }
        public string Opis { get; set; }
        public int? Kolicina { get; set; }
        public bool? IsPrihvacena { get; set; }
        public bool? IsObavljena { get; set; }
        public DateTime? DatumVrijeme { get; set; }

        public virtual Benefiktor Benefiktor { get; set; }
        public virtual Donator Donator { get; set; }
        public virtual Kategorija Kategorija { get; set; }
        public virtual ICollection<OcjenaDonacije> OcjenaDonacije { get; set; }
    }
}
