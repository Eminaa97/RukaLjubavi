using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RukaLjubavi.Api.Contracts.Requests
{
    public class NotifikacijaInsertRequest
    {
        public string Sadrzaj { get; set; }
        public int KorisnikId { get; set; }
    }
}
