<?php
$conn = new mysqli(
    "localhost",
    "klmpk_6",
    "kelompok6sttc",
    "appocaly_Toko"
);

if ($conn->connect_error) {
    die("Koneksi gagal: " . $conn->connect_error);
}
?>
