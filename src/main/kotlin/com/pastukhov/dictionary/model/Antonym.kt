package com.pastukhov.dictionary.model

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.*
import javax.json.JsonArray
import javax.json.JsonObject

class Antonym : JsonModel, Model {

    private val wordProperty = SimpleStringProperty()
    private var word by wordProperty

    var antonyms = FXCollections.observableArrayList<String>()

    override fun updateModel(json: JsonObject) {
        with(json) {
            word = string("word")
            val jsonArray: JsonArray = getJsonArray("antonyms")
            val arr = mutableListOf<String>()
            jsonArray.forEach { arr.add(it.toString()) }
            antonyms.setAll(arr)
        }
    }

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("word", word)
            add("antonyms", antonyms)
        }
    }

    override fun isEmpty() = antonyms.isEmpty() ?: true
    override fun isNotEmpty() = antonyms.isNotEmpty() ?: false
}
