package index

import kotlinext.js.*
import react.dom.*
import kotlin.browser.*

fun main(args: Array<String>) {

    requireAll(require.context("src", true, js("/\\.css$/")))

    render(document.getElementById("root")) {
        h1{
            +"Welcome to keepcalm-food."
        }
    }
}
