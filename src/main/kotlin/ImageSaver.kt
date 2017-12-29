import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.HTMLButtonElement
import kotlin.browser.document

class ImageSaver {
    fun update(png: String) {
        val link = document.getElementById("save") as HTMLAnchorElement
        link.href = png.replace("data:image/png", "data:application/octet-stream")

        var preview = document.getElementById("preview")
        if (preview == null) {
            preview = document.createElement("img");
            preview.setAttribute("id", "preview");
            preview.setAttribute("height", "60");
            preview.setAttribute("width", "60");
            val container = document.getElementById("previewContainer") as HTMLButtonElement
            container.appendChild(preview);
        }
        preview.setAttribute("src", png);
    }
}