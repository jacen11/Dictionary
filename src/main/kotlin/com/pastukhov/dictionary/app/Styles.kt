package com.pastukhov.dictionary.app

import javafx.scene.paint.Color.rgb
import javafx.scene.paint.Paint
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val cssRule by cssclass()
        val cssButton by cssclass()
    }

    init {
        select(cssRule) {
            minHeight = 500.px
            minWidth = 700.px
        }

        select(cssButton){
            //backgroundColor =  Paint.valueOf("#ff0000")
        }
    }
}