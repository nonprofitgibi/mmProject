package edu.gvsu.osconcepts.homework.mm

import java.util.*

object MemoryManager {
    val programs: HashMap<Int, Program> = HashMap()
    val frames: List<Frame> = (0..7).map { Frame(it) }
    const val frameSize = 512
    private val freeFrames: Stack<Frame> = Stack()

    init {
        frames.reversed().forEach { freeFrames.push(it) }
    }

    fun processEvent(event: Event): Program {
        return if (event.type == EventType.halt)
            deallocate(event.id)
        else
            allocate(Program(event))
    }

    private fun allocate(program: Program): Program {
        program.textTable.forEach { it.frame = freeFrames.pop().id }
        program.dataTable.forEach { it.frame = freeFrames.pop().id }
        programs[program.id] = program
        return program
    }

    private fun deallocate(id: Int): Program {
        programs[id]?.textTable?.forEach { freeFrames.push(frames[it.frame]) }
        programs[id]?.dataTable?.forEach { freeFrames.push(frames[it.frame]) }
        programs[id]?.allocated = false
        return programs.remove(id)!!
    }
}