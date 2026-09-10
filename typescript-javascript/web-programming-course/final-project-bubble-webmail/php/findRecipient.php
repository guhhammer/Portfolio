


<?php 


	$email = $_POST["email"];

	$arcName = "$email".".xml";


	$verify["status"] = "doesNotExist";

	$directory = dir("../xml/AllEmails/");

	while($file = $directory -> read()){

		if($file == $arcName){

			$verify["status"] = "alreadyExists";

		}

	}

	echo json_encode($verify);

?>



