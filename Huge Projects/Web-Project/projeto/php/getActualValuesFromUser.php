

<?php 

	$email = $_POST["email"];

	$dir = "../xml/AllEmails/".$email.".xml";

	$xml_string = file_get_contents($dir);

	$xml_objeto = simplexml_load_string($xml_string);

	$retorno["btGuiaBarra"] = $xml_objeto->options->btGuiaBarra[0]."";
	$retorno["btFavoritos"] = $xml_objeto->options->btFavoritos[0]."";
	$retorno["btCaixaDeEntrada"] = $xml_objeto->options->btCaixaDeEntrada[0]."";
	$retorno["btLixoEletronico"] = $xml_objeto->options->btLixoEletronico[0]."";
	$retorno["btRascunhos"] = $xml_objeto->options->btRascunhos[0]."";
	$retorno["btItensEnviados"] = $xml_objeto->options->btItensEnviados[0]."";
	$retorno["btItensExcluidos"] = $xml_objeto->options->btItensExcluidos[0]."";
	$retorno["btArquivoMorto"] = $xml_objeto->options->btArquivoMorto[0]."";

	echo json_encode($retorno);

?>