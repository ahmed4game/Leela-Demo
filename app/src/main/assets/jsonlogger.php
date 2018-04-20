<?php 
include 'phppack/connection.php'; 
$logmessfromstb = $_GET['strlog'];
//$name = $_POST['name'];
//print_r(json_encode($_POST));

$varip=$_SERVER['REMOTE_ADDR'];

		$sqlquery = "select room from stbmap where ip='".$varip ."'";
	
		$obj_connection = new dbconnection();	
		$resultset_stb = mysqli_query( $obj_connection->connection() ,$sqlquery);
		
		if (! $resultset_stb)
		   die('Database error: ' . mysqli_error());
	   
		$varexcep="";
		if ($row = mysqli_fetch_assoc($resultset_stb))
		{	
			writetologfile($logmessfromstb,$row['room']);
			if (strpos($logmessfromstb,"Exception"))
			{
				$varexcep = preg_split("/\\r\\n|\\r|\\n/", $logmessfromstb);
				$logmessfromstb=str_replace("'","",$varexcep[0]);
			}
			//writetologfile($logmessfromstb,$row['room']);
			writetodb($logmessfromstb,$varip,$row['room']);
		}
		
	


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