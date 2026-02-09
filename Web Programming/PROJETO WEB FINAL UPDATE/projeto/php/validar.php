
<?php

	$email = $_POST["email"];
	$senha = $_POST["senha"];


	$dir = "../xml/AllEmails/";
	$diretorio = dir($dir);

	$retorno["status"] = "emailNotFounded";
	$retorno["password"] = "_";
	
	while($file = $diretorio -> read()){
		
		if($file == $email.".xml"){

			$retorno["status"] = "emailfounded";

			$xml_string = file_get_contents($dir.$email.".xml");

			$xml_objeto = simplexml_load_string($xml_string);

			$xml_senha = $xml_objeto->senha[0];
		
			if($senha == $xml_senha){  $retorno["password"] = "match"; }
			else{  $retorno["password"] = "no match";  }

		}
	}	
	
	echo json_encode($retorno);

?>
