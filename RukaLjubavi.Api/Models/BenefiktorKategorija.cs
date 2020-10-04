using System.ComponentModel.DataAnnotations.Schema;

namespace RukaLjubavi.Api.Models
{
    public class BenefiktorKategorija
    {
        public int BenefiktorId { get; set; }
        public int KategorijaId { get; set; }

        [ForeignKey(nameof(BenefiktorId))]
        public  Benefiktor Benefiktor { get; set; }

        [ForeignKey(nameof(KategorijaId))]
        public  Kategorija Kategorija { get; set; }
    }
}
