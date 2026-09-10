

<?php 

	$recipient = $_POST["dest"];
	$emailCc = $_POST["cc"];
	$subject = $_POST["ass"];
	$message = $_POST["msg"];

	$xml = new DOMDocument();

	$xml_email = $xml->createElement("email");
	$xml_dest = $xml->createElement("recipient","$recipient");
	$xml_cc = $xml->createElement("emailCc","$emailCc");
	$xml_ass = $xml->createElement("subject","$subject");
	$xml_msg = $xml->createElement("message","$message");

	$xml_email->appendChild($xml_dest);
	$xml_email->appendChild($xml_cc);
	$xml_email->appendChild($xml_ass);
	$xml_email->appendChild($xml_msg);

	$xml->appendChild($xml_email);

	$xml->save("../xml/bridge/actual.xml");

	$received["status"] = "ok";

	echo json_encode($received);

?>