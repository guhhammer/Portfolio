

<?php 

	$draft_code = intval($_POST["draft_code"]);

	$recipient = $_POST["recipient"];

	$emailCc = $_POST["emailCc"];

	$subject = $_POST["subject"];
	$message = $_POST["message"];


	$draftPath = $_POST["draftPath"];

	// Quem enviou.
	$xml = new DOMDocument();

	$xml_email = $xml->createElement("email");

	$cc = $draft_code."";
	$xml_code_cc = $xml->createElement("code","$cc");
	$xml_recipient = $xml->createElement("recipient","$recipient");
	$xml_emailCC = $xml->createElement("emailCc","$emailCc");
	$xml_subject = $xml->createElement("subject","$subject");
	$xml_message = $xml->createElement("message","$message");

	$xml_email->appendChild($xml_code_cc);
	$xml_email->appendChild($xml_recipient);
	$xml_email->appendChild($xml_emailCC);
	$xml_email->appendChild($xml_subject);
	$xml_email->appendChild($xml_message);

	$xml->appendChild($xml_email);

	$xml->save($draftPath);



	$confirm["status"] = "ok";

	echo json_encode($confirm);

?>

