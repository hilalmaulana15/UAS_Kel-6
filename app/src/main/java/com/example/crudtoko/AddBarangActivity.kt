package com.example.crudtoko

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.io.IOException

class AddBarangActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_barang)

        val namaEditText = findViewById<EditText>(R.id.namaEditText)
        val kategoriEditText = findViewById<EditText>(R.id.kategoriEditText)
        val hargaEditText = findViewById<EditText>(R.id.hargaEditText)
        val stokEditText = findViewById<EditText>(R.id.stokEditText)
        val saveButton = findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            val nama = namaEditText.text.toString()
            val kategori = kategoriEditText.text.toString()
            val harga = hargaEditText.text.toString()
            val stok = stokEditText.text.toString()

            if (nama.isNotEmpty() && kategori.isNotEmpty()) {
                simpanBarang(nama, kategori, harga, stok)
            } else {
                Toast.makeText(this, "Harap isi semua data!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun simpanBarang(nama:String, kategori:String, harga:String, stok:String) {

        val client = OkHttpClient()

        val body = FormBody.Builder()
            .add("nama", nama)
            .add("kategori", kategori)
            .add("harga", harga)
            .add("stok", stok)
            .build()

        val request = Request.Builder()
            .url("https://appocalypse.my.id/Toko_api.php?action=create")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@AddBarangActivity, "Gagal menyimpan", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    Toast.makeText(this@AddBarangActivity, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        })
    }
}
