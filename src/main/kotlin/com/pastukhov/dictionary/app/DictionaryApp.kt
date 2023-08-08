package com.pastukhov.dictionary.app

import com.pastukhov.dictionary.Constants
import com.pastukhov.dictionary.view.MainView
import tornadofx.App
import tornadofx.HttpURLRequest
import tornadofx.Rest

/**
 * Starting point of the application
 */
class DictionaryApp : App(MainView::class, Styles::class) {

    private val api: Rest by inject()

    init {
        api.baseURI = "https://wordsapiv1.p.rapidapi.com/words/"
        api.engine.requestInterceptor = {
            (it as HttpURLRequest).addHeader("X-Mashape-Key", Constants.API_KEY)
        }
    }
}