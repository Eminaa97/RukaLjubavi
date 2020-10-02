create database RukaLjubaviDB

use RukaLjubaviDB

create table Grad
(
	Id int primary key identity(1,1),
	Naziv nvarchar(50),
)

create table Donator
(
	Id int primary key identity(1,1),
	Ime nvarchar(50),
	Prezime nvarchar(50),
	JMBG nvarchar(13),
	DatumRodjenja date,
	MjestoRodjenja nvarchar(50),
	Email nvarchar(50),
	LozinkaSalt nvarchar(200),
	LozinkaHash nvarchar(200),
	Telefon nvarchar(20),
	Adresa nvarchar(50),
	isVerifikovan bit,
	GradId int foreign key references Grad(Id)
)

create table Benefiktor
(
	Id int primary key identity(1,1),
	Naziv nvarchar(50),
	PDVBroj nvarchar(20),
	Email nvarchar(50),
	Telefon nvarchar(20),
	LozinkaSalt nvarchar(200),
	LozinkaHash nvarchar(200),
	Adresa nvarchar(50),
	isVerifikovan bit,
	GradId int foreign key references Grad(Id)
)

create table Kategorija
(
	Id int primary key identity(1,1),
	Naziv nvarchar(50),
)

create table Donator_Kategorije
(
	Id int primary key identity(1,1),
	DonatorId int foreign key references Donator(Id),
	KategorijaId int foreign key references Kategorija(Id),
)

create table Benefiktor_Kategorije
(
	Id int primary key identity(1,1),
	BenefiktorId int foreign key references Benefiktor(Id),
	KategorijaId int foreign key references Kategorija(Id),
)

create table Donacija
(
	Id int primary key identity(1,1),
	DonatorId int foreign key references Donator(Id),
	BenefiktorId int foreign key references Benefiktor(Id),
	KategorijaId int foreign key references Kategorija(Id),
	Opis nvarchar(200),
	Kolicina int,
	isPrihvacena bit,
	isObavljena bit,
	DatumVrijeme datetime
)

create table OcjenaDonacije
(
	Id int primary key identity(1,1),
	DonacijaId int foreign key references Donacija(Id),
	Komentar nvarchar(200),
	Povjerljivost int,
	BrzinaDostavljanja int,
	PostivanjeDogovora int,
	isOcjenioDonator bit,
	isOcjenioBenefiktor bit,
)

create table PredefinisaniKomentari
(
	Id int primary key identity(1,1),
	Komentar nvarchar(200)
)