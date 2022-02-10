

<?php 

	$destinatario = $_POST["destinatario"];
	$destinatario_codigo = intval($_POST["destinatario_codigo"]);	

	$emailCc = $_POST["emailCc"];
	$emailCc_codigo = intval($_POST["emailCc_codigo"]);

	$assunto = $_POST["assunto"];
	$mensagem = $_POST["mensagem"];


	$pathFrom = $_POST["pathFrom"];

	$pathTo = $_POST["pathTo"];


	// Quem recebe.
	$xml = new DOMDocument();

	$xml_email = $xml->createElement("email");

	$xml_codigo_destinatario = $xml->createElement("codigo","$destinatario_codigo");
	$xml_destinatario = $xml->createElement("destinatario","$destinatario");
	$xml_emailCC = $xml->createElement("emailCc","$emailCc");
	$xml_assunto = $xml->createElement("assunto","$assunto");
	$xml_mensagem = $xml->createElement("mensagem","$mensagem");
	$xml_greenCode = $xml->createElement("greenCode", "1");
	$xml_yellowCode = $xml->createElement("yellowCode", "0");

	$xml_email->appendChild($xml_codigo_destinatario);
	$xml_email->appendChild($xml_destinatario);
	$xml_email->appendChild($xml_emailCC);
	$xml_email->appendChild($xml_assunto);
	$xml_email->appendChild($xml_mensagem);
	$xml_email->appendChild($xml_greenCode);
	$xml_email->appendChild($xml_yellowCode);

	$xml->appendChild($xml_email);

	$xml->save($pathTo);


	// Quem enviou.
	$xml2 = new DOMDocument();

	$xml_email2 = $xml2->createElement("email");

	$xml_codigo_cc = $xml2->createElement("codigo","$emailCc_codigo");
	$xml_destinatario2 = $xml2->createElement("destinatario","$destinatario");
	$xml_emailCC2 = $xml2->createElement("emailCc","$emailCc");
	$xml_assunto2 = $xml2->createElement("assunto","$assunto");
	$xml_mensagem2 = $xml2->createElement("mensagem","$mensagem");
	$xml_greenCode2 = $xml2->createElement("greenCode", "1");
	$xml_yellowCode2 = $xml2->createElement("yellowCode", "0");

	$xml_email2->appendChild($xml_codigo_cc);
	$xml_email2->appendChild($xml_destinatario2);
	$xml_email2->appendChild($xml_emailCC2);
	$xml_email2->appendChild($xml_assunto2);
	$xml_email2->appendChild($xml_mensagem2);
	$xml_email2->appendChild($xml_greenCode2);
	$xml_email2->appendChild($xml_yellowCode2);

	$xml2->appendChild($xml_email2);

	$xml2->save($pathFrom);



	$confirm["status"] = "ok";

	echo json_encode($confirm);

?>

