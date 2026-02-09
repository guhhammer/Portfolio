
<?php 


	$value = $_POST["valor"];

	$user = $_POST["emailAtual"];

	$arcName = $_POST["arcName"];

	$path = "../xml/emails/".$user."/caixaDeEntrada/".$arcName;

	$newPath = "../xml/emails/".$user."/favoritos/".$arcName;

	if($value == "1"){
	
		copy($path, $newPath);

	}
	if($value == "0"){

		unlink($newPath);

	}

	$confirm["status"] = "ok";

	echo json_encode($confirm);


?>