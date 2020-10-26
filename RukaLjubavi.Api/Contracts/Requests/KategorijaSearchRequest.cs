using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RukaLjubavi.Api.Contracts.Requests
{
    public class KategorijaSearchRequest
    {
        public int? DonatorId { get; set; }
        public int? BenefiktorId { get; set; }
    }
}
