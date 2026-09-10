
<?php

	$email = $_POST["email"];
	$password = $_POST["password"];


	$dir = "../xml/AllEmails/";
	$directory = dir($dir);

	$result["status"] = "emailNotFounded";
	$result["password"] = "_";
	
	while($file = $directory -> read()){
		
		if($file == $email.".xml"){

			$result["status"] = "emailfounded";

			$xml_string = file_get_contents($dir.$email.".xml");

			$xml_objeto = simplexml_load_string($xml_string);

			$xml_password = $xml_objeto->password[0];
		
			if($password == $xml_password){  $result["password"] = "match"; }
			else{  $result["password"] = "no match";  }

		}
	}	
	
	echo json_encode($result);

?>
