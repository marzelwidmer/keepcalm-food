package index

 import kotlinext.js.require
 import kotlinext.js.requireAll
 import react.dom.h1
 import react.dom.render
 import kotlin.browser.document

fun main(args: Array<String>) {
    requireAll(require.context("src", true, js("/\\.css$/")))

    val rootDiv = document.getElementById("root")
    render(rootDiv) {
        h1 {
            +"Welcome to keepcalm foods"
        }
    }
}
