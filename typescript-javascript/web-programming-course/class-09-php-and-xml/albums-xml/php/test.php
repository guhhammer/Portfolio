

<?php 

	$xml = new DOMDocument("1.0"); 

	$xml_album = $xml->createElement("Album");
	
	$xml_playlist = $xml->createElement("Playlist");

	$xml_name = $xml->createElement("name", "Name do Album");
	$xml_singer = $xml->createElement("singer", "Name do singer/band");

	$xml_song = $xml->createElement("Song", "Name da Musica");
	$xml_song->setAttribute("duration", "0:03:15");
	$xml_playlist-> appendChild($xml_song);

	$xml_song = $xml->createElement("Song", "Name da Musica");
	$xml_song->setAttribute("duration", "0:03:15");
	$xml_playlist-> appendChild($xml_song);

	$xml_song = $xml->createElement("Song", "Name da Musica");
	$xml_song->setAttribute("duration", "0:03:15");
	$xml_playlist-> appendChild($xml_song);

	$xml_album->appendChild($xml_name);
	$xml_album->appendChild($xml_singer);
	$xml_album->appendChild($xml_playlist);
	
	$xml->appendChild($xml_album);


	$xml->save("../xml/songsTest.xml");




?>