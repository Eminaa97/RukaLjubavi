using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RukaLjubavi.Api.Contracts.Requests
{
    public class NotifikacijaSearchRequest
    {
        public DateTime? DatumSlanja { get; set; }
        public DateTime? DatumPregleda { get; set; }
        public string Korisnik { get; set; }
        public int? KorisnikId { get; set; }
    }
}
