package org.example.superid

fun isEmailValid(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}\$".toRegex()
    return email.matches(emailRegex)
}