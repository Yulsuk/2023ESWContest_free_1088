<?php
	include("dbCon.php");
    //$con = mysqli_connect('220.69.240.29', 'rasp', 'selab', 'greenhouse');
    /*if($con) {
    	echo "Connected";
    }
	*/

	$sql = "SELECT temperature, humidity FROM rasptoapp_tempandhum WHERE id > (SELECT MAX(id)-5000 FROM rasptoapp_tempandhum)";
	$result = mysqli_query($con, $sql);
	$result_arr = array();
	while($row = mysqli_fetch_array($result)){
		array_push($result_arr, array('temperature'=>$row[0],'humidity'=>$row[1]));
	}
	echo json_encode(array("raspToApp"=>$result_arr), JSON_UNESCAPED_UNICODE);

    mysqli_close($con);
?>