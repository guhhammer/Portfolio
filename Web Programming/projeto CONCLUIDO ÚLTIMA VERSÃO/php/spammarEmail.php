




<?php 

	$user = $_POST["emailAtual"];

	$arcName = $_POST["arcName"];

	$actualPaste = $_POST["actualPaste"];

	$path = "../xml/emails/".$user."/".$actualPaste."/".$arcName;

	$newPath = "../xml/emails/".$user."/lixoEletronico/".$arcName;

	if($actualPaste == "lixoEletronico"){

		rename($path, ("../xml/emails/".$user."/caixaDeEntrada/".$arcName));

	}
	else{

		rename($path, $newPath);

	}

	$confirm["status"] = "ok";

	echo json_encode($confirm);


?>