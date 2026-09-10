


<?php 


	$xml_string = file_get_contents("../xml/bridge/actualShowMessage.xml");

	$xml_objeto = simplexml_load_string($xml_string);


	$infos["recipient"] = $xml_objeto->recipient[0]."";

	$infos["emailCc"] = $xml_objeto->emailCc[0]."";

	$infos["subject"] = $xml_objeto->subject[0]."";

	$infos["message"] = $xml_objeto->message[0]."";

	$infos["screen"] = $xml_objeto->screen[0]."";

	$infos["link"] = $xml_objeto->link[0]."";

	echo json_encode($infos);


?>