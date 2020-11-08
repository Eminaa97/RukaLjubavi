namespace RukaLjubavi.Api.Contracts.Requests
{
    public class OcjenaDonacijeSearchRequest
    {
        public int? KorisnikId { get; set; }
        public int? DonacijaId { get; set; }
        public bool isVasaDonacija { get; set; } = true;
    }
}
