using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RukaLjubavi.Api.Contracts.Requests
{
    public class OcjenaDonacijeInsertRequest
    {
        public int Id { get; set; }
        public string Komentar { get; set; }
        [Range(1, 5)]
        public int? Povjerljivost { get; set; }
        [Range(1,5)]
        public int? BrzinaDostavljanja { get; set; }
        [Range(1, 5)]
        public int? PostivanjeDogovora { get; set; }
        public int OcjenjivacTipKorisnika { get; set; }
        public int KorisnikId { get; set; }
        public int DonacijaId { get; set; }
    }
}
