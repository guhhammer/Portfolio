
<?php 

	$change = $_POST["change"];

	$path = $_POST["path"];

	$which = $_POST["color"];

	$xml_objeto = simplexml_load_string(file_get_contents($path));

	$xml_codigo = $xml_objeto->codigo[0]."";
	$xml_destinatario = $xml_objeto->destinatario[0]."";
	$xml_emailCc = $xml_objeto->emailCc[0]."";
	$xml_assunto = $xml_objeto->assunto[0]."";
	$xml_mensagem = $xml_objeto->mensagem[0]."";
	$xml_greenCode = $xml_objeto->greenCode[0]."";
	$xml_yellowCode = $xml_objeto->yellowCode[0]."";


	$xmlNovo = new DOMDocument();

	$email = $xmlNovo->createElement("email");
	$cod = $xmlNovo->createElement("codigo",$xml_codigo);
	$dest = $xmlNovo->createElement("destinatario",$xml_destinatario);
	$cc = $xmlNovo->createElement("emailCc",$xml_emailCc);
	$ass = $xmlNovo->createElement("assunto",$xml_assunto);
	$msg = $xmlNovo->createElement("mensagem",$xml_mensagem);

	if($which == "green"){
		$gCode = $xmlNovo->createElement("greenCode",$change);
		$yCode = $xmlNovo->createElement("yellowCode",$xml_yellowCode);
	}
	if($which == "yellow"){
		$gCode = $xmlNovo->createElement("greenCode",$xml_greenCode);
		$yCode = $xmlNovo->createElement("yellowCode",$change);
	}

	$email->appendChild($cod);
	$email->appendChild($dest);
	$email->appendChild($cc);
	$email->appendChild($ass);
	$email->appendChild($msg);
	$email->appendChild($gCode);
	$email->appendChild($yCode);

	$xmlNovo->appendChild($email);

	$xmlNovo->save($path);

	$confirm["status"] = "ok";	

	echo json_encode($confirm);


?>