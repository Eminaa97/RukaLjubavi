using Microsoft.EntityFrameworkCore;
using RukaLjubavi.Api.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RukaLjubavi.Api.Database
{
    public class RukaLjubaviDbContext : DbContext
    {
        public RukaLjubaviDbContext(DbContextOptions<RukaLjubaviDbContext> options) : base(options) { }

        protected override void OnModelCreating(ModelBuilder builder)
        {
            builder.Entity<BenefiktorKategorija>().HasKey(table => new
            {
                table.BenefiktorId,
                table.KategorijaId
            });
            builder.Entity<DonatorKategorija>().HasKey(table => new
            {
                table.DonatorId,
                table.KategorijaId
            });
        }

        public DbSet<Drzava> Drzave { get; set; }
        public DbSet<Grad> Gradovi { get; set; }
        public DbSet<Korisnik> Korisnici { get; set; }
        public DbSet<Donator> Donatori { get; set; }
        public DbSet<Benefiktor> Benefiktori { get; set; }
        public DbSet<Notifikacija> Notifikacije { get; set; }
        public DbSet<Kategorija> Kategorije { get; set; }
        public DbSet<Donacija> Donacije { get; set; }
        public DbSet<DonatorKategorija> DonatorKategorije { get; set; }
        public DbSet<BenefiktorKategorija> BenefiktorKategorije { get; set; }
        public DbSet<OcjenaDonacije> OcjeneDonacija { get; set; }
        public DbSet<PdvBroj> PdvBroj { get; set; }
    }
}
