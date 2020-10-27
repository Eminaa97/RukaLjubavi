using Microsoft.EntityFrameworkCore.Migrations;

namespace RukaLjubavi.Api.Migrations
{
    public partial class UserEntity : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "IsPrihvacena",
                table: "Donacije");

            migrationBuilder.AddColumn<int>(
                name: "StatusDonacije",
                table: "Donacije",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "StatusDonacije",
                table: "Donacije");

            migrationBuilder.AddColumn<bool>(
                name: "IsPrihvacena",
                table: "Donacije",
                type: "bit",
                nullable: false,
                defaultValue: false);
        }
    }
}
