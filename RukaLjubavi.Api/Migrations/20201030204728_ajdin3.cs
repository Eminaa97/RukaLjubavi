using Microsoft.EntityFrameworkCore.Migrations;

namespace RukaLjubavi.Api.Migrations
{
    public partial class ajdin3 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "NazivKategorije",
                table: "Donacije",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "NazivKategorije",
                table: "Donacije");
        }
    }
}
