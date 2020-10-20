package com.fit.ba.rukaljubavi.Models

import java.io.Serializable

class Grad(var id: Int, var naziv: String) : Serializable {
    override fun toString(): String {
        return naziv!!
    }
}