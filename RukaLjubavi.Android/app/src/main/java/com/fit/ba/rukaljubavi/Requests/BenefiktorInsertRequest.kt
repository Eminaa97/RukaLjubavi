package com.fit.ba.rukaljubavi.Requests

import java.util.ArrayList

class BenefiktorInsertRequest {
    var email: String? = null
    var telefon: String? = null
    var adresa: String? = null
    var password: String? = null
    var confirmPassword: String? = null
    var nazivKompanije: String? = null
    var pdvbroj: String? = null
    var mjestoPrebivalistaId: Int? = null
    var kategorije: ArrayList<Int> = arrayListOf()
}