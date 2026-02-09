




<?php 

	$path = $_JSON["caminho"];

	$xml_string = file_get_contents($path);

	$xml_objeto = simplexml_load_string($xml_string);

	$info["destinatario"] = $xml_objeto->destinatario[0]."";

	$info["emailCc"] = $xml_objeto->emailCc[0]."";

	$info["assunto"] = $xml_objeto->assunto[0]."";

	$info["mensagem"] = $xml_objeto->mensagem[0]."";

	echo json_encode($info);

?>

