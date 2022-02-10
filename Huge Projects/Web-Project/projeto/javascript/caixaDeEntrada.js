


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
			caminho: ("../xml/emails/"+(emailVAR)+"/caixaDeEntrada/"),
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
		var codigoVerde = "";

		var link = ("../xml/emails/"+emailVAR+"/caixaDeEntrada/"+emailsJSONVAR[index]);
		var referencia = "../html/caixaDeEntrada.html";

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
				codigoVerde = info["greenCode"];
				codigoAmarelo =info["yellowCode"];
			}
		
		});

		var classeVerde = "";
		if(codigoVerde == "1"){ classeVerde = "green"; }
		else{ classeVerde = "transparentV"; }

		var classeAmarela = "";
		if(codigoAmarelo == "1"){ classeAmarela = "yellow"; }
		else{ classeAmarela = "transparentV"; }

		var string = "";

		string += "<tr id=\"trNum"+counter+"\"><td><button id=\"msgBox\" class=\"designRascunho\"><table><tr>";
		string += "<td><button id=\"btExcluir\" class=\"excluir\"></button></td>";
		string += "<td><button id=\"btSpam\" class=\"spam\"></button></td>";
		string += "<td><button id=\"btFavoritar\" class=\""+classeAmarela+"\"></button></td>";
		string += "<td><button id=\"btVisualizado\" class=\""+classeVerde+"\"></button></td>";		
		string += "<td><p id=\"showDE\">De: "+emailCc+"</p><p id=\"DE\" class=\"hide\">De: "+emailCc+"</p></td>";
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

	
	$("#mensagens #showDE").each(function(){

		var limit = 18;

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

		var limit = 22; 

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

		var limit = 45;

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

	$("#favoritos").append("<td><button id=\"yellowShow\" class=\"yellowHIDE\" disabled></button></td>");
	$("#mensagens #msgBox #btFavoritar").each(function(){

		$(this).hover(
			function(){
				
				$("#yellowShow").removeClass("yellowHIDE");
				$("#yellowShow").addClass("yellowON");

			},
			function(){
				
				$("#yellowShow").removeClass("yellowON");
				$("#yellowShow").addClass("yellowHIDE");

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
	var notYELLOW = false;
	var notGREEN = false;
	var notORANGE = false;


	$("#mensagens #msgBox #btExcluir").each(function(){

		$(this).click(function(){

			var hold = $(this).closest("#msgBox").find("td:eq(9)").text().toString(); // link email deste button.

			var dest = hold.replace("../xml/emails/", "");
			dest = dest.replace(emailVAR+"/caixaDeEntrada/", "");

			$.ajax({

				type: "POST",
				dataType: "json",
				url: "../php/deleteFile.php",
				async: false,
				data: {
					emailAtual: emailVAR, arcName: dest, actualPaste: "caixaDeEntrada",
				},
				success: function(confirm){

				}

			});

			notRED = true;

			window.open("../html/caixaDeEntrada.html", "_self");

		});

		notRED = true;

	});



	$("#mensagens #msgBox #btSpam").each(function(){

		$(this).click(function(){

			var hold = $(this).closest("#msgBox").find("td:eq(9)").text().toString(); // link email deste button.

			var dest = hold.replace("../xml/emails/", "");
			dest = dest.replace(emailVAR+"/caixaDeEntrada/", "");

			$.ajax({

				type: "POST",
				dataType: "json",
				url: "../php/spammarEmail.php",
				async: false,
				data: {
					emailAtual: emailVAR, arcName: dest, actualPaste: "caixaDeEntrada",
				},
				success: function(confirm){

				}

			});

			notORANGE = true;

			window.open("../html/caixaDeEntrada.html", "_self");

		});

		notORANGE = true;

	});



	$("#mensagens #msgBox #btFavoritar").each(function(){

		$(this).click(function(){

			var hold = $(this).closest("#msgBox").find("td:eq(9)").text().toString(); // link email deste button.

			var dest = hold.replace("../xml/emails/", "");
			dest = dest.replace(emailVAR+"/caixaDeEntrada/", "");

			if($(this).hasClass("transparentV")){ 
				$(this).removeClass("transparentV"); 
				$(this).addClass("yellow"); 

				$.ajax({

					type: "POST",
					dataType: "json",
					url: "../php/alterarGreenCode.php",
					async: false,
					data: {
						change: "1", path: hold, color: "yellow",
					},
					success: function(confirm){
		
					}

				});

				$.ajax({
					type: "POST",
					dataType: "json",
					url: "../php/favoritarEmail.php",
					async: false,
					data: {
						valor: "1", emailAtual: emailVAR, arcName: dest,
					},
					success: function(confirm){

					}

				});

			}
			else{ 
				$(this).removeClass("yellow");
				$(this).addClass("transparentV");

				$.ajax({

					type: "POST",
					dataType: "json",
					url: "../php/alterarGreenCode.php",
					async: false,
					data: {
						change: "0", path: hold, color: "yellow",
					},
					success: function(confirm){

					}

				});	

				$.ajax({
					type: "POST",
					dataType: "json",
					url: "../php/favoritarEmail.php",
					async: false,
					data: {
						valor: "0", emailAtual: emailVAR, arcName: dest,
					},
					success: function(confirm){

					}

				});

				
			}

			notYELLOW = true;

		});
		
		notYELLOW = true;

	})


	$("#mensagens #msgBox #btVisualizado").each(function(){

		$(this).click(function(){

			var hold = $(this).closest("#msgBox").find("td:eq(9)").text().toString(); // link email deste button.
			if($(this).hasClass("green")){ 
				$(this).removeClass("green"); 
				$(this).addClass("transparentV"); 

				$.ajax({

					type: "POST",
					dataType: "json",
					url: "../php/alterarGreenCode.php",
					async: false,
					data: {
						change: "0", path: hold, color: "green",
					},
					success: function(confirm){

					}

				});

			}
			else{ 
				$(this).removeClass("transparentV");
				$(this).addClass("green");

				$.ajax({

					type: "POST",
					dataType: "json",
					url: "../php/alterarGreenCode.php",
					async: false,
					data: {
						change: "1", path: hold, color: "green",
					},
					success: function(confirm){

					}

				});
				
			}

			notGREEN = true;

		});

		notGREEN = true;


	});





	$("#mensagens #msgBox").click(function(){

		if(!notRED && !notYELLOW && !notGREEN && !notORANGE){

			$(this).click(function(){
				
				this.id = "teste";

				
				var destS = $("#teste #DE").text().toString().replace("De: ", "");
				var assS = $("#teste #ASSUNTO").text().toString().replace("Assunto: ", "");
				var msgS = $("#teste #MENSAGEM").text().toString().replace("Mensagem: ", "");

				var linkS = $("#teste #LINK").text().toString().replace("../xml/emails/", "");
				linkS = linkS.replace((emailVAR+"/caixaDeEntrada/"), "");


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
		        		dest: emailVAR, cc: deQuem, ass: assS, msg: msgS, fromScreen: "../html/caixaDeEntrada.html", link: linkS,
		        	},
		        	sucess: function(received){

		          	}

		        });

		        window.open("../html/telaVisualizarMensagem.html", "_self");

			});
			

		}
		else{
			notRED = false; notYELLOW = false; notGREEN = false; notORANGE = false;
		}


	});


});

