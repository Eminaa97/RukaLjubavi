using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RukaLjubavi.Api.Contracts.Requests
{
    public class DonacijaSearchRequest
    {
        public bool? IsPrihvacena { get; set; }
        public int? DonatorId { get; set; }
        public int? BenefiktorId { get; set; }
        public bool? isZahtjevZaDonatora { get; set; }
        public bool? isZahtjevZaBenefiktora { get; set; }
    }
}
