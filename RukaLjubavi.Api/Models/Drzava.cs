using System.ComponentModel.DataAnnotations;

namespace RukaLjubavi.Api.Models
{
    public class Drzava : IEntity
    {
        [Key]
        public int Id { get; set; }
        public string Naziv { get; set; }
    }
}
