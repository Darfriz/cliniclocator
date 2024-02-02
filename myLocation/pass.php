<?php
    $con = mysqli_connect("localhost", "root", "", "location");
    $latitude = $_POST['lat'];
    $longitude = $_POST['lng'];
    $user_agent = $_SERVER['HTTP_USER_AGENT'];



    if ($con) {
        $sql = "INSERT INTO gpslocation (User_Agent, lat, lng) VALUES ('".$user_agent."', '".$latitude."', '".$longitude."')";
        if(mysqli_query($con, $sql)){
            echo "success";
        } else echo "Location Send failed";
    }else echo "Database connection failed";

?>