package com.pastukhov.dictionary.model

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.*
import javax.json.JsonArray
import javax.json.JsonObject

class Synonyms : JsonModel {

    val wordProperty = SimpleStringProperty()
    var word by wordProperty

    val synonymsProperty = SimpleStringProperty()// ReadOnlyListWrapper<String>() // SimpleListProperty<String>()
    var synonyms= FXCollections.observableArrayList<String>()// by synonymsProperty

    override fun updateModel(json: JsonObject) {
        with(json) {
            word = string("word")
            val jsonArray: JsonArray = getJsonArray("synonyms")
            val arr = mutableListOf<String >()
            jsonArray.forEach { arr.add(it.toString())}
            synonyms.setAll(arr)
           // synonyms = getJsonArray("synonyms")
//                    .joinToString ( prefix = "\n" )
//                    .replace("\"","")
        }
    }

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("word", word)
            add("synonyms", synonyms)
        }
    }
}

