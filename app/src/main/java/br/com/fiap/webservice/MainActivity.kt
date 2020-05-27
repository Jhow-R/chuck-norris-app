package br.com.fiap.webservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    lateinit var buttonJoke: Button
    lateinit var textViewJoke: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonJoke = findViewById(R.id.buttonJoke)
        textViewJoke = findViewById(R.id.textViewJoke)

        buttonJoke.setOnClickListener {

            //Coroutines
            lifecycleScope.launch(Dispatchers.IO) {

                val joke = getJoke()

                withContext(Dispatchers.Main) {
                    textViewJoke.text = joke
                }
            }
        }
    }

    fun getJoke(): String {

        val url = URL("https://api.chucknorris.io/jokes/random")

        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        val jsonString = connection.inputStream.bufferedReader().readText()

        Log.d("JSON", jsonString)

        val joke = JSONObject(jsonString).getString("value")

        return joke
    }
}
