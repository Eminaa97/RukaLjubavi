package com.fit.ba.rukaljubavi.Models

import java.io.Serializable

class OcjenaDonacije(
    var id: Int,
    var komentar: String,
    var povjerljivost : Int,
    var brzinaDostavljanja : Int,
    var postivanjeDogovora : Int,
    var korisnikId  : Int,
    var donatorIme : String,
    var donatorPrezime: String,
    var benefiktorNazivKompanije : String
) : Serializable