using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace RukaLjubavi.Api.Models
{ 
    public enum StatusDonacije
    {
        Aktivna,
        Na_cekanju,
        Prihvacena,
        Odbijena,
        U_toku,
        Zavrsena
    }
    public class Donacija : IEntity
    {
        [Key]
        public int Id { get; set; }
        public string Opis { get; set; }
        public int? Kolicina { get; set; }
        public StatusDonacije StatusDonacije { get; set; }
        public DateTime? DatumVrijeme { get; set; }

        [ForeignKey("BenefiktorId,KategorijaId")]
        public virtual BenefiktorKategorija BenefiktorKategorije { get; set; }
        public int? BenefiktorId { get; set; }
        public int? KategorijaId { get; set; }
        public string NazivKategorije { get; set; }

        [ForeignKey(nameof(DonatorId))]
        public virtual Donator Donator { get; set; }
        public int? DonatorId { get; set; }
    }
}
