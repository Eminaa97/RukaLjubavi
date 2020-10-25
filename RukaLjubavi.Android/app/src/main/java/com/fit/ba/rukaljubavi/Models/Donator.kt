package com.fit.ba.rukaljubavi.Models

import java.io.Serializable

class Donator(var ime: String, var prezime: String,var jmbg: String,var email:String,var telefon:String, var adresa: String, var datumRegistracije: String, var isVerifikovan: Boolean, var mjestoPrebivalista: String)
    : Serializable {
}