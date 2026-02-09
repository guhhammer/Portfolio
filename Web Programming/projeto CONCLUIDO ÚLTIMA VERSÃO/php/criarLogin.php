



<?php

	$login =  $_POST["login"];
	$senha =  $_POST["senha"];

	$xml = new DOMDocument();

	$xml_cadastro = $xml->createElement("cadastro");

	$xml_login = $xml->createElement("login", "$login");
	$xml_senha = $xml->createElement("senha", "$senha");

	
	$xml_cadastro->appendChild($xml_login);
	$xml_cadastro->appendChild($xml_senha);



	$xml_options = $xml->createElement("options");

	$xml_guia_barra = $xml->createElement("btGuiaBarra", "1");

	$xml_btFavoritos = $xml->createElement("btFavoritos", "1");

	$xml_btCaixaDeEntrada = $xml->createElement("btCaixaDeEntrada", "1");

	$xml_btLixoEletronico = $xml->createElement("btLixoEletronico", "1");

	$xml_btRascunhos = $xml->createElement("btRascunhos", "1");

	$xml_btItensEnviados = $xml->createElement("btItensEnviados", "1");

	$xml_btItensExcluidos = $xml->createElement("btItensExcluidos", "1");

	$xml_btArquivoMorto = $xml-> createElement("btArquivoMorto", "1");



	$xml_options->appendChild($xml_guia_barra);
	$xml_options->appendChild($xml_btFavoritos);
	$xml_options->appendChild($xml_btCaixaDeEntrada);
	$xml_options->appendChild($xml_btLixoEletronico);
	$xml_options->appendChild($xml_btRascunhos);
	$xml_options->appendChild($xml_btItensEnviados);
	$xml_options->appendChild($xml_btItensExcluidos);
	$xml_options->appendChild($xml_btArquivoMorto);



	$xml_cadastro->appendChild($xml_options);

	$xml->appendChild($xml_cadastro);


	$check = true;

	$confirm["status"] = "ok";

	$path = "../xml/AllEmails/"."$login".".xml";

	$diretorio = dir("../xml/AllEmails/");

	while($arquivo = $diretorio -> read()){
		if($arquivo == "$login.xml"){
			$check = false;

			$confirm["status"] = "not ok";

		}
	}



	if($check){
		
		$xml->save($path);

		$pathNewDir = "../xml/emails/"."$login";

		mkdir($pathNewDir, 0755);

		mkdir($pathNewDir."/favoritos", 0755);
		mkdir($pathNewDir."/caixaDeEntrada", 0755);
		mkdir($pathNewDir."/lixoEletronico", 0755);
		mkdir($pathNewDir."/rascunhos", 0755);
		mkdir($pathNewDir."/itensEnviados", 0755);
		mkdir($pathNewDir."/itensExcluidos", 0755);
		mkdir($pathNewDir."/arquivoMorto", 0755);

		mkdir($pathNewDir."/favoritos/info", 0755);
		mkdir($pathNewDir."/caixaDeEntrada/info", 0755);
		mkdir($pathNewDir."/lixoEletronico/info", 0755);
		mkdir($pathNewDir."/rascunhos/info", 0755);
		mkdir($pathNewDir."/itensEnviados/info", 0755);
		mkdir($pathNewDir."/itensExcluidos/info", 0755);
		mkdir($pathNewDir."/arquivoMorto/info", 0755);


		$emailCodigo = new DOMDocument();

		$codigo = $emailCodigo->createElement("codigo");
		$valor = $emailCodigo->createElement("valor", "0");
		
		$codigo->appendChild($valor);
		$emailCodigo->appendChild($codigo);

		$emailCodigo->save($pathNewDir."/favoritos/info/codigo.xml");
		$emailCodigo->save($pathNewDir."/caixaDeEntrada/info/codigo.xml");
		$emailCodigo->save($pathNewDir."/lixoEletronico/info/codigo.xml");
		$emailCodigo->save($pathNewDir."/rascunhos/info/codigo.xml");
		$emailCodigo->save($pathNewDir."/itensEnviados/info/codigo.xml");
		$emailCodigo->save($pathNewDir."/itensExcluidos/info/codigo.xml");
		$emailCodigo->save($pathNewDir."/arquivoMorto/info/codigo.xml");


	}
	

	echo json_encode($confirm);

?>