package edu.gvsu.osconcepts.homework.mm

import java.io.File
import java.io.InputStreamReader
import java.util.*
import java.util.regex.Pattern

object TraceTape : Enumeration<Event> {
    private val lines: Stack<String> = Stack()
    private val previousEvents: Stack<Event> = Stack()
    private val previousLines: Stack<String> = Stack()
    private val pattern = Pattern.compile("""\s*(?<id>\d+)\s+(?:(?<halt>Halt)|(?:(?<text>\d+)\s+(?<data>\d+)))\s*""")

    override fun hasMoreElements(): Boolean {
        return !lines.empty()
    }

    override fun nextElement(): Event {
        return previousEvents.push(createEvent(previousLines.push(lines.pop())))
    }

    // here for potentially implementing a previous button on the front end
    fun rollback(): Event {
        lines.push(previousLines.pop())
        return previousEvents.pop()
    }

    //Generally I'd say we probably would use a queue to buffer requests of memory but I'm not a huge fan of java's
    //queue implementations so I used a stack because we know the entirety of whats going to happen throughout this run
    fun load(file: File) {
        lines.clear()
        previousEvents.clear()
        InputStreamReader(file.inputStream()).readLines().reversed().forEach { lines.push(it.trim()) }
    }

    private fun createEvent(line: String): Event {
        val matcher = pattern.matcher(line)
        if (!matcher.matches())
            throw IllegalArgumentException(":$line: does not match the pattern")
        val id = matcher.group("id").toInt()
        if (matcher.group("halt") != null)
            return Event(EventType.halt, id)
        return Event(EventType.arrival, id, matcher.group("text").toInt(), matcher.group("data").toInt())
    }
}