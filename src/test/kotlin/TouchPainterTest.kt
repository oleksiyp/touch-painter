import kotlinx.html.dom.create
import kotlinx.html.js.body
import kotlin.browser.document
import kotlin.test.Test

class TouchPainterTest {
    @Test
    fun start() {
        val painter = TouchPainter()

        val html = document.create.body()
        painter.onStart(html)
        println(html.innerHTML)
    }
}