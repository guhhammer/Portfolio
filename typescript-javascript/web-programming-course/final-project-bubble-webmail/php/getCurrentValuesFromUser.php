

<?php 

	$email = $_POST["email"];

	$dir = "../xml/AllEmails/".$email.".xml";

	$xml_string = file_get_contents($dir);

	$xml_objeto = simplexml_load_string($xml_string);

	$result["btTabBar"] = $xml_objeto->options->btTabBar[0]."";
	$result["btFavorites"] = $xml_objeto->options->btFavorites[0]."";
	$result["btInbox"] = $xml_objeto->options->btInbox[0]."";
	$result["btJunkMail"] = $xml_objeto->options->btJunkMail[0]."";
	$result["btDrafts"] = $xml_objeto->options->btDrafts[0]."";
	$result["btSentItems"] = $xml_objeto->options->btSentItems[0]."";
	$result["btDeletedItems"] = $xml_objeto->options->btDeletedItems[0]."";
	$result["btArchive"] = $xml_objeto->options->btArchive[0]."";

	echo json_encode($result);

?>