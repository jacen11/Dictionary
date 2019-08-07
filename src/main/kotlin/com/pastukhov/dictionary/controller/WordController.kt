package com.pastukhov.dictionary.controller

import com.pastukhov.dictionary.model.Antonym
import com.pastukhov.dictionary.model.Meaning
import com.pastukhov.dictionary.model.Synonyms
import tornadofx.*

/**
 * Abstraction layer to perform HTTP operations
 */
class WordController : Controller() {

    private val api: Rest by inject()

    /**
     * Sends a GET request to fetch the meaning
     * from the API endpoint
     * @param word to get the meaning for
     * @return meaning object if success else null
     */
    fun getMeaning(word: String): Meaning? {
        val response = api.get("$word/definitions")
        try {
            return when {
                response.ok() -> response.one().toModel()
                else -> null
            }
        } finally {
            response.consume()
        }
    }

    fun getSynonyms(word: String) : Synonyms? {
        val response = api.get("$word/synonyms")
        try {
            return when {
                response.ok() -> response.one().toModel()
                else -> null
            }
        } finally {
            response.consume()
        }
    }
    fun getAntonyms(word: String) : Antonym? {
        val response = api.get("$word/antonyms")
        try {
            return when {
                response.ok() -> response.one().toModel()
                else -> null
            }
        } finally {
            response.consume()
        }
    }
}