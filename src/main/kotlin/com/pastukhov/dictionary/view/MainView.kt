package com.pastukhov.dictionary.view

import com.pastukhov.dictionary.Constants
import com.pastukhov.dictionary.app.Styles.Companion.cssRule
import com.pastukhov.dictionary.controller.WordController
import javafx.geometry.Orientation
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import tornadofx.*

/**
 * Base view shown when the application is started
 */
class MainView : View("Dictionary") {

    // Get the REST client
    private val api: Rest by inject()
    private val controller: WordController by inject()

    // UI elements
    private var inputWord: TextField by singleAssign()
    private var result: Label by singleAssign()

    init {
        // Configuring the client
        api.baseURI = "https://wordsapiv1.p.mashape.com/words/"
        api.engine.requestInterceptor = {
            (it as HttpURLRequest).addHeader("X-Mashape-Key", Constants.API_KEY)
        }

    }

    /**
     * Root View
     */
    override val root = vbox {
        addClass(cssRule)
        form {
            fieldset(labelPosition = Orientation.VERTICAL) {
                field("Enter word", Orientation.VERTICAL) {
                    inputWord = textfield()
                }
                buttonbar {
                    button("Clean") { action { result.text = "" } }
                    button("Get meaning") {
                        action {
                            if (inputWord.text.isNotBlank()) {
                                runAsync {
                                    // Send request to API
                                    controller.getMeaning(inputWord.text)
                                } ui { meaning ->
                                    if (meaning != null) {
                                        // Show the result on the UI
                                        val meanings = meaning.definitions.joinToString(
                                                "\n • ",
                                                "\n • ") { it.definition }
                                        result.text = "Meaning(s): $meanings"
                                    } else {
                                        result.text = "Unable to find the meaning"
                                    }
                                    result.isWrapText = true
                                }
                            }
                        }
                    }
                    button("Get synonyms") {
                        action {
                            if (inputWord.text.isNotBlank()) {
                                runAsync {
                                    // Send request to API
                                    controller.getSynonyms(inputWord.text)
                                } ui {
                                    if (it != null) {
                                        val synonyms = it.synonyms
                                                .joinToString(prefix = "\n")
                                                .replace("\"", "")
                                        result.text = "Synonym(s): $synonyms"
                                    } else {
                                        result.text = "Unable to find the synonyms"
                                    }
                                   result.isWrapText = true
                                }
                            }
                        }
                    }
                    button("Get antonyms") {
                        action {
                            if (inputWord.text.isNotBlank()) {
                                runAsync {
                                    // Send request to API
                                    controller.getAntonyms(inputWord.text)
                                } ui {
                                    if (it != null) {
                                        val antonyms = it.antonyms
                                                .joinToString(prefix = "\n")
                                                .replace("\"", "")
                                        result.text = "Antonym(s): $antonyms"
                                    } else {
                                        result.text = "Unable to find the antonyms"
                                    }
                                   result.isWrapText = true
                                }
                            }
                        }
                    }
                }
            }
            result = label { }
        }
    }
}