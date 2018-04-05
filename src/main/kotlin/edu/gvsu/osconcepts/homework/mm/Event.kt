package edu.gvsu.osconcepts.homework.mm

enum class EventType {
    halt, arrival
}

data class Event(val type: EventType, val id: Int, val textSize: Int = 0, val dataSize: Int = 0)