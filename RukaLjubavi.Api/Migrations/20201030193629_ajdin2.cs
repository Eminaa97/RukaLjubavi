using Microsoft.EntityFrameworkCore.Migrations;

namespace RukaLjubavi.Api.Migrations
{
    public partial class ajdin2 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<bool>(
                name: "isPotrebnaKategorija",
                table: "DonatorKategorije",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "isPotrebnaKategorija",
                table: "BenefiktorKategorije",
                nullable: false,
                defaultValue: false);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "isPotrebnaKategorija",
                table: "DonatorKategorije");

            migrationBuilder.DropColumn(
                name: "isPotrebnaKategorija",
                table: "BenefiktorKategorije");
        }
    }
}
