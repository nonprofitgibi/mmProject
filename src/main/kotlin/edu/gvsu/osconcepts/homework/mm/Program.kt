package edu.gvsu.osconcepts.homework.mm

import javafx.scene.paint.Color

class Program(event: Event) {
    val id: Int = event.id
    val textTable: List<Page> = List(Math.ceil((event.textSize.toDouble() / (MemoryManager.frameSize))).toInt()) { Page(it) }
    val textColor: Color = Color.color(Math.random(), Math.random(), Math.random())
    val dataTable: List<Page> = List(Math.ceil(event.dataSize.toDouble() / (MemoryManager.frameSize)).toInt()) { Page(it) }
    val dataColor: Color = Color.color(Math.random(), Math.random(), Math.random())
    //for front end
    var allocated = true
}