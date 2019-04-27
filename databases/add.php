<?php
$host = "localhost";
$username = "root";
$password = '';
$database = "registration";
$conn = mysqli_connect($host,$username,$password,$database);

if ($conn){
    echo "Connection successful";
}else{
    echo "Something went wrong".mysqli_error($conn);
}


extract($_REQUEST);

$sql = "INSERT INTO `students`(`name`, `course`) VALUES ('$name', '$course')";

mysqli_query($conn, $sql);

mysqli_close($conn);

?>