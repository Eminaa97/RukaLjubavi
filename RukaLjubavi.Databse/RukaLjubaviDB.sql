CREATE DATABASE RukaLjubaviDB;
GO

USE RukaLjubaviDB;
GO

CREATE TABLE Drzave
(
	Id int primary key identity(1,1),
	Naziv nvarchar(50),
)
GO

CREATE TABLE Gradovi
(
	Id int primary key identity(1,1),
	Naziv nvarchar(50),
	DrzavaId int foreign key references Drzave(Id)
)
GO

CREATE TABLE Korisnici
(
	Id int primary key identity(1,1),
	Email nvarchar(50),
	LozinkaSalt nvarchar(200),
	LozinkaHash nvarchar(200),
	DatumRegistracije datetime,
	Telefon nvarchar(20),
	Adresa nvarchar(100),
	IsVerifikovan bit,
	MjestoPrebivalistaID int foreign key references Gradovi(Id)
)
GO

CREATE TABLE Donator
(
	Id int primary key identity(1,1),
	Ime nvarchar(50),
	Prezime nvarchar(50),
	JMBG nvarchar(13),
	DatumRodjenja date,
	MjestoRodjenjaID int foreign key references Gradovi(Id)
)
GO

CREATE TABLE Benefiktor
(
	Id int primary key identity(1,1),
	NazivKompanije nvarchar(100),
	PDVBroj nvarchar(20)
)
GO

CREATE TABLE Notifikacije
(
	Id int primary key identity(1,1),
	UserId int foreign key references Korisnici(Id),
	Sadrzaj nvarchar(255),
	DatumSlanja datetime,
	DatumPregleda datetime
)

CREATE TABLE Kategorija
(
	Id int primary key identity(1,1),
	Naziv nvarchar(50),
);
GO

CREATE TABLE Donator_Kategorije
(
	DonatorId int foreign key references Donator(Id),
	KategorijaId int foreign key references Kategorija(Id),
	constraint PK_Donator_KategorijeID primary key(DonatorId, KategorijaId)
);
GO

CREATE TABLE Benefiktor_Kategorije
(
	BenefiktorId int foreign key references Benefiktor(Id),
	KategorijaId int foreign key references Kategorija(Id),
	constraint PK_Benefiktor_KategorijeID primary key(BenefiktorId, KategorijaId)
)
GO

CREATE TABLE Donacija
(
	Id int primary key identity(1,1),
	DonatorId int foreign key references Donator(Id),
	BenefiktorId int,
	KategorijaId int,
	Opis nvarchar(200),
	Kolicina int,
	isPrihvacena bit,
	DatumVrijeme datetime,
	FOREIGN KEY (BenefiktorId, KategorijaId) REFERENCES Benefiktor_Kategorije (BenefiktorId, KategorijaId)
)
GO 

CREATE TABLE OcjenaDonacije
(
	Id int primary key identity(1,1),
	DonacijaId int foreign key references Donacija(Id),
	Komentar nvarchar(200),
	KomentarId int,
	Povjerljivost int,
	BrzinaDostavljanja int,
	PostivanjeDogovora int,
	Ocjenjivac int, -- 1 = Donator, 2 = Benefiktor
)
GO 
