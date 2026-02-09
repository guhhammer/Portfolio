


<?php 


	$email = $_POST["email"];

	$arcName = "$email".".xml";


	$verify["status"] = "doesNotExist";

	$diretorio = dir("../xml/AllEmails/");

	while($arquivo = $diretorio -> read()){

		if($arquivo == $arcName){

			$verify["status"] = "alreadyExists";

		}

	}

	echo json_encode($verify);

?>



