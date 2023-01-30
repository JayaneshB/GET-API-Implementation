package com.project.api_implementation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.project.api_implementation.`interface`.ApiInterface
import com.project.api_implementation.databinding.ActivityMainBinding
import com.project.api_implementation.model.MyDataItems
import org.json.JSONObject
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.net.URL


const val BASE_URL = "https://jsonplaceholder.typicode.com/"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMyData()
//        makeRequest()
//        getRequest()
    }

    private fun getRequest() {
        val url = URL("https://jsonplaceholder.typicode.com/posts")
        val dataConnection = url.openConnection() as HttpURLConnection
        dataConnection.requestMethod="GET"
        dataConnection.connect()

        if(dataConnection.responseCode==200) {
            val data= dataConnection.inputStream.bufferedReader().readText()
            println("Data : $data")
        }
        else{
            println("Error : ${dataConnection.responseCode}")
        }
    }

    private fun makeRequest() {
        val connection =
            URL("https://api.themoviedb.org/3/movie/550?api_key=72feba6dc1a2eda1297a8f778e2eb1d1").openConnection() as HttpURLConnection
        val data = connection.inputStream.bufferedReader().readText()
//        val movieJsonObject = JSONObject(data)
//        val keys: Array<String> = JSONObject.getNames(movieJsonObject)
//        for (key in keys) {
//            println(key)
//        }
        val jsonObject = JSONObject(data)
        val iterator = jsonObject.keys()
        while (iterator.hasNext()) {
            val key = iterator.next()
            println(key)
        }
        println("budget: ${jsonObject.getLong("budget")}")
        val genres = jsonObject.getJSONArray("genres")
        for (i in 0 until genres.length()) {
            val genre = genres.getJSONObject(i)
            println("genre name: ${genre.getString("name")}")
        }
    }

    private fun getMyData() {

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<MyDataItems>?> {
            override fun onResponse(
                call: Call<List<MyDataItems>?>,
                response: Response<List<MyDataItems>?>
            ) {
                val responseBody = response.body()

                if (responseBody != null) {
                    val stringBuilder = StringBuilder()
                    for (myData in responseBody) {
                        stringBuilder.append(myData.title)
                        stringBuilder.append("\n")
                    }
                    binding.textId.text = stringBuilder
                }

            }

            override fun onFailure(call: Call<List<MyDataItems>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure" + t.message)
            }
        })

    }
}