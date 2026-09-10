



<?php

	$login =  $_POST["login"];
	$password =  $_POST["password"];

	$xml = new DOMDocument();

	$xml_account = $xml->createElement("account");

	$xml_login = $xml->createElement("login", "$login");
	$xml_password = $xml->createElement("password", "$password");

	
	$xml_account->appendChild($xml_login);
	$xml_account->appendChild($xml_password);



	$xml_options = $xml->createElement("options");

	$xml_guia_barra = $xml->createElement("btTabBar", "1");

	$xml_btFavorites = $xml->createElement("btFavorites", "1");

	$xml_btInbox = $xml->createElement("btInbox", "1");

	$xml_btJunkMail = $xml->createElement("btJunkMail", "1");

	$xml_btDrafts = $xml->createElement("btDrafts", "1");

	$xml_btSentItems = $xml->createElement("btSentItems", "1");

	$xml_btDeletedItems = $xml->createElement("btDeletedItems", "1");

	$xml_btArchive = $xml-> createElement("btArchive", "1");



	$xml_options->appendChild($xml_guia_barra);
	$xml_options->appendChild($xml_btFavorites);
	$xml_options->appendChild($xml_btInbox);
	$xml_options->appendChild($xml_btJunkMail);
	$xml_options->appendChild($xml_btDrafts);
	$xml_options->appendChild($xml_btSentItems);
	$xml_options->appendChild($xml_btDeletedItems);
	$xml_options->appendChild($xml_btArchive);



	$xml_account->appendChild($xml_options);

	$xml->appendChild($xml_account);


	$check = true;

	$confirm["status"] = "ok";

	$path = "../xml/AllEmails/"."$login".".xml";

	$directory = dir("../xml/AllEmails/");

	while($file = $directory -> read()){
		if($file == "$login.xml"){
			$check = false;

			$confirm["status"] = "not ok";

		}
	}



	if($check){
		
		$xml->save($path);

		$pathNewDir = "../xml/emails/"."$login";

		mkdir($pathNewDir, 0755);

		mkdir($pathNewDir."/favorites", 0755);
		mkdir($pathNewDir."/inbox", 0755);
		mkdir($pathNewDir."/junkMail", 0755);
		mkdir($pathNewDir."/drafts", 0755);
		mkdir($pathNewDir."/sentItems", 0755);
		mkdir($pathNewDir."/deletedItems", 0755);
		mkdir($pathNewDir."/archive", 0755);

		mkdir($pathNewDir."/favorites/info", 0755);
		mkdir($pathNewDir."/inbox/info", 0755);
		mkdir($pathNewDir."/junkMail/info", 0755);
		mkdir($pathNewDir."/drafts/info", 0755);
		mkdir($pathNewDir."/sentItems/info", 0755);
		mkdir($pathNewDir."/deletedItems/info", 0755);
		mkdir($pathNewDir."/archive/info", 0755);


		$emailCode = new DOMDocument();

		$code = $emailCode->createElement("code");
		$value = $emailCode->createElement("value", "0");
		
		$code->appendChild($value);
		$emailCode->appendChild($code);

		$emailCode->save($pathNewDir."/favorites/info/code.xml");
		$emailCode->save($pathNewDir."/inbox/info/code.xml");
		$emailCode->save($pathNewDir."/junkMail/info/code.xml");
		$emailCode->save($pathNewDir."/drafts/info/code.xml");
		$emailCode->save($pathNewDir."/sentItems/info/code.xml");
		$emailCode->save($pathNewDir."/deletedItems/info/code.xml");
		$emailCode->save($pathNewDir."/archive/info/code.xml");


	}
	

	echo json_encode($confirm);

?>