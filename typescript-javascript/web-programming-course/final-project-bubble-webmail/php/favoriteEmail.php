
<?php 


	$value = $_POST["value"];

	$user = $_POST["emailAtual"];

	$arcName = $_POST["arcName"];

	$path = "../xml/emails/".$user."/inbox/".$arcName;

	$newPath = "../xml/emails/".$user."/favorites/".$arcName;

	if($value == "1"){
	
		copy($path, $newPath);

	}
	if($value == "0"){

		unlink($newPath);

	}

	$confirm["status"] = "ok";

	echo json_encode($confirm);


?>