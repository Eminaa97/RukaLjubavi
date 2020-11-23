using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace RukaLjubavi.Api.Models
{
    public class Benefiktor : IEntity
    {
        [Key]
        public int Id { get; set; }
        public string NazivKompanije { get; set; }
        public string PDVbroj { get; set; }

        [ForeignKey(nameof(KorisnikId))]
        public Korisnik Korisnik { get; set; }
        public int KorisnikId { get; set; }
    }
}
