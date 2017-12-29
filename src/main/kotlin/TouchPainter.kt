import kotlinx.html.a
import kotlinx.html.button
import kotlinx.html.canvas
import kotlinx.html.dom.append
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.section
import org.w3c.dom.HTMLBodyElement
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.asList
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.browser.window
import kotlin.collections.first
import kotlin.collections.set


enum class Color {
    RED, GREEN, BLUE
}

class TouchPainter {
    lateinit var canvas: TouchCanvas

    private fun onColorButton(color: Color) {
        canvas.color = color
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

        (document.getElementById("red") as HTMLElement).click()


        canvas = TouchCanvas(
                document.getElementById("canvas") as HTMLCanvasElement,
                document.getElementById("paintArea") as HTMLElement,
                ImageSaver())

        val controlArea = document.getElementById("controlArea") as HTMLElement
        controlArea.addEventListener("touchmove", Event::preventDefault)
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