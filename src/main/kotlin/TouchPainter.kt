import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.section
import org.w3c.dom.HTMLBodyElement
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.collections.set


class TouchPainter {
    lateinit var canvas: TouchCanvas

    private fun onColorButton(color: Color) {
        canvas.color = color
    }

    fun init(body: HTMLBodyElement) {
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
                button { id = "yellow"; onClickFunction = { onColorButton(Color.YELLOW) } }
                button { id = "magenta"; onClickFunction = { onColorButton(Color.MAGENTA) } }
                button { id = "cyan"; onClickFunction = { onColorButton(Color.CYAN) } }
                button {
                    id = "white";
                    onClickFunction = { onColorButton(Color.ERASE) }
                    img {
                        src = "eraser.png"
                        width = "50px"
                        height = "50px"
                    }
                }
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
