

<?php 	

	$result["email"] = simplexml_load_string(file_get_contents("../xml/UserAtual/atual.xml"))->email[0]."";    

	echo json_encode($result);

?>