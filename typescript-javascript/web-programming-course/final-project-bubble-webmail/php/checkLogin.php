

<?php
	
	$login = $_POST["login"];

	$arcName = "$login".".xml";
	
	$checker["status"] = "doesNotExist";
	
	$directory = dir("../xml/AllEmails/"); 

	while($file = $directory -> read()){

		if($file == $arcName){

			$checker["status"] = "alreadyExists";  

		}

	}

	echo json_encode($checker);

?>