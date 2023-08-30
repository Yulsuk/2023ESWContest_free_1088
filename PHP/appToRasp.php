<?php 
 
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 
 
    include('dbCon.php');
 
 
    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");
 
 
    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {
 
        //$id=$_POST['id']??'';
        $controlVal=$_POST['controlVal']??'';
        $operateGreenhouseOpen=$_POST['operateGreenhouseOpen']??'';
        $operateGreenhouseClose=$_POST['operateGreenhouseClose']??'';
        $operateWaterSprayer=$_POST['operateWaterSprayer']??'';        
        $operateVentilator=$_POST['operateVentilator']??'';
        $operateHeatingWire=$_POST['operateHeatingWire']??'';
        $operateWiper=$_POST['operateWiper']??'';
        $cameraStart=$_POST['cameraStart']??'';
        $cameraEnd=$_POST['cameraEnd']??'';
        $selectedCorps=$_POST['selectedCorps']??'';
        $operateWaterSprayerOutside=$_POST['operateWaterSprayerOutside']??'';
        
        if(empty($controlVal)){
            $sql = "SELECT controlVal FROM apptorasp_controlvalue";
            $result = mysqli_query($con, $sql);
            $row = mysqli_fetch_array($result);
            $controlVal = $row[0];
        }
        if(empty($operateGreenhouseOpen)){
            $sql = "SELECT operateGreenhouseOpen FROM apptorasp_controlvalue";
            $result = mysqli_query($con, $sql);
            $row = mysqli_fetch_array($result);
            $operateGreenhouseOpen = $row[0];
        }
        if(empty($operateGreenhouseClose)){
            $sql = "SELECT operateGreenhouseClose FROM apptorasp_controlvalue";
            $result = mysqli_query($con, $sql);
            $row = mysqli_fetch_array($result);
            $operateGreenhouseClose = $row[0];
        }
        if(empty($operateWaterSprayer)){
            $sql = "SELECT operateWaterSprayer FROM apptorasp_controlvalue";
            $result = mysqli_query($con, $sql);
            $row = mysqli_fetch_array($result);
            $operateWaterSprayer = $row[0];
        }
        if(empty($operateVentilator)){
            $sql = "SELECT operateVentilator FROM apptorasp_controlvalue";
            $result = mysqli_query($con, $sql);
            $row = mysqli_fetch_array($result);
            $operateVentilator = $row[0];
        }
        if(empty($operateHeatingWire)){
            $sql = "SELECT operateHeatingWire FROM apptorasp_controlvalue";
            $result = mysqli_query($con, $sql);
            $row = mysqli_fetch_array($result);
            $operateHeatingWire = $row[0];
        }
        if(empty($operateWiper)){
            $sql = "SELECT operateWiper FROM apptorasp_controlvalue";
            $result = mysqli_query($con, $sql);
            $row = mysqli_fetch_array($result);
            $operateWiper = $row[0];
        }
        if(empty($cameraStart)){
            $sql = "SELECT cameraStart FROM apptorasp_controlvalue";
            $result = mysqli_query($con, $sql);
            $row = mysqli_fetch_array($result);
            $timeStart = $row[0];
        }
        if(empty($cameraEnd)){
            $sql = "SELECT cameraEnd FROM apptorasp_controlvalue";
            $result = mysqli_query($con, $sql);
            $row = mysqli_fetch_array($result);
            $timeEnd = $row[0];
        }
        if(empty($selectedCorps)){
            $sql = "SELECT selectedCorps FROM apptorasp_controlvalue";
            $result = mysqli_query($con, $sql);
            $row = mysqli_fetch_array($result);
            $selectedCorps = $row[0];
        }
        if(empty($operateWaterSprayerOutside)){
            $sql = "SELECT operateWaterSprayerOutside FROM apptorasp_controlvalue";
            $result = mysqli_query($con, $sql);
            $row = mysqli_fetch_array($result);
            $operateWaterSprayerOutside = $row[0];
        }
        
 
        if(!isset($errMSG)) 
        {
            try{
                $stmt = $con->prepare(
                "UPDATE apptorasp_controlvalue SET 
                controlVal='$controlVal', operateGreenhouseOpen = '$operateGreenhouseOpen',
                operateGreenhouseClose = '$operateGreenhouseClose', operateWaterSprayer = '$operateWaterSprayer',
                operateVentilator='$operateVentilator',operateHeatingWire='$operateHeatingWire',
                operateWiper='$operateWiper',cameraStart='$cameraStart',
                cameraEnd='$cameraEnd',selectedCorps='$selectedCorps', operateWaterSprayerOutside='$operateWaterSprayerOutside'
                WHERE id = 1"
                );
                
                if($stmt->execute())
                {
                    $successMSG = "update.";
                }
                else
                {
                    $errMSG = "error.";
                }
 
            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
        }
 
    }
 
?>
 
 
<?php 
    if (isset($errMSG)) echo $errMSG;
    if (isset($successMSG)) echo $successMSG;
 
    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");
   
    if( !$android )
    {
?>
    <html>
       <body>
 
            <form action="<?php $_PHP_SELF ?>" method="POST">
                
                controlVal: <input type = "text" name = "controlVal" /><br>
                operateGreenhouseOpen: <input type = "text" name = "operateGreenhouseOpen" /><br>
                operateGreenhouseClose: <input type = "text" name = "operateGreenhouseClose" /><br>
                operateWaterSprayer: <input type = "text" name = "operateWaterSprayer" /><br>
                operateVentilator: <input type = "text" name = "operateVentilator" /><br>
                operateHeatingWire: <input type = "text" name = "operateHeatingWire" /><br>
                operateWiper: <input type = "text" name = "operateWiper" /><br>
                cameraStart: <input type = "text" name = "cameraStart" /><br>
                cameraEnd: <input type = "text" name = "cameraEnd" /><br>
                selectedCorps: <input type = "text" name = "selectedCorps" /><br>
                operateWaterSprayerOutside : <input type = "text" name = "operateWaterSprayerOutside" /><br>
                <input type = "submit" name = "submit" /><br>
            </form>
       
       </body>
    </html>
 
<?php 
    }
?>
