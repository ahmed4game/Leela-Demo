<?php
include 'phppack/connection.php'; 
//echo $_SERVER['REMOTE_ADDR'];

		$varip=$_SERVER['REMOTE_ADDR'];

		$sqlquery = "SELECT GuestFullName FROM Guest where GuestStatus='A' and GuestRoomNo=(select room from stbmap where ip='".$varip ."') ORDER by rModifiedOn ASC LIMIT 1";		
	
		$obj_connection = new dbconnection();	
		$resultset_family = mysqli_query( $obj_connection->connection() ,$sqlquery);
		
		if (! $resultset_family)
		 // die('Database error: ' . mysqli_error($obj_connection->connection()));
		//echo 'Database error: ' . mysqli_error($obj_connection->connection());
			echo '';

		if($row = mysqli_fetch_assoc($resultset_family))
		{	
			echo $row['GuestFullName'];
			
		}
		else
			echo '';

?>
