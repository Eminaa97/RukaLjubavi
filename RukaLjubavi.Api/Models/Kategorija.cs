using RukaLjubavi.API.Models;
using System.ComponentModel.DataAnnotations;

namespace RukaLjubavi.Api.Models
{
    public class Kategorija : IEntity
    {
        [Key]
        public int Id { get; set; }
        public string Naziv { get; set; }
    }
}
