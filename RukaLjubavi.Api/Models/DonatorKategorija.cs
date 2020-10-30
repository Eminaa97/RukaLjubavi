using System.ComponentModel.DataAnnotations.Schema;

namespace RukaLjubavi.Api.Models
{
    public  class DonatorKategorija
    {
        public int DonatorId { get; set; }
        public int KategorijaId { get; set; }

        [ForeignKey(nameof(DonatorId))]
        public  Donator Donator { get; set; }

        [ForeignKey(nameof(KategorijaId))]
        public  Kategorija Kategorija { get; set; }
        public bool isPotrebnaKategorija { get; set; }
    }
}
