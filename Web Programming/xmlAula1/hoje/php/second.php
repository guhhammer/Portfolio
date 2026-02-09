



<?php
	
	$dir = "../xml/first.xml";

	$xml_string = file_get_contents($dir);

	$xml_objeto = simplexml_load_string($xml_string);

	

	echo ($xml_objeto->banda[0]->nomeDaMusica->musica[1]."<br>");

	echo ($xml_objeto->banda[1]->nomeDaMusica->musica[0]."<br>");

	echo ($xml_objeto->banda[2]->nomeDaMusica->musica[1]."<br>");


	 


?>