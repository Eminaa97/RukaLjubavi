using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RukaLjubavi.Api.Contracts.Dto
{
    public class NotifikacijaDto
    {
        public int Id { get; set; }
        public string Sadrzaj { get; set; }
        public DateTime DatumSlanja { get; set; }
        public DateTime? DatumPregleda { get; set; }
        public string Korisnik { get; set; }
        public int KorisnikId { get; set; }
    }
}
