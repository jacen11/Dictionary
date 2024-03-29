package com.pastukhov.dictionary.view

import com.pastukhov.dictionary.Constants
import com.pastukhov.dictionary.app.Styles.Companion.cssButton
import com.pastukhov.dictionary.app.Styles.Companion.cssField
import com.pastukhov.dictionary.app.Styles.Companion.cssRule
import com.pastukhov.dictionary.controller.WordController
import javafx.geometry.Orientation
import javafx.scene.control.Label
import javafx.scene.control.ProgressIndicator
import javafx.scene.control.TextField
import tornadofx.*

/**
 * Base view shown when the application is started
 */
class MainView : View("Dictionary") {

    private val controller: WordController by inject()

    // UI elements
    private var inputWord: TextField by singleAssign()
    private var result: Label by singleAssign()
    private var progressIndicator: ProgressIndicator = ProgressIndicator() //by singleAssign()

    private var isWrongSymbols = false

    /**
     * Root View
     */
    override val root = vbox {
        addClass(cssRule)
        form {
            fieldset(labelPosition = Orientation.VERTICAL) {
                label("Enter word")
                field { inputWord = textfield() }.addClass(cssField)
                buttonbar {
                    ValidationContext().addValidator(inputWord, inputWord.textProperty()) {
                        isWrongSymbols = inputWord.text.replace(Regex("[(\\d||\\w)]+"), "").isNotEmpty()
                        if (isWrongSymbols) error("You can use only numbers and letters of the Latin alphabet.") else null
                    }

                    button("Clean") { action { result.text = ""; progressIndicator.hide() } }.addClass(cssButton)
                    button("Get meaning") {
                        addClass(cssButton)
                        action {
                            when {
                                inputWord.text.isBlank() -> result.text = "Empty request"
                                isWrongSymbols -> result.text =
                                    "You can use only numbers and letters of the Latin alphabet."
                            }
                            if (inputWord.text.isBlank() || isWrongSymbols) {
                                return@action
                            }
//                            if (inputWord.text.isBlank()) {
//                                result.text = "Empty request"
//                                return@action
//                            }
//                            if (isWrongSymbols) {
//                                result.text = "You can use only numbers and letters of the Latin alphabet."
//                                return@action
//                            }
                            runAsync {
                                // Send request to API
                                progressIndicator.show()
                                controller.getMeaning(inputWord.text)
                            } ui { meaning ->
                                progressIndicator.hide()
                                if (meaning != null) {
                                    // Show the result on the UI

                                    val meanings = meaning.definitions.joinToString(
                                        "\n • ",
                                        "\n • "
                                    ) { it.definition }
                                    result.text = if (meanings.isBlank()) "Missing" else "Meaning(s): $meanings"
                                } else {
                                    result.text = "Unable to find the meaning"
                                }
                                result.isWrapText = true
                            }
                        }
                    }
                    button("Get synonyms") {
                        addClass(cssButton)
                        action {
                            if (isWrongSymbols) {
                                result.text = "You can use only numbers and letters of the Latin alphabet."
                                return@action
                            }
                            if (inputWord.text.isBlank()) {
                                result.text = "Empty request"
                                return@action
                            }
                            runAsync {
                                progressIndicator.show()
                                // Send request to API
                                controller.getSynonyms(inputWord.text)
                            } ui {
                                progressIndicator.hide()
                                if (it != null) {
                                    val synonyms = it.synonyms
                                        .joinToString(prefix = "\n")
                                        .replace("\"", "")
                                    result.text = if (synonyms.isBlank()) "Missing" else "Synonym(s): $synonyms"
                                } else {
                                    result.text = "Unable to find the synonyms"
                                }
                                result.isWrapText = true
                            }

                        }
                    }
                    button("Get antonyms") {
                        addClass(cssButton)
                        action {
                            if (isWrongSymbols) {
                                result.text = "You can use only numbers and letters of the Latin alphabet."
                                return@action
                            }
                            if (inputWord.text.isBlank()) {
                                result.text = "Empty request"
                                return@action
                            }

                            runAsync {
                                progressIndicator.show()
                                // Send request to API
                                controller.getAntonyms(inputWord.text)
                                //     controller.getModel<Antonym>(inputWord.text, Models.Antonym)
                            } ui {
                                progressIndicator.hide()
                                if (it.isNotEmpty()) {
                                    val antonyms = it.antonyms
                                        .joinToString(prefix = "\n")
                                        .replace("\"", "")
                                    result.text = if (antonyms.isBlank()) "Missing" else "Antonym(s): $antonyms"
                                } else {
                                    result.text = "Unable to find the antonyms"
                                }
                                result.isWrapText = true
                            }

                        }
                    }
                }
            }
            result = label { }
            progressIndicator = progressindicator { hide() }
        }
    }
}
