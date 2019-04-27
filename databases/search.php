<?php
$host = "localhost";
$username = "root";
$password = '';
$database = "registration";
$conn = mysqli_connect($host,$username,$password,$database);

// if ($conn){
//     echo "Connection successful";
// }else{
//     echo "Something went wrong".mysqli_error($conn);
// }


extract($_REQUEST);

$sql = "SELECT `student_id`, `name`, `course`, `doA` FROM `students` WHERE `name`='$search'";

$result = mysqli_query($conn, $sql);
//check for empty resultset
if(mysqli_num_rows($result) > 0){
    $data = mysqli_fetch_assoc($result);
    $json = json_encode($data);
    echo $json;
}else{
    $error->code = "404";
    $error->message = "No such recod exists!";
    $e = json_encode($error);
    echo $e;
}

mysqli_close($conn);


?>