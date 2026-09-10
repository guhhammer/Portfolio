



<?php
	
	$dir = "../xml/first.xml";

	$xml_string = file_get_contents($dir);

	$xml_objeto = simplexml_load_string($xml_string);

	

	echo ($xml_objeto->band[0]->nameDaMusica->song[1]."<br>");

	echo ($xml_objeto->band[1]->nameDaMusica->song[0]."<br>");

	echo ($xml_objeto->band[2]->nameDaMusica->song[1]."<br>");


	 


?>