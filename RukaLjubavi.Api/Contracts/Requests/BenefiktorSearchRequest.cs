using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RukaLjubavi.Api.Contracts.Requests
{
    public class BenefiktorSearchRequest
    {
        public string nazivKompanije { get; set; }
        public int? LokacijaId { get; set; }

    }
}
