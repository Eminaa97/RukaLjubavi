using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RukaLjubavi.Api.Contracts.Requests
{
    public class OcjenaDonacijeSearchRequest
    {
        public int Povjerljivost { get; set; }
        public int BrzinaDostavljanja { get; set; }
        public int PostivanjeDogovora { get; set; }
        public int KorisnikId { get; set; }
        public int DonacijaId { get; set; }
    }
}
