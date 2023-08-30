<?php
	include("dbCon.php");
    //$con = mysqli_connect('220.69.240.29', 'rasp', 'selab', 'greenhouse');
    /*if($con) {
    	echo "Connected";
    }
	*/

	$sql = "SELECT pm1_0,pm2_5,pm10_0 FROM rasptoapp_microdust WHERE id > (SELECT MAX(id)-5000 FROM rasptoapp_tempandhum)";
	$result = mysqli_query($con, $sql);
	$result_arr = array();
	while($row = mysqli_fetch_array($result)){
		array_push($result_arr, array('pm1_0'=>$row[0],'pm2_5'=>$row[1], 'pm10_0'=>$row[2]));
	}
	echo json_encode(array("raspToAppMicrodust"=>$result_arr), JSON_UNESCAPED_UNICODE);

    mysqli_close($con);
?>