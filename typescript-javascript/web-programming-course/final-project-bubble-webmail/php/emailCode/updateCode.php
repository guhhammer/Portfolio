

<?php 

	$path = $_POST["path"];

	$old_cod = simplexml_load_string(file_get_contents($path))->value[0]."";


	$xml_novo = new DOMDocument();

	$xml_code = $xml_novo->createElement("code");

	$novo_value = (intval($old_cod)+1);

	$str_value = $novo_value."";

	$xml_value = $xml_novo->createElement("value", "$str_value");


	$xml_code->appendChild($xml_value);

	$xml_novo->appendChild($xml_code);

	$xml_novo->save($path);

	$confirm["status"] = "ok";

	echo json_encode($confirm);

?>
