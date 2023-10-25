package com.example.chatbot.viewmodels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class VoiceToTextParser(
    private val app : Application,
    private val talkViewModel: TalkViewModel,
    private val context : Context
) : RecognitionListener, ViewModel() {

    private val _state = MutableStateFlow(VoiceToTextParserState())

    val state = _state.asStateFlow()
    val recognizer = SpeechRecognizer.createSpeechRecognizer(app)

    fun startListening(languageCode : String = "en")
    {
        _state.update { VoiceToTextParserState() }

        if(!SpeechRecognizer.isRecognitionAvailable(app))
        {
            _state.update {
                it.copy(
                    error = "Recognition is not available"
                )
            }
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                languageCode
            )
        }

        recognizer.setRecognitionListener(this)
        recognizer.startListening(intent)

        _state.update {
            it.copy(
                isSpeaking = true
            )
        }
    }

    fun textToSpeechFun(result: String){
        var  textToSpeech:TextToSpeech? = null

        textToSpeech = TextToSpeech(
            context
        ) {
            if (it == TextToSpeech.SUCCESS) {
                textToSpeech?.let { txtToSpeech ->
                    txtToSpeech.language = Locale.US
                    txtToSpeech.setSpeechRate(1.0f)
                    txtToSpeech.speak(
                        result,
                        TextToSpeech.QUEUE_ADD,
                        null,
                        null
                    )

                }

            }
        }
        textToSpeech.stop()
    }

    fun stopListening() {
        _state.update {
            it.copy(
                isSpeaking = false
            )
        }
        recognizer.stopListening()
    }


    override fun onReadyForSpeech(params: Bundle?) {
        _state.update {
            it.copy(
                error = null
            )
        }
    }

    override fun onBeginningOfSpeech() = Unit

    override fun onRmsChanged(rmsdB: Float) = Unit

    override fun onBufferReceived(buffer: ByteArray?) = Unit

    override fun onEndOfSpeech() {
        _state.update {
            it.copy(
                isSpeaking = false
            )
        }
    }

    override fun onError(error: Int) {
        if (error == SpeechRecognizer.ERROR_CLIENT)
            return
        _state.update {
            it.copy(
                error = "Error : $error"
            )
        }
    }

    override fun onResults(results: Bundle?) {
        results
            ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.getOrNull(0)
            ?.let { result ->
                _state.update {
                    it.copy(
                        spokenText = result
                    )
                }
            }
        GlobalScope.launch {
            val result = talkViewModel.emitMessage(state.value.spokenText)

            textToSpeechFun(result.await())
        }

    }

    override fun onPartialResults(partialResults: Bundle?) = Unit

    override fun onEvent(eventType: Int, params: Bundle?) = Unit
}

data class VoiceToTextParserState(
    val spokenText : String = "",
    val isSpeaking : Boolean = false,
    val error : String? = null,
    val resultText : String = ""
)