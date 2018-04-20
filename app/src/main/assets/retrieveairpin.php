<?php
include 'phppack/connection.php'; 
//echo $_SERVER['REMOTE_ADDR'];

		$varip=$_SERVER['REMOTE_ADDR'];


		$sqlquery = "select room,airpinpwd from stbmap where ip='".$varip ."'";
	
		$obj_connection = new dbconnection();	
		$resultset_family = mysqli_query( $obj_connection->connection() ,$sqlquery);
		
		if (! $resultset_family)
		   //die('Database error: ' . mysqli_error());
			echo '';

		while($row = mysqli_fetch_assoc($resultset_family))
		{	
			echo $row['room'] .",".$row['airpinpwd'];
			
		}

?>