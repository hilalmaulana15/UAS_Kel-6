package com.example.crudtoko

data class Barang(
    val id: Int,
    val nama: String,
    val kategori: String,
    val harga: Int,
    val stok: Int
)
