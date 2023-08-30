<?php
date_default_timezone_set('Asia/Seoul');						
$dateTime = date("Y-m-d H:i:s");
$servername = "220.69.240.29";
$username = "rasp";
$password = "selab";
$dbname = "greenhouse";
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
	
$image = $_FILES["file"]["name"]??'';
$insert = $conn->query("INSERT into rpiimage (name, created) VALUES ('$image', '$dateTime')");
if($insert){
	move_uploaded_file($_FILES["file"]["tmp_name"],$_FILES["file"]["name"]);
}

?>