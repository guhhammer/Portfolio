

<?php 	


	$email = $_POST["login"];

	$xml = new DOMDocument();
	
	$xml_user = $xml->createElement("user");

	$xml_email = $xml->createElement("email", "$email");

	$xml_user->appendChild($xml_email);

	$xml->appendChild($xml_user);


	$xml->save("../xml/UsuarioAtual/atual.xml");	


?>