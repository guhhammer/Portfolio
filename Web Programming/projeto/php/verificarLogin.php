

<?php
	
	$login = $_POST["login"];

	$arcName = "$login".".xml";
	
	$checker["status"] = "doesNotExist";
	
	$diretorio = dir("../xml/AllEmails/"); 

	while($arquivo = $diretorio -> read()){

		if($arquivo == $arcName){

			$checker["status"] = "alreadyExists";  

		}

	}

	echo json_encode($checker);

?>