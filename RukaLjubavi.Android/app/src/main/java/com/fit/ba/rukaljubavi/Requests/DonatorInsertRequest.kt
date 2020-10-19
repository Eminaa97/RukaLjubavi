package com.fit.ba.rukaljubavi.Requests

import java.util.*

class DonatorInsertRequest {
    var email: String? = null
    var telefon: String? = null
    var adresa: String? = null
    var password: String? = null
    var confirmPassword: String? = null
    var ime: String? = null
    var prezime: String? = null
    var jmbg: String? = null
    var datumRodjenja: String? = null
    var mjestoRodjenjaId: Int? = null
    var mjestoPrebivalistaId: Int? = null
    var kategorije: ArrayList<Int> = arrayListOf()
}