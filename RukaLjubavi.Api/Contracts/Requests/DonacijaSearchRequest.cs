using RukaLjubavi.Api.Models;

namespace RukaLjubavi.Api.Contracts.Requests
{
    public class DonacijaSearchRequest
    {
        public StatusDonacije? StatusDonacije { get; set; }
        public int? DonatorId { get; set; }
        public int? KategorijaId { get; set; }
        public string NazivKompanije { get; set; }
        public int? LokacijaId { get; set; }
        public int? BenefiktorId { get; set; }
        public bool? isZahtjevZaDonatora { get; set; }
        public bool? isZahtjevZaBenefiktora { get; set; }
    }
}
