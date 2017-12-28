import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.section
import org.w3c.dom.HTMLBodyElement
import org.w3c.dom.asList
import kotlin.browser.document
import kotlin.browser.window

class TouchPainter {
    enum class Color {
        RED, GREEN, BLUE
    }

    private fun onColorButton(color: Color) {
        println(color)
    }

    fun onStart(body: HTMLBodyElement) {
        body.append {
            section {
                id = "paintArea"
                canvas {
                    id = "canvas"
                }
            }
            section {
                id = "controlArea"
                button { id = "red"; onClickFunction = { onColorButton(Color.RED) } }
                button { id = "green"; onClickFunction = { onColorButton(Color.GREEN) } }
                button { id = "blue"; onClickFunction = { onColorButton(Color.BLUE) } }
                a {
                    id = "save"
                    href = "#"
                    attributes["download"] = "image.png"

                    button {
                        id = "previewContainer"
                    }
                }
            }
        }
    }


}

fun main(args: Array<String>) {
    val painter = TouchPainter()
    window.onload = {
        val body = document.getElementsByTagName("body")
                .asList()
                .first() as HTMLBodyElement

        painter.onStart(body)
    }
}