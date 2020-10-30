package com.fit.ba.rukaljubavi.Models

import java.io.Serializable

class Benefiktor(var id: Int,var korisnikId: Int,var nazivKompanije: String,var pdvbroj: String, var email: String,
                 var telefon: String, var adresa: String, var mjestoPrebivalista: String,var mjestoPrebivalistaId: Int ,var datumRegistracije: String, var BrojDonacija: Int) : Serializable {
}