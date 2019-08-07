package com.pastukhov.dictionary.model

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.*
import javax.json.JsonObject

class Meaning : JsonModel {

    private val wordProperty = SimpleStringProperty()
    var word by wordProperty

    val definitions = FXCollections.observableArrayList<Definition>()

    override fun updateModel(json: JsonObject) {
        with(json) {
            word = string("word")
            definitions.setAll(getJsonArray("definitions").toModel())
        }
    }

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("word", word)
            add("definitions", definitions.toJSON())
        }
    }
}