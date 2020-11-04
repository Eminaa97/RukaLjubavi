using System;
using System.ComponentModel.DataAnnotations;

namespace RukaLjubavi.Api.Contracts.Requests
{
    public class OcjenaDonacijeInsertRequest
    {
        public string Komentar { get; set; }
        [Range(1, 5)]
        public int? Povjerljivost { get; set; }
        [Range(1, 5)]
        public int? BrzinaDostavljanja { get; set; }
        [Range(1, 5)]
        public int? PostivanjeDogovora { get; set; }
        public int OcjenjivacTipKorisnika { get; set; }
        public int KorisnikId { get; set; }
        public int DonacijaId { get; set; }
    }
}
