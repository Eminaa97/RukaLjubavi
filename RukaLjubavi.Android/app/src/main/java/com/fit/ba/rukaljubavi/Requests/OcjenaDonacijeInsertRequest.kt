package com.fit.ba.rukaljubavi.Requests

import java.io.Serializable

class OcjenaDonacijeInsertRequest()
{
    var komentar: String? = null
    var povjerljivost: Int? = null
    var brzinaDostavljanja: Int? = null
    var postivanjeDogovora: Int? = null
    var ocjenjivacTipKorisnika: Int? = null
    var korisnikId: Int? = null
    var donacijaId: Int? = null
}