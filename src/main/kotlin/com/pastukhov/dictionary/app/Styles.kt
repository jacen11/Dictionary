package com.pastukhov.dictionary.app

import javafx.scene.paint.Color
import javafx.scene.paint.Color.rgb
import javafx.scene.paint.Paint
import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val cssRule by cssclass()
        val cssButton by cssclass()
        val cssHeader by cssclass()
    }

    init {
        select(cssHeader){
            fontWeight = FontWeight.NORMAL
        }

        select(cssRule) {
            minHeight = 500.px
            minWidth = 700.px
            fontWeight = FontWeight.BOLD
            backgroundColor += c(223, 223, 223)
            borderRadius += box(20.px)
        }

        select(cssButton){
            backgroundColor += c(77, 102, 204)
            padding = box(0.7.em, 0.57.em)
            fontSize = 14.px
            textFill = c("white")

            and(hover) {
                backgroundColor += Color.RED
            }

        }

    }
}