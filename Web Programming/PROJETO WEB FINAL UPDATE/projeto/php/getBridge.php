


<?php 


	$xml_string = file_get_contents("../xml/bridge/actual.xml");

	$xml_objeto = simplexml_load_string($xml_string);


	$infos["destinatario"] = $xml_objeto->destinatario[0]."";

	$infos["emailCc"] = $xml_objeto->emailCc[0]."";

	$infos["assunto"] = $xml_objeto->assunto[0]."";

	$infos["mensagem"] = $xml_objeto->mensagem[0]."";


	echo json_encode($infos);


?>