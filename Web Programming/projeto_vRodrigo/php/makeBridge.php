

<?php 

	$destinatario = $_POST["dest"];
	$emailCc = $_POST["cc"];
	$assunto = $_POST["ass"];
	$mensagem = $_POST["msg"];

	$xml = new DOMDocument();

	$xml_email = $xml->createElement("email");
	$xml_dest = $xml->createElement("destinatario","$destinatario");
	$xml_cc = $xml->createElement("emailCc","$emailCc");
	$xml_ass = $xml->createElement("assunto","$assunto");
	$xml_msg = $xml->createElement("mensagem","$mensagem");

	$xml_email->appendChild($xml_dest);
	$xml_email->appendChild($xml_cc);
	$xml_email->appendChild($xml_ass);
	$xml_email->appendChild($xml_msg);

	$xml->appendChild($xml_email);

	$xml->save("../xml/bridge/actual.xml");

	$received["status"] = "ok";

	echo json_encode($received);

?>