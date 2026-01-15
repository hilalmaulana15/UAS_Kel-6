package com.example.crudtoko

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import java.io.IOException

class BarangAdapter(
    private var barangList: ArrayList<Barang>,
    private val context: Context
) : RecyclerView.Adapter<BarangAdapter.BarangViewHolder>() {

    inner class BarangViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvKategori: TextView = itemView.findViewById(R.id.tvKategori)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHarga)
        val tvStok: TextView = itemView.findViewById(R.id.tvStok)
        val btnEdit: ImageView = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageView = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_barang, parent, false)
        return BarangViewHolder(view)
    }

    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val barang = barangList[position]

        holder.tvNama.text = "Nama: ${barang.nama}"
        holder.tvKategori.text = "Kategori: ${barang.kategori}"
        holder.tvHarga.text = "Harga: Rp${barang.harga}"
        holder.tvStok.text = "Stok: ${barang.stok}"

        // EDIT
        holder.btnEdit.setOnClickListener {
            val intent = Intent(context, UpdateBarangActivity::class.java)
            intent.putExtra("id_barang", barang.id)
            intent.putExtra("nama_barang", barang.nama)
            intent.putExtra("kategori", barang.kategori)
            intent.putExtra("harga", barang.harga)
            intent.putExtra("stok", barang.stok)
            context.startActivity(intent)
        }

        // DELETE
        holder.btnDelete.setOnClickListener {
            deleteBarang(barang.id, position)
        }
    }

    override fun getItemCount(): Int = barangList.size

    private fun deleteBarang(id: Int, position: Int) {

        val client = OkHttpClient()

        val body = FormBody.Builder()
            .add("id", id.toString())
            .build()

        val request = Request.Builder()
            .url("https://appocalypse.my.id/Toko_api.php?action=delete")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {

                    (context as Activity).runOnUiThread {
                        barangList.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, barangList.size)
                    }

                }
            }
        })
    }



    fun refreshData(newList: ArrayList<Barang>) {
        barangList = newList
        notifyDataSetChanged()
    }
}
