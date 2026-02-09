


$(document).ready(function(){


	var voltarVAR = "";

	var userAtual = "";

	var linkEmail = "";


	var emailVAR = "";

	// AJAX para retornar o usuário remetente(Email Cc).
	$.ajax({

		type: "POST",
		dataType: "json",
		url: "../php/findActualUser.php",
		async: false,
		data: {

		},
		success: function(retorno){
			emailVAR = retorno["email"];
		}

	});


	// AJAX para retornar o usuário remetente(Email Cc).
	$.ajax({

		type: "POST",
		dataType: "json",
		url: "../php/getBridgeShowMessage.php",
		async: false,
		data: {

		},
		success: function(infos){

			$("#emailDestinatario").val(infos["destinatario"]);
			
			$("#emailCc").val(infos["emailCc"]);
			userAtual = infos["emailCc"];
			$("#emailCc").prop("disabled", true);


			$("#emailAssunto").val(infos["assunto"]);
			$("#emailMensagem").val(infos["mensagem"]);

			voltarVAR = infos["screen"];

			linkEmail = infos["link"];

		}

	});


	$("#btVoltar").click(function(){

		window.open(voltarVAR, "_self");

	});


	$("#btDelete").click(function(){

		var clean = voltarVAR.replace("../html/", "");
		var cleaned = clean.replace(".html", "");

		$.ajax({
			type: "POST",
			dataType: "json",
			url: "../php/deleteFile.php",
			async: false,
			data: {
				emailAtual: userAtual, arcName: linkEmail, actualPaste: cleaned,
			},
			success: function(confirm){

			}	

		});

		window.open(voltarVAR, "_self");

	});


});