<?php
include 'koneksi.php';

$id = $_POST['id'];
$conn->query("DELETE FROM barang WHERE id='$id'");
echo "success";
?>
