package com.ethixdigitallabs.personaforgestudio.assistant

class Cynthia {

    fun greetUser(name: String): String {
        return "Welcome back, $name. What shall we forge today?"
    }

    fun celebrate() {
        println("🎆 Fireworks erupt across the forge!")
    }

    fun comment(message: String): String {
        return "Cynthia: $message"
    }
}