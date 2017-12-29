import org.w3c.dom.HTMLBodyElement
import org.w3c.dom.asList
import kotlin.browser.document
import kotlin.browser.window

fun main(args: Array<String>) {
    val painter = TouchPainter()
    window.onload = {
        val body = document.getElementsByTagName("body")
                .asList()
                .first() as HTMLBodyElement

        painter.init(body)
    }
}