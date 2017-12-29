import org.w3c.dom.events.Event

val Event.touches
    get() = asDynamic().touches as TouchList

external class TouchList {
    val length: Int

}

fun TouchList.get(idx: Int) = asDynamic()[idx]

operator fun TouchList.iterator() : Iterator<Touch> = object : AbstractIterator<Touch>() {
    var idx = 0
    override fun computeNext() {
        if (idx < length) {
            setNext(get(idx++))
        } else {
            done()
        }
    }
}

external class Touch {
    val pageX: Double
    val pageY: Double
    val identifier: Int
    val radiusX: Double
    val radiusY: Double
    val rotationAngle: Double
}