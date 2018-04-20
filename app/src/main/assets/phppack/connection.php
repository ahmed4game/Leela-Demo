<?php
		
class dbconnection
{
public function connection()
{	
	$dbhost = 'localhost';
		$dbuser = 'root';
		$dbpass = 'Passw0rd';
		$dbname ='ithaca';
		$conn = mysqli_connect($dbhost, $dbuser, $dbpass,$dbname);
		if(! $conn )
		{
  			die('Could not connect: ' . mysql_error());
		}
		//mysqli_select_db('ithaca', $conn);
		return $conn;
}

}

?>