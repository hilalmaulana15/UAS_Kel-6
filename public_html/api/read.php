<?php
include 'koneksi.php';

$data = $conn->query("SELECT * FROM barang");
$result = array();

while($row = $data->fetch_assoc()){
    $result[] = $row;
}

echo json_encode($result);
?>
