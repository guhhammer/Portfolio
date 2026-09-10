

<?php

	$login = $_POST["login"];
	$password = $_POST["password"];

	$dir = "../xml/AllEmails/".$login.".xml";

	$xml_string = file_get_contents($dir);

	$xml_objeto = simplexml_load_string($xml_string);

	$email = $xml_objeto->login[0];


	$options1 = $xml_objeto->options->btTabBar;
	$options2 = $xml_objeto->options->btFavorites;
	$options3 = $xml_objeto->options->btInbox;
	$options4 = $xml_objeto->options->btJunkMail;
	$options5 = $xml_objeto->options->btDrafts;
	$options6 = $xml_objeto->options->btSentItems;
	$options7 = $xml_objeto->options->btDeletedItems;
	$options8 = $xml_objeto->options->btArchive;


	$xml = new DOMDocument();

	$xml_account = $xml->createElement("account");

	$xml_email = $xml->createElement("login", "$email");
	$xml_password = $xml->createElement("password", "$password");


	$xml_guia_barra = $xml->createElement("btTabBar", $options1);
	$xml_btFavorites = $xml->createElement("btFavorites", $options2);
	$xml_btInbox = $xml->createElement("btInbox", $options3);
	$xml_btJunkMail = $xml->createElement("btJunkMail", $options4);
	$xml_btDrafts = $xml->createElement("btDrafts", $options5);
	$xml_btSentItems = $xml->createElement("btSentItems", $options6);
	$xml_btDeletedItems = $xml->createElement("btDeletedItems", $options7);
	$xml_btArchive = $xml-> createElement("btArchive", $options8);

	$xml_options = $xml->createElement("options");


	$xml_options->appendChild($xml_guia_barra);
	$xml_options->appendChild($xml_btFavorites);
	$xml_options->appendChild($xml_btInbox);
	$xml_options->appendChild($xml_btJunkMail);
	$xml_options->appendChild($xml_btDrafts);
	$xml_options->appendChild($xml_btSentItems);
	$xml_options->appendChild($xml_btDeletedItems);
	$xml_options->appendChild($xml_btArchive);

	$xml_account->appendChild($xml_email);
	$xml_account->appendChild($xml_password);
	$xml_account->appendChild($xml_options);

	$xml->appendChild($xml_account);

	$xml->save($dir);

	echo json_encode($process);

?>