using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace RukaLjubavi.API.Database
{
    public partial class RukaLjubaviDBContext : DbContext
    {
        public RukaLjubaviDBContext()
        {
        }

        public RukaLjubaviDBContext(DbContextOptions<RukaLjubaviDBContext> options)
            : base(options)
        {
        }

        public virtual DbSet<Benefiktor> Benefiktor { get; set; }
        public virtual DbSet<Donacija> Donacija { get; set; }
        public virtual DbSet<Donator> Donator { get; set; }
        public virtual DbSet<Grad> Grad { get; set; }
        public virtual DbSet<Kategorija> Kategorija { get; set; }
        public virtual DbSet<OcjenaDonacije> OcjenaDonacije { get; set; }
        public virtual DbSet<PredefinisaniKomentari> PredefinisaniKomentari { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
//#warning To protect potentially sensitive information in your connection string, you should move it out of source code. See http://go.microsoft.com/fwlink/?LinkId=723263 for guidance on storing connection strings.
                optionsBuilder.UseSqlServer("Server=(local);Database=RukaLjubaviDB;Integrated Security=True;Trusted_Connection=True;");
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Benefiktor>(entity =>
            {
                entity.Property(e => e.Adresa).HasMaxLength(50);

                entity.Property(e => e.Email).HasMaxLength(50);

                entity.Property(e => e.IsVerifikovan).HasColumnName("isVerifikovan");

                entity.Property(e => e.LozinkaHash).HasMaxLength(200);

                entity.Property(e => e.LozinkaSalt).HasMaxLength(200);

                entity.Property(e => e.Naziv).HasMaxLength(50);

                entity.Property(e => e.Pdvbroj)
                    .HasColumnName("PDVBroj")
                    .HasMaxLength(20);

                entity.Property(e => e.Telefon).HasMaxLength(20);

                entity.HasOne(d => d.Grad)
                    .WithMany(p => p.Benefiktor)
                    .HasForeignKey(d => d.GradId)
                    .HasConstraintName("FK__Benefikto__GradI__29572725");
            });

            modelBuilder.Entity<Donacija>(entity =>
            {
                entity.Property(e => e.DatumVrijeme).HasColumnType("datetime");

                entity.Property(e => e.IsObavljena).HasColumnName("isObavljena");

                entity.Property(e => e.IsPrihvacena).HasColumnName("isPrihvacena");

                entity.Property(e => e.Opis).HasMaxLength(200);

                entity.HasOne(d => d.Benefiktor)
                    .WithMany(p => p.Donacija)
                    .HasForeignKey(d => d.BenefiktorId)
                    .HasConstraintName("FK__Donacija__Benefi__2F10007B");

                entity.HasOne(d => d.Donator)
                    .WithMany(p => p.Donacija)
                    .HasForeignKey(d => d.DonatorId)
                    .HasConstraintName("FK__Donacija__Donato__2E1BDC42");

                entity.HasOne(d => d.Kategorija)
                    .WithMany(p => p.Donacija)
                    .HasForeignKey(d => d.KategorijaId)
                    .HasConstraintName("FK__Donacija__Katego__300424B4");
            });

            modelBuilder.Entity<Donator>(entity =>
            {
                entity.Property(e => e.Adresa).HasMaxLength(50);

                entity.Property(e => e.DatumRodjenja).HasColumnType("date");

                entity.Property(e => e.Email).HasMaxLength(50);

                entity.Property(e => e.Ime).HasMaxLength(50);

                entity.Property(e => e.IsVerifikovan).HasColumnName("isVerifikovan");

                entity.Property(e => e.Jmbg)
                    .HasColumnName("JMBG")
                    .HasMaxLength(13);

                entity.Property(e => e.LozinkaHash).HasMaxLength(200);

                entity.Property(e => e.LozinkaSalt).HasMaxLength(200);

                entity.Property(e => e.MjestoPrebivalista).HasMaxLength(50);

                entity.Property(e => e.MjestoRodjenja).HasMaxLength(50);

                entity.Property(e => e.Prezime).HasMaxLength(50);

                entity.Property(e => e.Telefon).HasMaxLength(20);

                entity.HasOne(d => d.Grad)
                    .WithMany(p => p.Donator)
                    .HasForeignKey(d => d.GradId)
                    .HasConstraintName("FK__Donator__GradId__267ABA7A");
            });

            modelBuilder.Entity<Grad>(entity =>
            {
                entity.Property(e => e.Naziv).HasMaxLength(50);
            });

            modelBuilder.Entity<Kategorija>(entity =>
            {
                entity.Property(e => e.Naziv).HasMaxLength(50);
            });

            modelBuilder.Entity<OcjenaDonacije>(entity =>
            {
                entity.Property(e => e.IsOcjenioBenefiktor).HasColumnName("isOcjenioBenefiktor");

                entity.Property(e => e.IsOcjenioDonator).HasColumnName("isOcjenioDonator");

                entity.Property(e => e.Komentar).HasMaxLength(200);

                entity.HasOne(d => d.Donacija)
                    .WithMany(p => p.OcjenaDonacije)
                    .HasForeignKey(d => d.DonacijaId)
                    .HasConstraintName("FK__OcjenaDon__Donac__32E0915F");
            });

            modelBuilder.Entity<PredefinisaniKomentari>(entity =>
            {
                entity.Property(e => e.Komentar).HasMaxLength(200);
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
