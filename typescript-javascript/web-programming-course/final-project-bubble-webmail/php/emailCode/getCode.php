
<?php 

	$code["value"] = simplexml_load_string(file_get_contents($_POST["path"]))->value[0]."";

	echo json_encode($code);

?>

