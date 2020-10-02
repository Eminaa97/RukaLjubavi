using System;
using System.Collections.Generic;

namespace RukaLjubavi.API.Database
{
    public partial class OcjenaDonacije
    {
        public int Id { get; set; }
        public int? DonacijaId { get; set; }
        public string Komentar { get; set; }
        public int? Povjerljivost { get; set; }
        public int? BrzinaDostavljanja { get; set; }
        public int? PostivanjeDogovora { get; set; }
        public bool? IsOcjenioDonator { get; set; }
        public bool? IsOcjenioBenefiktor { get; set; }

        public virtual Donacija Donacija { get; set; }
    }
}
