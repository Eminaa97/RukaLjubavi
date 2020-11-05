package com.fit.ba.rukaljubavi.Models

import java.io.Serializable

class Donator(var korisnikId: Int ,var ime: String, var prezime: String,var jmbg: String,var email:String,var telefon:String, var adresa: String, var datumRegistracije: String, var isVerifikovan: Boolean, var mjestoPrebivalista: String,var mjestoPrebivalistaId:Int,
              var brojDonacija: Int, var ocjenaPovjerljivost: Float, var ocjenaBrzinaDostavljanja: Float, var ocjenaPostivanjeDogovora: Float)
    : Serializable {
}