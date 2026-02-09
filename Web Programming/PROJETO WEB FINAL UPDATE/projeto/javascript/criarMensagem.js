
$(document).ready(function(){

	// AJAX para retornar o usuário remetente(Email Cc).
	$.ajax({

		type: "POST",
		dataType: "json",
		url: "../php/findActualUser.php",
		async: false,
		data: {

		},
		success: function(retorno){
			$("#emailCc").val(retorno["email"]);
		}

	});


	var emailDestinatarioVar = 1;

	$("#emailDestinatario").click(function(){ $("#emailDestinatario").removeClass("redBorder"); $("#emailDestinatario").val(""); });


	$("#emailDestinatario").change(function(){


		$.ajax({

			type: "POST",
			dataType: "json",
			url: "../php/findDestinatary.php",
			data: {
				email: $("#emailDestinatario").val(),
			},
			success: function(verify){

				if(verify["status"] == "doesNotExist"){ emailDestinatarioVar = 0; }
				else{ emailDestinatarioVar = 1; }

			}

		});

	});



	$("#btEnviar").click(function(){

		if($("#emailDestinatario").val() == ""){ emailDestinatarioVar = 0; }

		if(emailDestinatarioVar == 0){ 

			alert("Email destinatário digitado não existe. Por favor, digite um email existente.");
			$("#emailDestinatario").addClass("redBorder");
		
		}
		else{
				

			var dest = $("#emailDestinatario").val();		
			var cc = $("#emailCc").val();
			var ass = $("#emailAssunto").val();
			var msg = $("#emailMensagem").val();

			var codCc = "";
			var codDest = "";

			$.ajax({
				type: "POST",
				dataType: "json",
				url: "../php/emailCodigo/getCodigo.php",
				async: false,
				data: {
					path: ("../../xml/emails/"+cc+"/itensEnviados/info/codigo.xml"),
				},
				success: function(codigo){
					codCc = codigo["valor"];
				}

			});

			$.ajax({
				type: "POST",
				dataType: "json",
				url: "../php/emailCodigo/getCodigo.php",
				async: false,
				data: {
					path: ("../../xml/emails/"+dest+"/caixaDeEntrada/info/codigo.xml"),
				},
				success: function(codigo){
					codDest = codigo["valor"];
				}

			});

			$.ajax({
				type: "POST",
				dataType: "json",
				url: "../php/makeEmail.php",
				async: false,
				data: {
					destinatario: dest, destinatario_codigo: codDest, 
					emailCc: cc, emailCc_codigo: codCc,
					assunto: ass, mensagem: msg,
					pathFrom: ("../xml/emails/"+cc+"/itensEnviados/emailItensEnviados"+codCc+".xml"),
					pathTo: ("../xml/emails/"+dest+"/caixaDeEntrada/emailCaixaDeEntrada"+codDest+".xml"),
				},
				success: function(confirm){
				
				}

			});

			$.ajax({
				type: "POST",
				dataType: "json",
				url: "../php/emailCodigo/alterarCodigo.php",
				async: false,
				data: {
					path: ("../../xml/emails/"+cc+"/itensEnviados/info/codigo.xml"),
				},
				success: function(confirm){
				
				}

			});

			$.ajax({
				type: "POST",
				dataType: "json",
				url: "../php/emailCodigo/alterarCodigo.php",
				async: false,
				data: {
					path: ("../../xml/emails/"+dest+"/caixaDeEntrada/info/codigo.xml"),
				},
				success: function(confirm){
				
				}

			});

			window.open("../html/caixaDeEntrada.html", "_self");

		}

	});


	$("#btRascunhar").click(function(){

		var emailPara = $("#emailDestinatario").val();
		var emailDe = $("#emailCc").val();
		var assunto = $("#emailAssunto").val();
		var mensagem = $("#emailMensagem").val();

		if(emailPara == ""){ emailPara = " ";}
		if(assunto == ""){ assunto = " ";}
		if(mensagem == ""){ mensagem = " ";}

		var codRascunho = "";

		$.ajax({
			type: "POST",
			dataType: "json",
			url: "../php/emailCodigo/getCodigo.php",
			async: false,
			data: {
				path: ("../../xml/emails/"+emailDe+"/rascunhos/info/codigo.xml"),
			},
			success: function(codigo){
				codRascunho = codigo["valor"];
			}

		});

		$.ajax({
			type: "POST",
			dataType: "json",
			url: "../php/makeRascunho.php",
			async: false,
			data: {
				rascunho_codigo: codRascunho, 
				destinatario: emailPara, emailCc: emailDe, 
				assunto: assunto, mensagem: mensagem,
				pathRascunho: ("../xml/emails/"+emailDe+"/rascunhos/emailRascunhos"+codRascunho+".xml"),
			},
			success: function(confirm){
			}

		});

		$.ajax({
			type: "POST",
			dataType: "json",
			url: "../php/emailCodigo/alterarCodigo.php",
			async: false,
			data: {
				path: ("../../xml/emails/"+emailDe+"/rascunhos/info/codigo.xml"),
			},
			success: function(confirm){
			}

		});

		window.open("../html/rascunhos.html", "_self");

	});


	$("#btAbortar").click(function(){

		var emailPara = $("#emailDestinatario").val();
		var emailDe = $("#emailCc").val();
		var assunto = $("#emailAssunto").val();
		var mensagem = $("#emailMensagem").val();

		if(emailPara == ""){ emailPara = " ";}
		if(assunto == ""){ assunto = " ";}
		if(mensagem == ""){ mensagem = " ";}

		var codArquivoMorto = "";

		$.ajax({
			type: "POST",
			dataType: "json",
			url: "../php/emailCodigo/getCodigo.php",
			async: false,
			data: {
				path: ("../../xml/emails/"+emailDe+"/arquivoMorto/info/codigo.xml"),
			},
			success: function(codigo){
				codArquivoMorto = codigo["valor"];
			}

		});


		// Usa-se o mesmo código .php para fazer o arquivo morto. 
		$.ajax({
			type: "POST",
			dataType: "json",
			url: "../php/makeRascunho.php",
			async: false,
			data: {
				rascunho_codigo: codArquivoMorto, 
				destinatario: emailPara, emailCc: emailDe, 
				assunto: assunto, mensagem: mensagem,
				pathRascunho: ("../xml/emails/"+emailDe+"/arquivoMorto/emailArquivoMorto"+codArquivoMorto+".xml"),
			},
			success: function(confirm){

			}

		});

		$.ajax({
			type: "POST",
			dataType: "json",
			url: "../php/emailCodigo/alterarCodigo.php",
			async: false,
			data: {
				path: ("../../xml/emails/"+emailDe+"/arquivoMorto/info/codigo.xml"),
			},
			success: function(confirm){
			
			}

		});

		window.open("../html/caixaDeEntrada.html", "_self");

	});



});