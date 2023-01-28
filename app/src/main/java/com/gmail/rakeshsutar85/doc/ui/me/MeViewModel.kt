package com.gmail.rakeshsutar85.doc.ui.me

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Me Fragment"
    }
    val text: LiveData<String> = _text
}