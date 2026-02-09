

<?php 

	$rascunho_codigo = intval($_POST["rascunho_codigo"]);

	$destinatario = $_POST["destinatario"];

	$emailCc = $_POST["emailCc"];

	$assunto = $_POST["assunto"];
	$mensagem = $_POST["mensagem"];


	$pathRascunho = $_POST["pathRascunho"];

	// Quem enviou.
	$xml = new DOMDocument();

	$xml_email = $xml->createElement("email");

	$cc = $rascunho_codigo."";
	$xml_codigo_cc = $xml->createElement("codigo","$cc");
	$xml_destinatario = $xml->createElement("destinatario","$destinatario");
	$xml_emailCC = $xml->createElement("emailCc","$emailCc");
	$xml_assunto = $xml->createElement("assunto","$assunto");
	$xml_mensagem = $xml->createElement("mensagem","$mensagem");

	$xml_email->appendChild($xml_codigo_cc);
	$xml_email->appendChild($xml_destinatario);
	$xml_email->appendChild($xml_emailCC);
	$xml_email->appendChild($xml_assunto);
	$xml_email->appendChild($xml_mensagem);

	$xml->appendChild($xml_email);

	$xml->save($pathRascunho);



	$confirm["status"] = "ok";

	echo json_encode($confirm);

?>

