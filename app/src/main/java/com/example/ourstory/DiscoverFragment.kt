package com.example.ourstory

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.EXTRA_STATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ourstory.adapter.CardViewDiscoverAdapter
import com.example.ourstory.model.Book
import com.example.ourstory.model.BookPart
import kotlinx.android.synthetic.main.activity_discover.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class DiscoverFragment : Fragment(R.layout.fragment_discover) {
    private val listBook = ArrayList<Book>()
    private val client = OkHttpClient()
    private lateinit var adapter: CardViewDiscoverAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val mFragmentManager = fragmentManager as FragmentManager
//        val mFragmentTransaction = mFragmentManager.beginTransaction()
//        adapter = CardViewDiscoverAdapter(listBook,mFragmentTransaction )

        if(savedInstanceState == null){
            progressBar.visibility = View.VISIBLE
            recyclerview_id.setHasFixedSize(true)
            getListBook()
        }else{
            progressBar.visibility = View.VISIBLE
            recyclerview_id.setHasFixedSize(true)
            getListBook()
//            val list = savedInstanceState.getParcelableArrayList<Book>(EXTRA_STATE)
//            recyclerview_id.layoutManager = GridLayoutManager(requireContext(),2)
//            val cardViewDiscoverAdapter = list?.let { CardViewDiscoverAdapter(it, mFragmentTransaction) }
//            recyclerview_id.adapter = cardViewDiscoverAdapter
        }

    }

    private fun getListBook(){
        progressBar.visibility = View.VISIBLE
//        val client = AsyncHttpClient()
        var url = "https://api.wattpad.com:443/v4/stories"
        val request = Request.Builder()
                .header("Authorization", "COmAsfoTl5bHFOoHoKl8uQCo12cA8sl2ytzk2RPu3uRB")
                .url(url)
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                progressBar.visibility = View.INVISIBLE
            }

            override fun onResponse(call: Call, response: Response) {
                var str_response = response.body()!!.string()
                //creating json object
                val json_contact: JSONObject = JSONObject(str_response)
                //creating json array
                var jsonarray_info: JSONArray = json_contact.getJSONArray("stories")
                var size: Int = jsonarray_info.length()
                for(i in 0 until size) {
                    var json_objectdetail = jsonarray_info.getJSONObject(i)
                    var json_object_user = json_objectdetail.getJSONObject("user")
                    var book: Book = Book(
                            id = json_objectdetail.getString("id"),
                            title = json_objectdetail.getString("title"),
                            description = json_objectdetail.getString("description"),
                            penulis = json_object_user.getString("fullname"),
                            image = json_objectdetail.getString("cover"),
                            rating = json_objectdetail.getInt("rating"),
                            numPart = json_objectdetail.getInt("numParts"),
                            part = arrayListOf())
                    var jsonarray_part: JSONArray = json_objectdetail.getJSONArray("parts")
                    for (j in 0 until jsonarray_part.length()){
                        var json_objectpart = jsonarray_part.getJSONObject(j)
                        var part: BookPart = BookPart()
                        part.id = json_objectpart.getInt("id")
                        part.title = json_objectpart.getString("title")
                        part.url = json_objectpart.getString("url")
                        part.rating = json_objectpart.getInt("rating")
                        book.part.add(part)
                    }

                    listBook.add(book)
                }
                runOnUiThread{
                    val mFragmentManager = fragmentManager as FragmentManager
                    val mFragmentTransaction = mFragmentManager.beginTransaction()
                    recyclerview_id.layoutManager = GridLayoutManager(requireContext(),2)
                    val cardViewDiscoverAdapter = CardViewDiscoverAdapter(listBook, mFragmentTransaction)
                    recyclerview_id.adapter = cardViewDiscoverAdapter

                }
                progressBar.visibility = View.INVISIBLE
            }
        })
    }

    fun Fragment?.runOnUiThread(action: () -> Unit ){
        this?:return
        if(!isAdded) return
        activity?.runOnUiThread(action)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
//        outState.putParcelableArrayList(EXTRA_STATE, listBook)
    }

}