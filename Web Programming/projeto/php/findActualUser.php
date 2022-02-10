

<?php 	

	$retorno["email"] = simplexml_load_string(file_get_contents("../xml/UsuarioAtual/atual.xml"))->email[0]."";    

	echo json_encode($retorno);

?>