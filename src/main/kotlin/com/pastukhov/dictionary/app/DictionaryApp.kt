package com.pastukhov.dictionary.app

import com.pastukhov.dictionary.view.MainView
import tornadofx.App

/**
 * Starting point of the application
 */
class DictionaryApp : App(MainView::class, Styles::class)