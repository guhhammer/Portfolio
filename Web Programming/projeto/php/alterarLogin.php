

<?php

	$login = $_POST["login"];
	$senha = $_POST["senha"];

	$dir = "../xml/AllEmails/".$login.".xml";

	$xml_string = file_get_contents($dir);

	$xml_objeto = simplexml_load_string($xml_string);

	$email = $xml_objeto->login[0];


	$options1 = $xml_objeto->options->btGuiaBarra;
	$options2 = $xml_objeto->options->btFavoritos;
	$options3 = $xml_objeto->options->btCaixaDeEntrada;
	$options4 = $xml_objeto->options->btLixoEletronico;
	$options5 = $xml_objeto->options->btRascunhos;
	$options6 = $xml_objeto->options->btItensEnviados;
	$options7 = $xml_objeto->options->btItensExcluidos;
	$options8 = $xml_objeto->options->btArquivoMorto;


	$xml = new DOMDocument();

	$xml_cadastro = $xml->createElement("cadastro");

	$xml_email = $xml->createElement("login", "$email");
	$xml_senha = $xml->createElement("senha", "$senha");


	$xml_guia_barra = $xml->createElement("btGuiaBarra", $options1);
	$xml_btFavoritos = $xml->createElement("btFavoritos", $options2);
	$xml_btCaixaDeEntrada = $xml->createElement("btCaixaDeEntrada", $options3);
	$xml_btLixoEletronico = $xml->createElement("btLixoEletronico", $options4);
	$xml_btRascunhos = $xml->createElement("btRascunhos", $options5);
	$xml_btItensEnviados = $xml->createElement("btItensEnviados", $options6);
	$xml_btItensExcluidos = $xml->createElement("btItensExcluidos", $options7);
	$xml_btArquivoMorto = $xml-> createElement("btArquivoMorto", $options8);

	$xml_options = $xml->createElement("options");


	$xml_options->appendChild($xml_guia_barra);
	$xml_options->appendChild($xml_btFavoritos);
	$xml_options->appendChild($xml_btCaixaDeEntrada);
	$xml_options->appendChild($xml_btLixoEletronico);
	$xml_options->appendChild($xml_btRascunhos);
	$xml_options->appendChild($xml_btItensEnviados);
	$xml_options->appendChild($xml_btItensExcluidos);
	$xml_options->appendChild($xml_btArquivoMorto);

	$xml_cadastro->appendChild($xml_email);
	$xml_cadastro->appendChild($xml_senha);
	$xml_cadastro->appendChild($xml_options);

	$xml->appendChild($xml_cadastro);

	$xml->save($dir);

	echo json_encode($process);

?>