
<?php 

	$codigo["valor"] = simplexml_load_string(file_get_contents($_POST["path"]))->valor[0]."";

	echo json_encode($codigo);

?>

