<?php 

	$path = $_POST["caminho"];

	$xml_string = file_get_contents($path);

	$xml_objeto = simplexml_load_string($xml_string);

	$info["recipient"] = $xml_objeto->recipient[0]."";

	$info["emailCc"] = $xml_objeto->emailCc[0]."";

	$info["subject"] = $xml_objeto->subject[0]."";

	$info["message"] = $xml_objeto->message[0]."";

	$info["greenCode"] = $xml_objeto->greenCode[0]."";

	$info["yellowCode"] = $xml_objeto->yellowCode[0]."";

	echo json_encode($info);

?>

