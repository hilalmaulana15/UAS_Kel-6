package com.example.crudtoko

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.io.IOException

class UpdateBarangActivity : AppCompatActivity() {

    private var barangId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_barang)

        // Ambil data dari Intent
        barangId = intent.getStringExtra("id_barang") ?: ""
        val namaAwal = intent.getStringExtra("nama_barang") ?: ""
        val kategoriAwal = intent.getStringExtra("kategori") ?: ""
        val hargaAwal = intent.getStringExtra("harga") ?: ""
        val stokAwal = intent.getStringExtra("stok") ?: ""

        val namaEditText = findViewById<EditText>(R.id.namaEditText)
        val kategoriEditText = findViewById<EditText>(R.id.kategoriEditText)
        val hargaEditText = findViewById<EditText>(R.id.hargaEditText)
        val stokEditText = findViewById<EditText>(R.id.stokEditText)
        val saveButton = findViewById<Button>(R.id.saveButton)

        // Isi data lama
        namaEditText.setText(namaAwal)
        kategoriEditText.setText(kategoriAwal)
        hargaEditText.setText(hargaAwal)
        stokEditText.setText(stokAwal)

        saveButton.text = "Perbarui Data"

        saveButton.setOnClickListener {
            val nama = namaEditText.text.toString()
            val kategori = kategoriEditText.text.toString()
            val harga = hargaEditText.text.toString()
            val stok = stokEditText.text.toString()

            if (nama.isNotEmpty() && kategori.isNotEmpty()) {
                updateBarang(barangId, nama, kategori, harga, stok)
            } else {
                Toast.makeText(this, "Lengkapi data!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateBarang(id:String, nama:String, kategori:String, harga:String, stok:String) {

        val client = OkHttpClient()

        val body = FormBody.Builder()
            .add("id", id)
            .add("nama", nama)
            .add("kategori", kategori)
            .add("harga", harga)
            .add("stok", stok)
            .build()

        val request = Request.Builder()
            .url("https://appocalypse.my.id/Toko_api.php?action=update")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@UpdateBarangActivity, "Gagal update", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    Toast.makeText(this@UpdateBarangActivity, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        })
    }
}
