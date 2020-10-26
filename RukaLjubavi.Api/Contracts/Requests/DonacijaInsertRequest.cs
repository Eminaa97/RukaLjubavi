using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RukaLjubavi.Api.Contracts.Requests
{
    public class DonacijaInsertRequest
    {
        public string Opis { get; set; }
        public int? Kolicina { get; set; }
        public int? BenefiktorId { get; set; }
        public int KategorijaId { get; set; }
        public int? DonatorId { get; set; }
    }
}
