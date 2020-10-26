using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RukaLjubavi.Api.Migrations
{
    public partial class ajdin1 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Donacije_Donatori_DonatorId",
                table: "Donacije");

            migrationBuilder.DropForeignKey(
                name: "FK_Donacije_BenefiktorKategorije_BenefiktorId_KategorijaId",
                table: "Donacije");

            migrationBuilder.AlterColumn<int>(
                name: "Kolicina",
                table: "Donacije",
                nullable: true,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<int>(
                name: "KategorijaId",
                table: "Donacije",
                nullable: true,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<int>(
                name: "DonatorId",
                table: "Donacije",
                nullable: true,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<DateTime>(
                name: "DatumVrijeme",
                table: "Donacije",
                nullable: true,
                oldClrType: typeof(DateTime),
                oldType: "datetime2");

            migrationBuilder.AlterColumn<int>(
                name: "BenefiktorId",
                table: "Donacije",
                nullable: true,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AddForeignKey(
                name: "FK_Donacije_Donatori_DonatorId",
                table: "Donacije",
                column: "DonatorId",
                principalTable: "Donatori",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_Donacije_BenefiktorKategorije_BenefiktorId_KategorijaId",
                table: "Donacije",
                columns: new[] { "BenefiktorId", "KategorijaId" },
                principalTable: "BenefiktorKategorije",
                principalColumns: new[] { "BenefiktorId", "KategorijaId" },
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Donacije_Donatori_DonatorId",
                table: "Donacije");

            migrationBuilder.DropForeignKey(
                name: "FK_Donacije_BenefiktorKategorije_BenefiktorId_KategorijaId",
                table: "Donacije");

            migrationBuilder.AlterColumn<int>(
                name: "Kolicina",
                table: "Donacije",
                type: "int",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "KategorijaId",
                table: "Donacije",
                type: "int",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "DonatorId",
                table: "Donacije",
                type: "int",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.AlterColumn<DateTime>(
                name: "DatumVrijeme",
                table: "Donacije",
                type: "datetime2",
                nullable: false,
                oldClrType: typeof(DateTime),
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "BenefiktorId",
                table: "Donacije",
                type: "int",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.AddForeignKey(
                name: "FK_Donacije_Donatori_DonatorId",
                table: "Donacije",
                column: "DonatorId",
                principalTable: "Donatori",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_Donacije_BenefiktorKategorije_BenefiktorId_KategorijaId",
                table: "Donacije",
                columns: new[] { "BenefiktorId", "KategorijaId" },
                principalTable: "BenefiktorKategorije",
                principalColumns: new[] { "BenefiktorId", "KategorijaId" },
                onDelete: ReferentialAction.Cascade);
        }
    }
}
