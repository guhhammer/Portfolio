
<?php 

	$change = $_POST["change"];

	$path = $_POST["path"];

	$which = $_POST["color"];

	$xml_objeto = simplexml_load_string(file_get_contents($path));

	$xml_code = $xml_objeto->code[0]."";
	$xml_recipient = $xml_objeto->recipient[0]."";
	$xml_emailCc = $xml_objeto->emailCc[0]."";
	$xml_subject = $xml_objeto->subject[0]."";
	$xml_message = $xml_objeto->message[0]."";
	$xml_greenCode = $xml_objeto->greenCode[0]."";
	$xml_yellowCode = $xml_objeto->yellowCode[0]."";


	$xmlNovo = new DOMDocument();

	$email = $xmlNovo->createElement("email");
	$cod = $xmlNovo->createElement("code",$xml_code);
	$dest = $xmlNovo->createElement("recipient",$xml_recipient);
	$cc = $xmlNovo->createElement("emailCc",$xml_emailCc);
	$ass = $xmlNovo->createElement("subject",$xml_subject);
	$msg = $xmlNovo->createElement("message",$xml_message);

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