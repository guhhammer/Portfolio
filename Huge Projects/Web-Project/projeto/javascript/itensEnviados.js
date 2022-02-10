

$(document).ready(function(){


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



	var emailsJSONVAR = [];
	$.ajax({
		
		type: "POST",
		dataType: "json",
		url: "../php/getAllEmailsNames.php",
		async: false,
		data: {
			caminho: ("../xml/emails/"+(emailVAR)+"/itensEnviados/"),
		},
		success: function(emailsJSON){
			emailsJSONVAR = emailsJSON;
		}

	});


	var counter = 0;
	while((emailsJSONVAR).length > counter){
		
		var index = counter;

		var destinatario = "";
		var emailCc = "";
		var assunto = "";
		var mensagem = "";

		var link = ("../xml/emails/"+emailVAR+"/itensEnviados/"+emailsJSONVAR[index]);
		var referencia = "../html/itensEnviados.html";

		$.ajax({
		
			type: "POST",
			dataType: "json",
			url: "../php/getEmailInfos.php",
			async: false,
			data: {
				caminho: link,
			},
			success: function(info){
				destinatario = info["destinatario"];
				emailCc = info["emailCc"];
				assunto = info["assunto"];
				mensagem = info["mensagem"];
			}
		
		});

		var string = "";

		string += "<tr id=\"trNum"+counter+"\"><td><button id=\"msgBox\" class=\"designRascunho\"><table><tr>";
		string += "<td><button id=\"btExcluir\" class=\"excluir\"></button></td>";		
		string += "<td><p id=\"showPARA\">Para: "+destinatario+"</p><p id=\"PARA\" class=\"hide\">Para: "+destinatario+"</p></td>";
		string += "<td><p id\"space1\"> | </p></td>";
		string += "<td><p id=\"showASSUNTO\">Assunto: "+assunto+"</p><p id=\"ASSUNTO\" class=\"hide\">Assunto: "+assunto+"</p></td>";
		string += "<td><p id=\"space2\"> | </p></td>";
		string += "<td><p id=\"showMENSAGEM\">Mensagem: "+mensagem+"</p><p id=\"MENSAGEM\" class=\"hide\">Mensagem: "+mensagem+"</p></td>";
		string += "<td><p id=\"LINK\" class=\"hide\">"+link+"</p></td>";
		string += "<td><p id=\"REFERENCIA\" class=\"hide\">"+referencia+"</p></td>";
		string += "</tr></table></button></td></tr>";

		$("#mensagens").append(string.toString());


		counter += 1;

	}

	if($("#mensagens").text().toString().length < 26){
		$("#mensagens").append("<tr class=\"appended\"><td><img src=\"../imagens/mail.png\" id=\"emptyBox\"></tr></td>");
	}
	else{
		$(".appended").remove();
	}


	$("#mensagens #showPARA").each(function(){

		var limit = 22;

		if($(this).text().length > limit){

			var value = $(this).text();

			$(this).text("");

			var string = "";
			var counter = 0;
			while(counter < limit+4){

				string = (string+value.charAt(counter));
				counter = counter + 1;

			}
			$(this).text(string+"...");

		}

	});


	$("#mensagens #showASSUNTO").each(function(){

		var limit = 25; 

		if($(this).text().length > limit){

			var value = $(this).text();

			$(this).text("");

			var string = "";
			var counter = 0;
			while(counter < limit+6){

				string = (string+value.charAt(counter));
				counter = counter + 1;

			}
			$(this).text(string+"...");

		}

	});

		
	$("#mensagens #showMENSAGEM").each(function(){

		var limit = 50;

		if($(this).text().length > limit){

			var value = $(this).text();

			$(this).text("");

			var string = "";
			var counter = 0;
			while(counter < limit+7){

				string = (string+value.charAt(counter));
				counter = counter + 1;

			}
			$(this).text(string+"...");

		}

	});

	$("#ItensExcluidos").append("<td><button id=\"redShow\" class=\"redHIDE\" disabled></button></td>");
	$("#mensagens #msgBox #btExcluir").each(function(){

		$(this).hover(
			function(){
				
				$("#redShow").removeClass("redHIDE");
				$("#redShow").addClass("redON");

			},
			function(){
				
				$("#redShow").removeClass("redON");
				$("#redShow").addClass("redHIDE");

			}
		);

	});


	var notRED = false;
		

	$("#mensagens #msgBox #btExcluir").each(function(){

		$(this).click(function(){

			var hold = $(this).closest("#msgBox").find("td:eq(6)").text().toString(); // link email deste button.

			var dest = hold.replace("../xml/emails/", "");
			dest = dest.replace(emailVAR+"/itensEnviados/", "");

			$.ajax({

				type: "POST",
				dataType: "json",
				url: "../php/deleteFile.php",
				async: false,
				data: {
					emailAtual: emailVAR, arcName: dest, actualPaste: "itensEnviados",
				},
				success: function(confirm){

				}

			});

			notRed = true;

			window.open("../html/itensEnviados.html", "_self");

		});

		notRED = true;

	});


	$("#mensagens #msgBox").click(function(){

		if(!notRED){

			$(this).click(function(){
				
				this.id = "teste";

				
				var destS = $("#teste #PARA").text().toString().replace("Para: ", "");
				var assS = $("#teste #ASSUNTO").text().toString().replace("Assunto: ", "");
				var msgS = $("#teste #MENSAGEM").text().toString().replace("Mensagem: ", "");

				var linkS = $("#teste #LINK").text().toString().replace("../xml/emails/", "");
				linkS = linkS.replace((emailVAR+"/itensEnviados/"), "");

		        $.ajax({
		        	type: "POST",
		        	dataType: "json",
		        	url: "../php/makeBridgeShowMessage.php",
		        	async: false,
		        	data: {
		        		dest: destS, cc: emailVAR, ass: assS, msg: msgS, fromScreen: "../html/itensEnviados.html", link: linkS,
		        	},
		        	sucess: function(received){

		          	}

		        });

		        window.open("../html/telaVisualizarMensagem.html", "_self");

			});

		}
		else{
			notRED = false;
		}


	});


});
