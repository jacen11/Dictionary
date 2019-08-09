package com.pastukhov.dictionary.controller

import com.pastukhov.dictionary.model.Antonym
import com.pastukhov.dictionary.model.Meaning
import com.pastukhov.dictionary.model.Models
import com.pastukhov.dictionary.model.Synonyms
import tornadofx.*
import kotlin.RuntimeException

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

    fun getSynonyms(word: String): Synonyms? {
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

    private var responseAntonyms: Rest.Response? = null
    fun getAntonyms(word: String): Antonym {
        try {
            try {
                responseAntonyms = api.get("$word/antonyms")
            } catch (ex: RuntimeException) {
                System.err.println("инет беда")
            }
            return if (responseAntonyms != null && (responseAntonyms?.ok() == true)) responseAntonyms?.one()?.toModel()
                    ?: Antonym() else Antonym()
        } finally {
          responseAntonyms?.consume()
        }
    }


//    private var responseT: Rest.Response? = null
//    fun <T> getModel(word: String, model: Models ): T? {
//       // if (T is String) {        }
//
//        try {
//            try {
//                responseT = api.get("$word/${model.request}")
//            } catch (ex: RuntimeException) {
//            }
//
//            return if (responseT?.ok() == true) responseT?.one()?.toModel() else null
//
//        } finally {
//            responseAntonyms?.consume()
//        }
//    }
}