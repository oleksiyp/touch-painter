import kotlinx.html.dom.create
import kotlinx.html.js.body
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertEquals

class TouchPainterTest {
    @Test
    fun start() {
        val painter = TouchPainter()

        val html = document.create.body()
        painter.onStart(html)

        assertEquals("<section id=\"paintArea\"><canvas id=\"canvas\"></canvas></section><section id=\"controlArea\"><button id=\"red\"></button><button id=\"green\"></button><button id=\"blue\"></button><a id=\"save\" href=\"#\" download=\"image.png\"><button id=\"previewContainer\"></button></a></section>", html.innerHTML)
    }
}