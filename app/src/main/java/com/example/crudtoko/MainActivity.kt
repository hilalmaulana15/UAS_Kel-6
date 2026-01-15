package com.example.crudtoko

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudtoko.databinding.ActivityMainBinding
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BarangAdapter
    private val listBarang = ArrayList<Barang>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = BarangAdapter(listBarang, this)

        binding.recyclerViewBarang.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewBarang.adapter = adapter

        binding.btnTambah.setOnClickListener {
            startActivity(Intent(this, AddBarangActivity::class.java))
        }

        loadData()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://appocalypse.my.id/Toko_api.php?action=read")
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Gagal load data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string() ?: "[]"
                val array = JSONArray(json)

                listBarang.clear()

                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)

                    listBarang.add(
                        Barang(
                            obj.getInt("id"),        // FIX: Int
                            obj.getString("nama"),
                            obj.getString("kategori"),
                            obj.getInt("harga"),     // FIX: Int
                            obj.getInt("stok")       // FIX: Int
                        )
                    )
                }

                runOnUiThread {
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }
}
