<?php
include("dbCon.php");
#$result = $con->query("SELECT * FROM rpiimage where date(created)='$cur_date' order by created DESC ");
$result = $con->query("SELECT * FROM rpiimage order by created DESC ");
 While($imgData = $result->fetch_assoc()){        
                        			
      echo '<div><img width="100%" src="http://220.69.240.29/'.$imgData['name'].'"/>';
      echo "<br>". $imgData['created'] . "</div>" ;
	  echo "<br><br><br><br>";
 }
?>