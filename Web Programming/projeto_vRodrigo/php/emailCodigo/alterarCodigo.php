

<?php 

	$path = $_POST["path"];

	$old_cod = simplexml_load_string(file_get_contents($path))->valor[0]."";


	$xml_novo = new DOMDocument();

	$xml_codigo = $xml_novo->createElement("codigo");

	$novo_valor = (intval($old_cod)+1);

	$str_valor = $novo_valor."";

	$xml_valor = $xml_novo->createElement("valor", "$str_valor");


	$xml_codigo->appendChild($xml_valor);

	$xml_novo->appendChild($xml_codigo);

	$xml_novo->save($path);

	$confirm["status"] = "ok";

	echo json_encode($confirm);

?>
