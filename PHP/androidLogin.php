<?php
    include("dbCon.php");
    mysqli_query($con, 'SET NAMES utf8');
	
	
    $userID = $_POST["userID"]??'';
    $userPassword = $_POST["userPassword"]??'';
    
    $statement = mysqli_prepare($con, "SELECT userID, userPassword FROM userInfo WHERE userID = ? AND userPassword = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $userPassword);
    mysqli_stmt_execute($statement);
 
    mysqli_stmt_bind_result($statement, $userID, $userPassword);
 
    $response = array();
    $response["success"] = false;
 
    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["userID"] = $userID;
        $response["userPassword"] = $userPassword;       
    }
 
    echo json_encode($response);
 