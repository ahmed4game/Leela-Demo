<?php 
include 'phppack/connection.php'; 
$logmessfromstb = $_GET['strlog'];
//$name = $_POST['name'];
//print_r(json_encode($_POST));

$varip=$_SERVER['REMOTE_ADDR'];

		writetologfile($logmessfromstb,$varip);
	if ($varip=="10.90.0.0" || $varip=="10.90.7.252")
		echo "yes";
	if ($logmessfromstb =="USBNW" && $varip=="172.16.63.132")
		echo "reboot";
	
	


function writetodb($stringtowrite, $rmip, $roomnm)
{		
		$obj_connection = new dbconnection();	
		$logdate = date('Y-m-d H:i:s');
		$logtable_insert = "INSERT INTO Roomlogs (roomno, rmip, logmess,  logtime) 
									VALUES ('$roomnm','$rmip', '$stringtowrite','$logdate')"; 
		$logtable_result = mysqli_query( $obj_connection->connection() ,$logtable_insert);
		mysqli_close($obj_connection->connection());
}

function writetologfile($stringtowrite, $filename)
{
	$logdate = date('Y-m-d H:i:s');
	$date = date('d-m-y_');
	$folderdate = date('d-m-y');
	if (!file_exists('log/'.$folderdate))	
	{
    	mkdir('log/'.$folderdate, 0777, true);
	}

	$myfile = fopen('log/'.$folderdate.'/'.$date .$filename.".txt", "a"); //or 
	if (!$myfile)
	{
		die("Unable to open file!");
	}
	fwrite($myfile, "\r\n".$logdate." ====".$stringtowrite);
	fclose($myfile);
}
 
?>