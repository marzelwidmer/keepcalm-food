package app

import react.*
import react.dom.*
import logo.*
import ticker.*

class App : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        div("App-header") {
            logo()
            h2 {
                +"Welcome to keepcalm-food "
            }
        }

        p("App-ticker") {
            ticker()
        }
    }
}

fun RBuilder.app() = child(App::class) {}
