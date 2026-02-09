

<?php 

	$xml = new DOMDocument("1.0"); 

	$xml_album = $xml->createElement("Album");
	
	$xml_playlist = $xml->createElement("Playlist");

	$xml_nome = $xml->createElement("nome", "Nome do Album");
	$xml_cantor = $xml->createElement("cantor", "Nome do cantor/banda");

	$xml_musica = $xml->createElement("Música", "Nome da Musica");
	$xml_musica->setAttribute("duracao", "0:03:15");
	$xml_playlist-> appendChild($xml_musica);

	$xml_musica = $xml->createElement("Música", "Nome da Musica");
	$xml_musica->setAttribute("duracao", "0:03:15");
	$xml_playlist-> appendChild($xml_musica);

	$xml_musica = $xml->createElement("Música", "Nome da Musica");
	$xml_musica->setAttribute("duracao", "0:03:15");
	$xml_playlist-> appendChild($xml_musica);

	$xml_album->appendChild($xml_nome);
	$xml_album->appendChild($xml_cantor);
	$xml_album->appendChild($xml_playlist);
	
	$xml->appendChild($xml_album);


	$xml->save("../xml/musicasTeste.xml");




?>