<?php
class DbaseService
{

function insertUpdateDelete($querystring,$logmsg='')
{
				$obj_connection = new dbconnection();
				$date=date('Y-m-d H:i:s');
				
				if (mysql_query($querystring, $obj_connection->connection()))
				 {
    				//echo "New record created successfully";
					} 
				else {
    				//echo "Error: " . $sql . "<br>" . mysqli_error($conn);
					writetologfile("\r\n".$date."------------ insertion query is  " .$querystring ."\r\n Error -----" . mysql_error() ,"mysqlerror");
					}

				mysql_close($obj_connection->connection());
	
}

function insertUpdateDeleteUnique_Identity($querystring,$logmsg='')
{
				$obj_connection = new dbconnection();
				$date=date('Y-m-d H:i:s');
				
				if (mysql_query($querystring, $obj_connection->connection()))
				 {
					$last_id = mysql_insert_id($obj_connection->connection());
    				//echo "New record created successfully";
					return $last_id;
					} 
				else {
    				//echo "Error: " . $sql . "<br>" . mysqli_error($conn);
					writetologfile("\r\n".$date."------------ insertion query is  " .$querystring ."\r\n Error ---" . mysql_error() ,"mysqlerror");
					}

				mysql_close($obj_connection->connection());
	
}
  //Returns the result of the Aggregate(single value-column) function in the Query
  //Argument should be only a Select Query with Aggregate Function
function scalarquery($querystring,$logmsg='')
{

				$obj_connection = new dbconnection();
				$date=date('Y-m-d H:i:s');
				
				$result = mysqli_query( $obj_connection->connection() ,$querystring);
				if($result) {
				$id = $this->myfn_result($result,0,0);
				return $id;
				}
				else {
    				//echo "Error: " . $sql . "<br>" . mysqli_error($conn);
					writetologfile("\r\n".$date."------------ insertion query is  " .$querystring ."\r\n Error ---" . mysql_error() ,"mysqlerror");
					}
    			
			mysqli_close($obj_connection->connection());

}

function myfn_result($res,$row=0,$col=0){ 
    $numrows = mysqli_num_rows($res); 
    if ($numrows && $row <= ($numrows-1) && $row >=0){
        mysqli_data_seek($res,$row);
        $resrow = (is_numeric($col)) ? mysqli_fetch_row($res) : mysqli_fetch_assoc($res);
        if (isset($resrow[$col])){
            return $resrow[$col];
        }
    }
    return 0;
}

}

?>