<?php
include 'koneksi.php';

$nama = $_POST['nama'];
$kategori = $_POST['kategori'];
$harga = $_POST['harga'];
$stok = $_POST['stok'];

$conn->query("INSERT INTO barang VALUES ('','$nama','$kategori','$harga','$stok')");
echo "success";
?>
