using Microsoft.EntityFrameworkCore.Migrations;

namespace RukaLjubavi.Api.Migrations
{
    public partial class pdvbroj1 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "PdvBroj",
                columns: table => new
                {
                    PDVBroj = table.Column<string>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_PdvBroj", x => x.PDVBroj);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "PdvBroj");
        }
    }
}
