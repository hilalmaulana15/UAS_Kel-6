package com.example.crudtoko

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "toko.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "barang"
        private const val COLUMN_ID = "id_barang"
        private const val COLUMN_NAMA = "nama_barang"
        private const val COLUMN_KATEGORI = "kategori"
        private const val COLUMN_HARGA = "harga"
        private const val COLUMN_STOK = "stok"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NAMA TEXT," +
                "$COLUMN_KATEGORI TEXT," +
                "$COLUMN_HARGA INTEGER," +
                "$COLUMN_STOK INTEGER)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Fungsi untuk menambahkan data barang (CREATE)
    fun insertBarang(barang: Barang) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAMA, barang.nama)
            put(COLUMN_KATEGORI, barang.kategori)
            put(COLUMN_HARGA, barang.harga)
            put(COLUMN_STOK, barang.stok)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // Fungsi untuk membaca semua data barang
    fun getAllBarang(): List<Barang> {
        val barangList = mutableListOf<Barang>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val nama = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA))
                val kategori = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_KATEGORI))
                val harga = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HARGA))
                val stok = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STOK))

                val barang = Barang(id, nama, kategori, harga, stok)
                barangList.add(barang)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return barangList
    }

    // Fungsi Update Barang
    fun updateBarang(oldId: Int, barang: Barang) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAMA, barang.nama)
            put(COLUMN_KATEGORI, barang.kategori)
            put(COLUMN_HARGA, barang.harga)
            put(COLUMN_STOK, barang.stok)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(oldId.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    // Fungsi Delete Barang
    fun deleteBarang(id: Int) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

}



