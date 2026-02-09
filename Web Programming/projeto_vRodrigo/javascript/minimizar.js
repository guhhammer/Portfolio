




$(document).ready(function(){


	var email = "";

	$.ajax({
		type: "POST",
		dataType: "json",
		url: "../php/findActualUser.php",
		async: false,
		data: {

		},
		success: function(retorno){

			email = retorno["email"];

		}

	});


	var btGuiaBarra = "1";
	var btFavoritos = "1";
	var btCaixaDeEntrada = "1";
	var btLixoEletronico = "1";
	var btRascunhos = "1";
	var btItensEnviados = "1";
	var btItensExcluidos = "1";
	var btArquivoMorto = "1";

	$.ajax({
		type: "POST",
		dataType: "json",
		url: "../php/getActualValuesFromUser.php",
		async: false,
		data: {
			email: email,
		},
		success: function(retorno){

			btGuiaBarra = retorno["btGuiaBarra"];
			btFavoritos = retorno["btFavoritos"];
			btCaixaDeEntrada = retorno["btCaixaDeEntrada"];
			btLixoEletronico = retorno["btLixoEletronico"];
			btRascunhos = retorno["btRascunhos"];
			btItensEnviados = retorno["btItensEnviados"];
			btItensExcluidos = retorno["btItensExcluidos"];
			btArquivoMorto = retorno["btArquivoMorto"];
			
			if(retorno["btGuiaBarra"] == "1"){ document.getElementById("guiaBarra").style.display = "block"; }
			else{ document.getElementById("guiaBarra").style.display = "none"; }
			
			if(retorno["btFavoritos"] == "1"){ document.getElementById("btFavoritos").style.display = "block"; }
			else{ document.getElementById("btFavoritos").style.display = "none"; }

			if(retorno["btCaixaDeEntrada"] == "1"){ document.getElementById("btCaixaDeEntrada").style.display = "block"; }
			else{ document.getElementById("btCaixaDeEntrada").style.display = "none"; }

			if(retorno["btLixoEletronico"] == "1"){ document.getElementById("btLixoEletronico").style.display = "block"; }
			else{ document.getElementById("btLixoEletronico").style.display = "none"; }

			if(retorno["btRascunhos"] == "1"){ document.getElementById("btRascunhos").style.display = "block"; }
			else{ document.getElementById("btRascunhos").style.display = "none"; }

			if(retorno["btItensEnviados"] == "1"){ document.getElementById("btItensEnviados").style.display = "block"; }
			else{ document.getElementById("btItensEnviados").style.display = "none"; }

			if(retorno["btItensExcluidos"] == "1"){ document.getElementById("btItensExcluidos").style.display = "block"; }
			else{ document.getElementById("btItensExcluidos").style.display = "none"; }

			if(retorno["btArquivoMorto"] == "1"){ document.getElementById("btArquivoMorto").style.display = "block"; }
			else{ document.getElementById("btArquivoMorto").style.display = "none"; }
		}

	});

	function alterarValorDe(email, nome, valor){

		$.ajax({
			type: "POST",
			dataType: "json",
			url: "../php/alterarValorDeButton.php",
			data: {
				email: email, nomebutton: nome, valor: valor,
			},
			success: function(retorno){

			}

		});

	}


	$("#btRecolher").click(function(){

		if(btGuiaBarra == "0"){	
			document.getElementById("guiaBarra").style.display = "block";
			btGuiaBarra = "1";
			alterarValorDe(email, "btGuiaBarra", 1);
			$("#mensagens").removeClass("withoutGuide");
			$("#mensagens").addClass("withGuide");
			$("#criarMensagens").removeClass("withoutGuide_createMessage");
			$("#criarMensagens").addClass("withGuide_createMessage");
			$("#verMensagens").removeClass("withoutGuide_seeMessage");
			$("#verMensagens").addClass("withGuide_seeMessage");

	    }
		else{	
			document.getElementById("guiaBarra").style.display = "none"; 
			btGuiaBarra = "0"; 
			alterarValorDe(email, "btGuiaBarra", 0);
			$("#mensagens").removeClass("withGuide");
			$("#mensagens").addClass("withoutGuide");
			$("#criarMensagens").removeClass("withGuide_createMessage");
			$("#criarMensagens").addClass("withoutGuide_createMessage");
			$("#verMensagens").removeClass("withGuide_seeMessage");
			$("#verMensagens").addClass("withoutGuide_seeMessage");

		}

	});

	$("#primeiro").click(function(){

		if(btFavoritos == "1"){	document.getElementById("btFavoritos").style.display = "none"; btFavoritos = "0"; alterarValorDe(email, "btFavoritos", 0); }
		else{	document.getElementById("btFavoritos").style.display = "block";  btFavoritos = "1"; alterarValorDe(email, "btFavoritos", 1); }

	});

	$("#segundo").click(function(){

		if(btCaixaDeEntrada == "1"){	document.getElementById("btCaixaDeEntrada").style.display = "none";	 btCaixaDeEntrada = "0"; alterarValorDe(email, "btCaixaDeEntrada", 0); }
		else{	document.getElementById("btCaixaDeEntrada").style.display = "block"; btCaixaDeEntrada = "1";	alterarValorDe(email, "btCaixaDeEntrada", 1); }

	});

	$("#terceiro").click(function(){

		if(btLixoEletronico == "1"){	document.getElementById("btLixoEletronico").style.display = "none";	 btLixoEletronico = "0";	alterarValorDe(email, "btLixoEletronico", 0); }
		else{	document.getElementById("btLixoEletronico").style.display = "block";  btLixoEletronico = "1";	alterarValorDe(email, "btLixoEletronico", 1); }

	});

	$("#quarto").click(function(){

		if(btRascunhos == "1"){	document.getElementById("btRascunhos").style.display = "none";	btRascunhos = "0"; alterarValorDe(email, "btRascunhos", 0); }
		else{	document.getElementById("btRascunhos").style.display = "block";  btRascunhos = "1"; alterarValorDe(email, "btRascunhos", 1); }

	});

	$("#quinto").click(function(){

		if(btItensEnviados == "1"){	document.getElementById("btItensEnviados").style.display = "none";	 btItensEnviados = "0"; alterarValorDe(email, "btItensEnviados", 0); }
		else{	document.getElementById("btItensEnviados").style.display = "block";  btItensEnviados = "1"; alterarValorDe(email, "btItensEnviados", 1); }

	});

	$("#sexto").click(function(){

		if(btItensExcluidos == "1"){	document.getElementById("btItensExcluidos").style.display = "none";	 btItensExcluidos = "0"; alterarValorDe(email, "btItensExcluidos", 0); }
		else{	document.getElementById("btItensExcluidos").style.display = "block";  btItensExcluidos = "1";	alterarValorDe(email, "btItensExcluidos", 1); }

	});

	$("#setimo").click(function(){

		if(btArquivoMorto == "1"){	document.getElementById("btArquivoMorto").style.display = "none"; btArquivoMorto = "0"; alterarValorDe(email, "btArquivoMorto", 0); }
		else{	document.getElementById("btArquivoMorto").style.display = "block";  btArquivoMorto = "1";	alterarValorDe(email, "btArquivoMorto", 1); }

	});

});



