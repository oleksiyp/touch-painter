import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLElement
import kotlin.browser.window
import kotlin.collections.set
import kotlin.math.PI
import kotlin.math.sqrt

class TouchCanvas(val canvas: HTMLCanvasElement,
                  val paintArea: HTMLElement,
                  val imageSaver: ImageSaver) {

    val ctx = this.canvas.getContext("2d") as CanvasRenderingContext2D

    var color = Color.RED

    private fun fmtColor(color: Color) =
        when (color) {
            Color.RED -> "200, 0, 0"
            Color.GREEN -> "0, 200, 0"
            Color.BLUE -> "0, 0, 200"
            Color.CYAN -> "0, 200, 200"
            Color.MAGENTA -> "200, 0, 200"
            Color.YELLOW -> "200, 200, 0"
            Color.ERASE -> "255, 255, 255"
        }

    var clear = false
    var previousTouches = mutableMapOf<Int, Touch>()

    init {
        canvas.addEventListener("touchmove", { event ->
            event.preventDefault()
            processTouches(event.touches)
        })

        canvas.addEventListener("touchstart", { event ->
            event.preventDefault()
            clear = false
            processTouches(event.touches)
        })

        canvas.addEventListener("touchend", { event ->
            event.preventDefault()
            previousTouches.clear()
            if (clear) {
                clear()
            }
            saveImage()
        })

        window.addEventListener("resize", { onResize() })
        onResize()
    }

    private fun clear() {
        ctx.clearRect(
                0.0, 0.0,
                canvas.width.toDouble(),
                canvas.height.toDouble())
    }

    private fun saveImage() {
        val png = canvas.toDataURL("image/png")
        imageSaver.update(png)
    }

    fun paintTouch(x: Double, y: Double, touch: Touch) {
        fun ellipse(x: Double, y: Double, touch: Touch, r: Double, alpha: Double) {
            ctx.beginPath()
            ctx.ellipse(x, y,
                    touch.radiusX * r,
                    touch.radiusY * r,
                    touch.rotationAngle,
                    0.0,
                    2 * PI,
                    true)
            ctx.fillStyle = "rgba(" + fmtColor(color) + ", $alpha)"
            ctx.fill()
            ctx.closePath()
        }
        fun triEllipse() {
            ellipse(x, y, touch, 0.5, 0.1)
            ellipse(x, y, touch, 1.0 / 3, 0.1)
            ellipse(x, y, touch, 1.0 / 4, 0.4)
        }
        if (color == Color.ERASE) {
            val op = ctx.globalCompositeOperation
            ctx.globalCompositeOperation = "destination-out"
            triEllipse()
            ctx.globalCompositeOperation = op
        } else {
            triEllipse()
        }
    }


    private fun processTouches(touches: TouchList) {
        val newPrev = mutableMapOf<Int, Touch>()

        for (touch in touches) {
            processTouch(touch)
            newPrev[touch.identifier] = touch
        }
        previousTouches.clear()
        previousTouches.putAll(newPrev)
    }

    private fun processTouch(touch: Touch) {
        val px = touch.pageX;
        val py = touch.pageY;

        if (px < 10 && py < 10) {
            clear()
            clear = true;
        }

        val prevTouch = previousTouches[touch.identifier]

        if (prevTouch == null) {
            paintTouch(px, py, touch)
            return
        }

        val ppx = prevTouch.pageX
        val ppy = prevTouch.pageY

        var dx = px - ppx
        var dy = py - ppy

        val d = sqrt(dx * dx + dy * dy)
        dx /= d
        dy /= d

        var x = ppx + dx * 5
        var y = ppy + dy * 5

        for (j in 1..d.toInt() step 5) {
            paintTouch(x, y, touch)
            x += dx * 5
            y += dy * 5
        }
    }

    private fun onResize() {
        val canvasData = ctx.getImageData(
                0.0, 0.0,
                (canvas.width - 1).toDouble(),
                (canvas.height - 1).toDouble())

        canvas.width = paintArea.offsetWidth
        canvas.height = paintArea.offsetHeight
        ctx.putImageData(canvasData, 0.0, 0.0);
    }
}