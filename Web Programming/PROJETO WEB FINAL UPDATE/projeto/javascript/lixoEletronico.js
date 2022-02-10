

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
			caminho: ("../xml/emails/"+(emailVAR)+"/lixoEletronico/"),
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

		var link = ("../xml/emails/"+emailVAR+"/lixoEletronico/"+emailsJSONVAR[index]);
		var referencia = "../html/lixoEletronico.html";

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

		var show = "";
		if(emailCc != emailVAR){ show = "De: "+emailCc; }
		else{ show = "Para: "+destinatario}

		var string = "";

		string += "<tr id=\"trNum"+counter+"\"><td><button id=\"msgBox\" class=\"designRascunho\"><table><tr>";
		string += "<td><button id=\"btExcluir\" class=\"excluir\"></button></td>";
		string += "<td><button id=\"btSpam\" class=\"spam\"></button></td>";		
		string += "<td><p id=\"DE\">"+show+"</p></td>";
		string += "<td><p id\"space1\"> | </p></td>";
		string += "<td><p id=\"ASSUNTO\">Assunto: "+assunto+"</p></td>";
		string += "<td><p id=\"space2\"> | </p></td>";
		string += "<td><p id=\"MENSAGEM\">Mensagem: "+mensagem+"</p></td>";
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



	$("#mensagens #PARA").each(function(){

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


	$("#mensagens #ASSUNTO").each(function(){

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

		
	$("#mensagens #MENSAGEM").each(function(){

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


	$("#LixoEletronico").append("<td><button id=\"orangeShow\" class=\"orangeHIDE\" disabled></button></td>");
	$("#mensagens #msgBox #btSpam").each(function(){

		$(this).hover(
			function(){
				
				$("#orangeShow").removeClass("orangeHIDE");
				$("#orangeShow").addClass("orangeON");

			},
			function(){
				
				$("#orangeShow").removeClass("orangeON");
				$("#orangeShow").addClass("orangeHIDE");

			}
		);

	});


	var notRED = false;
	var notORANGE = false;
		

	$("#mensagens #msgBox #btExcluir").each(function(){

		$(this).click(function(){

			var hold = $(this).closest("#msgBox").find("td:eq(7)").text().toString(); // link email deste button.

			var dest = hold.replace("../xml/emails/", "");
			dest = dest.replace(emailVAR+"/lixoEletronico/", "");

			$.ajax({

				type: "POST",
				dataType: "json",
				url: "../php/deleteFile.php",
				async: false,
				data: {
					emailAtual: emailVAR, arcName: dest, actualPaste: "lixoEletronico",
				},
				success: function(confirm){

				}

			});

			notRED = true;

			window.open("../html/lixoEletronico.html", "_self");

		});

		notRED = true;

	});

	$("#mensagens #msgBox #btSpam").each(function(){

		$(this).click(function(){

			var hold = $(this).closest("#msgBox").find("td:eq(7)").text().toString(); // link email deste button.

			var dest = hold.replace("../xml/emails/", "");
			dest = dest.replace(emailVAR+"/lixoEletronico/", "");

			$.ajax({

				type: "POST",
				dataType: "json",
				url: "../php/spammarEmail.php",
				async: false,
				data: {
					emailAtual: emailVAR, arcName: dest, actualPaste: "lixoEletronico",
				},
				success: function(confirm){

				}

			});

			notORANGE = true;

			window.open("../html/caixaDeEntrada.html", "_self");

		});

		notORANGE = true;

	});


	$("#mensagens #msgBox").click(function(){

		if(!notRED && !notORANGE){

			$(this).click(function(){
				
				this.id = "teste";

				
				var destS = $("#teste #DE").text().toString().replace("De: ", "");
				var assS = $("#teste #ASSUNTO").text().toString().replace("Assunto: ", "");
				var msgS = $("#teste #MENSAGEM").text().toString().replace("Mensagem: ", "");

				var linkS = $("#teste #LINK").text().toString().replace("../xml/emails/", "");
				linkS = linkS.replace((emailVAR+"/lixoEletronico/"), "");

				var deQuem = "";
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "../php/getEmailInfos.php",
					async: false,
					data: {
						caminho: $("#teste #LINK").text().toString(),
					},
					success: function(info){
						deQuem = info["emailCc"];
					}

				});

		        $.ajax({
		        	type: "POST",
		        	dataType: "json",
		        	url: "../php/makeBridgeShowMessage.php",
		        	async: false,
		        	data: {
		        		dest: emailVAR, cc: deQuem, ass: assS, msg: msgS, fromScreen: "../html/lixoEletronico.html", link: linkS,
		        	},
		        	sucess: function(received){

		          	}

		        });

		        window.open("../html/telaVisualizarMensagem.html", "_self");

			});

		}
		else{
			notRED = false; notORANGE = false;
		}


	});


});

