package com.example.yourdigitalpath.utils

object Validator {
    fun validateNationalId(id: String): String? {
        return when {
            id.isEmpty() -> "يرجى إدخال الرقم القومي"
            id.length != 14 -> "يجب أن يتكون الرقم القومي من 14 رقمًا"
            !id.all { it.isDigit() } -> "الرقم القومي يجب أن يحتوي على أرقام فقط"
            else -> null
        }
    }

    fun validatePhone(phone: String): String? {
        return when {
            phone.isEmpty() -> "يرجى إدخال رقم الهاتف"
            phone.length != 11 -> "يجب أن يتكون رقم الهاتف من 11 رقمًا"
            !phone.startsWith("01") -> "يجب أن يبدأ بـ 01"
            !phone.all { it.isDigit() } -> "يجب أن يحتوي على أرقام فقط"
            else -> null
        }
    }
}