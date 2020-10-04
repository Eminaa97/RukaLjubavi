using RukaLjubavi.API.Models;
using System.ComponentModel.DataAnnotations;

namespace RukaLjubavi.Api.Models
{
    public class Benefiktor : IEntity
    {
        [Key]
        public int Id { get; set; }
        public string NazivKompanije { get; set; }
        public string Pdvbroj { get; set; }
    }
}
