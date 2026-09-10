

<?php 

	$recipient = $_POST["recipient"];
	$recipient_code = intval($_POST["recipient_code"]);	

	$emailCc = $_POST["emailCc"];
	$emailCc_code = intval($_POST["emailCc_code"]);

	$subject = $_POST["subject"];
	$message = $_POST["message"];


	$pathFrom = $_POST["pathFrom"];

	$pathTo = $_POST["pathTo"];


	// Quem recebe.
	$xml = new DOMDocument();

	$xml_email = $xml->createElement("email");

	$xml_code_recipient = $xml->createElement("code","$recipient_code");
	$xml_recipient = $xml->createElement("recipient","$recipient");
	$xml_emailCC = $xml->createElement("emailCc","$emailCc");
	$xml_subject = $xml->createElement("subject","$subject");
	$xml_message = $xml->createElement("message","$message");
	$xml_greenCode = $xml->createElement("greenCode", "1");
	$xml_yellowCode = $xml->createElement("yellowCode", "0");

	$xml_email->appendChild($xml_code_recipient);
	$xml_email->appendChild($xml_recipient);
	$xml_email->appendChild($xml_emailCC);
	$xml_email->appendChild($xml_subject);
	$xml_email->appendChild($xml_message);
	$xml_email->appendChild($xml_greenCode);
	$xml_email->appendChild($xml_yellowCode);

	$xml->appendChild($xml_email);

	$xml->save($pathTo);


	// Quem enviou.
	$xml2 = new DOMDocument();

	$xml_email2 = $xml2->createElement("email");

	$xml_code_cc = $xml2->createElement("code","$emailCc_code");
	$xml_recipient2 = $xml2->createElement("recipient","$recipient");
	$xml_emailCC2 = $xml2->createElement("emailCc","$emailCc");
	$xml_subject2 = $xml2->createElement("subject","$subject");
	$xml_message2 = $xml2->createElement("message","$message");
	$xml_greenCode2 = $xml2->createElement("greenCode", "1");
	$xml_yellowCode2 = $xml2->createElement("yellowCode", "0");

	$xml_email2->appendChild($xml_code_cc);
	$xml_email2->appendChild($xml_recipient2);
	$xml_email2->appendChild($xml_emailCC2);
	$xml_email2->appendChild($xml_subject2);
	$xml_email2->appendChild($xml_message2);
	$xml_email2->appendChild($xml_greenCode2);
	$xml_email2->appendChild($xml_yellowCode2);

	$xml2->appendChild($xml_email2);

	$xml2->save($pathFrom);



	$confirm["status"] = "ok";

	echo json_encode($confirm);

?>

