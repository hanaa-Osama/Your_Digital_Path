package com.example.yourdigitalpath.domain 

object NationalIdValidator {

    fun isValid(nationalId: String): Boolean {

        return nationalId.length == 14
    }
}