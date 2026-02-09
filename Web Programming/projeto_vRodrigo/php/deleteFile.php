
<?php 
	
	$user = $_POST["emailAtual"];

	$arcName = $_POST["arcName"];

	$actualPaste = $_POST["actualPaste"];

	$newPaste = "itensExcluidos";

	$path = "../xml/emails/".$user."/".$actualPaste."/".$arcName;

	$newPath = "../xml/emails/".$user."/".$newPaste."/".$arcName;

	if($actualPaste == $newPaste){

		unlink($path);

	}
	else{

		rename($path, $newPath);

	}

	$confirm["status"] = "ok";

	echo json_encode($confirm);
	
?>