using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RukaLjubavi.Api.Migrations
{
    public partial class Initial : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Drzave",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Naziv = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Drzave", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Kategorije",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Naziv = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Kategorije", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Gradovi",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Naziv = table.Column<string>(nullable: true),
                    DrzavaId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Gradovi", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Gradovi_Drzave_DrzavaId",
                        column: x => x.DrzavaId,
                        principalTable: "Drzave",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "Korisnici",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Email = table.Column<string>(nullable: true),
                    LozinkaSalt = table.Column<string>(nullable: true),
                    LozinkaHash = table.Column<string>(nullable: true),
                    DatumRegistracije = table.Column<DateTime>(nullable: false),
                    Telefon = table.Column<string>(nullable: true),
                    Adresa = table.Column<string>(nullable: true),
                    IsVerifikovan = table.Column<bool>(nullable: false),
                    TipKorisnika = table.Column<int>(nullable: false),
                    MjestoPrebivalistaId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Korisnici", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Korisnici_Gradovi_MjestoPrebivalistaId",
                        column: x => x.MjestoPrebivalistaId,
                        principalTable: "Gradovi",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "Benefiktori",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    NazivKompanije = table.Column<string>(nullable: true),
                    Pdvbroj = table.Column<string>(nullable: true),
                    KorisnikId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Benefiktori", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Benefiktori_Korisnici_KorisnikId",
                        column: x => x.KorisnikId,
                        principalTable: "Korisnici",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "Donatori",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Ime = table.Column<string>(nullable: true),
                    Prezime = table.Column<string>(nullable: true),
                    Jmbg = table.Column<string>(nullable: true),
                    DatumRodjenja = table.Column<DateTime>(nullable: false),
                    MjestoRodjenjaId = table.Column<int>(nullable: false),
                    KorisnikId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Donatori", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Donatori_Korisnici_KorisnikId",
                        column: x => x.KorisnikId,
                        principalTable: "Korisnici",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Donatori_Gradovi_MjestoRodjenjaId",
                        column: x => x.MjestoRodjenjaId,
                        principalTable: "Gradovi",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.NoAction);
                });

            migrationBuilder.CreateTable(
                name: "Notifikacije",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Sadrzaj = table.Column<string>(nullable: true),
                    DatumSlanja = table.Column<DateTime>(nullable: false),
                    DatumPregleda = table.Column<DateTime>(nullable: true),
                    KorisnikId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Notifikacije", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Notifikacije_Korisnici_KorisnikId",
                        column: x => x.KorisnikId,
                        principalTable: "Korisnici",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "BenefiktorKategorije",
                columns: table => new
                {
                    BenefiktorId = table.Column<int>(nullable: false),
                    KategorijaId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_BenefiktorKategorije", x => new { x.BenefiktorId, x.KategorijaId });
                    table.ForeignKey(
                        name: "FK_BenefiktorKategorije_Benefiktori_BenefiktorId",
                        column: x => x.BenefiktorId,
                        principalTable: "Benefiktori",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_BenefiktorKategorije_Kategorije_KategorijaId",
                        column: x => x.KategorijaId,
                        principalTable: "Kategorije",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "DonatorKategorije",
                columns: table => new
                {
                    DonatorId = table.Column<int>(nullable: false),
                    KategorijaId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_DonatorKategorije", x => new { x.DonatorId, x.KategorijaId });
                    table.ForeignKey(
                        name: "FK_DonatorKategorije_Donatori_DonatorId",
                        column: x => x.DonatorId,
                        principalTable: "Donatori",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_DonatorKategorije_Kategorije_KategorijaId",
                        column: x => x.KategorijaId,
                        principalTable: "Kategorije",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "Donacije",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Opis = table.Column<string>(nullable: true),
                    Kolicina = table.Column<int>(nullable: false),
                    IsPrihvacena = table.Column<bool>(nullable: false),
                    DatumVrijeme = table.Column<DateTime>(nullable: false),
                    BenefiktorId = table.Column<int>(nullable: false),
                    KategorijaId = table.Column<int>(nullable: false),
                    DonatorId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Donacije", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Donacije_Donatori_DonatorId",
                        column: x => x.DonatorId,
                        principalTable: "Donatori",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Donacije_BenefiktorKategorije_BenefiktorId_KategorijaId",
                        columns: x => new { x.BenefiktorId, x.KategorijaId },
                        principalTable: "BenefiktorKategorije",
                        principalColumns: new[] { "BenefiktorId", "KategorijaId" },
                        onDelete: ReferentialAction.NoAction);
                });

            migrationBuilder.CreateTable(
                name: "OcjeneDonacija",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Komentar = table.Column<string>(nullable: true),
                    Povjerljivost = table.Column<int>(nullable: false),
                    BrzinaDostavljanja = table.Column<int>(nullable: false),
                    PostivanjeDogovora = table.Column<int>(nullable: false),
                    Ocjenjivac = table.Column<int>(nullable: false),
                    KorisnikId = table.Column<int>(nullable: false),
                    DonacijaId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_OcjeneDonacija", x => x.Id);
                    table.ForeignKey(
                        name: "FK_OcjeneDonacija_Donacije_DonacijaId",
                        column: x => x.DonacijaId,
                        principalTable: "Donacije",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Benefiktori_KorisnikId",
                table: "Benefiktori",
                column: "KorisnikId");

            migrationBuilder.CreateIndex(
                name: "IX_BenefiktorKategorije_KategorijaId",
                table: "BenefiktorKategorije",
                column: "KategorijaId");

            migrationBuilder.CreateIndex(
                name: "IX_Donacije_DonatorId",
                table: "Donacije",
                column: "DonatorId");

            migrationBuilder.CreateIndex(
                name: "IX_Donacije_BenefiktorId_KategorijaId",
                table: "Donacije",
                columns: new[] { "BenefiktorId", "KategorijaId" });

            migrationBuilder.CreateIndex(
                name: "IX_Donatori_KorisnikId",
                table: "Donatori",
                column: "KorisnikId");

            migrationBuilder.CreateIndex(
                name: "IX_Donatori_MjestoRodjenjaId",
                table: "Donatori",
                column: "MjestoRodjenjaId");

            migrationBuilder.CreateIndex(
                name: "IX_DonatorKategorije_KategorijaId",
                table: "DonatorKategorije",
                column: "KategorijaId");

            migrationBuilder.CreateIndex(
                name: "IX_Gradovi_DrzavaId",
                table: "Gradovi",
                column: "DrzavaId");

            migrationBuilder.CreateIndex(
                name: "IX_Korisnici_MjestoPrebivalistaId",
                table: "Korisnici",
                column: "MjestoPrebivalistaId");

            migrationBuilder.CreateIndex(
                name: "IX_Notifikacije_KorisnikId",
                table: "Notifikacije",
                column: "KorisnikId");

            migrationBuilder.CreateIndex(
                name: "IX_OcjeneDonacija_DonacijaId",
                table: "OcjeneDonacija",
                column: "DonacijaId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "DonatorKategorije");

            migrationBuilder.DropTable(
                name: "Notifikacije");

            migrationBuilder.DropTable(
                name: "OcjeneDonacija");

            migrationBuilder.DropTable(
                name: "Donacije");

            migrationBuilder.DropTable(
                name: "Donatori");

            migrationBuilder.DropTable(
                name: "BenefiktorKategorije");

            migrationBuilder.DropTable(
                name: "Benefiktori");

            migrationBuilder.DropTable(
                name: "Kategorije");

            migrationBuilder.DropTable(
                name: "Korisnici");

            migrationBuilder.DropTable(
                name: "Gradovi");

            migrationBuilder.DropTable(
                name: "Drzave");
        }
    }
}
