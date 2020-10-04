using RukaLjubavi.API.Models;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace RukaLjubavi.Api.Models
{
    public class Grad : IEntity
    {
        [Key]
        public int Id { get; set; }
        public string Naziv { get; set; }

        [ForeignKey(nameof(DrzavaId))]
        public Drzava Drzava { get; set; }
        public int DrzavaId { get; set; }
    }
}
