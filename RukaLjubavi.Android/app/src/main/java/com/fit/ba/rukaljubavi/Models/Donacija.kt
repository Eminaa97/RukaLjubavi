package com.fit.ba.rukaljubavi.Models

import java.io.Serializable

class Donacija(
    var id: Int,
    var opis: String,
    var kolicina: Int,
    var datumVrijeme: String,
    var donatorId: Int,
    var donatorIme: String,
    var donatorPrezime: String,
    var benefiktorId: Int,
    var benefiktorNazivKompanije: String,
    var benefiktorLokacija: String,
    var nazivKategorije: String,
    var status: String
) : Serializable {
}