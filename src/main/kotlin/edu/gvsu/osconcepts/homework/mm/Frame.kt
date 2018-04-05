package edu.gvsu.osconcepts.homework.mm

import javafx.scene.paint.Color

class Frame(val id: Int) {
    // Used for simplicity on front end
    val text: String
        get() {
            return if (program == null)
                "Free"
            else
                "${if (program?.dataTable?.any { it.frame == id } == true) "Data" else "Text"} of ${program?.id}"
        }

    val color: Color
        get() {
            return when {
                program?.dataTable?.any { it.frame == id } == true -> {
                    program?.color ?: Color.color(1.0, 1.0, 1.0)
                }
                program?.textTable?.any { it.frame == id } == true -> {
                    program?.color ?: Color.color(1.0, 1.0, 1.0)
                }
                else -> Color.color(1.0, 1.0, 1.0)
            }
        }

    private val program: Program?
        get () {
            return MemoryManager.programs.values.firstOrNull {
                it.dataTable.any { it.frame == this.id } || it.textTable.any { it.frame == this.id }
            }
        }
}