




<?php 

	$user = $_POST["emailAtual"];

	$arcName = $_POST["arcName"];

	$actualPaste = $_POST["actualPaste"];

	$path = "../xml/emails/".$user."/".$actualPaste."/".$arcName;

	$newPath = "../xml/emails/".$user."/junkMail/".$arcName;

	if($actualPaste == "junkMail"){

		rename($path, ("../xml/emails/".$user."/inbox/".$arcName));

	}
	else{

		rename($path, $newPath);

	}

	$confirm["status"] = "ok";

	echo json_encode($confirm);


?>