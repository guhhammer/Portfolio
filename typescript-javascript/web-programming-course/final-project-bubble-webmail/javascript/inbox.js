


$(document).ready(function(){


	var emailVAR = "";

	// AJAX call that returns the sender user (Email Cc).
	$.ajax({

		type: "POST",
		dataType: "json",
		url: "../php/findCurrentUser.php",
		async: false,
		data: {

		},
		success: function(result){
			emailVAR = result["email"];
		}

	});



	var emailsJSONVAR = [];
	$.ajax({
		
		type: "POST",
		dataType: "json",
		url: "../php/getAllEmailsNames.php",
		async: false,
		data: {
			caminho: ("../xml/emails/"+(emailVAR)+"/inbox/"),
		},
		success: function(emailsJSON){
			emailsJSONVAR = emailsJSON;
		}

	});


	var counter = 0;
	while((emailsJSONVAR).length > counter){
		
		var index = counter;

		var recipient = "";
		var emailCc = "";
		var subject = "";
		var message = "";
		var greenCode = "";

		var link = ("../xml/emails/"+emailVAR+"/inbox/"+emailsJSONVAR[index]);
		var referencia = "../html/inbox.html";

		$.ajax({
		
			type: "POST",
			dataType: "json",
			url: "../php/getEmailInfo.php",
			async: false,
			data: {
				caminho: link,
			},
			success: function(info){
				recipient = info["recipient"];
				emailCc = info["emailCc"];
				subject = info["subject"];
				message = info["message"];
				greenCode = info["greenCode"];
				codeAmarelo =info["yellowCode"];
			}
		
		});

		var classeVerde = "";
		if(greenCode == "1"){ classeVerde = "green"; }
		else{ classeVerde = "transparentV"; }

		var classeAmarela = "";
		if(codeAmarelo == "1"){ classeAmarela = "yellow"; }
		else{ classeAmarela = "transparentV"; }

		var string = "";

		string += "<tr id=\"trNum"+counter+"\"><td><button id=\"msgBox\" class=\"draftDesign\"><table><tr>";
		string += "<td><button id=\"btDelete\" class=\"delete\"></button></td>";
		string += "<td><button id=\"btSpam\" class=\"spam\"></button></td>";
		string += "<td><button id=\"btFavorite\" class=\""+classeAmarela+"\"></button></td>";
		string += "<td><button id=\"btViewed\" class=\""+classeVerde+"\"></button></td>";		
		string += "<td><p id=\"FROM\">From: "+emailCc+"</p></td>";
		string += "<td><p id\"space1\"> | </p></td>";
		string += "<td><p id=\"SUBJECT\">Subject: "+subject+"</p></td>";
		string += "<td><p id=\"space2\"> | </p></td>";
		string += "<td><p id=\"MESSAGE\">Message: "+message+"</p></td>";
		string += "<td><p id=\"LINK\" class=\"hide\">"+link+"</p></td>";
		string += "<td><p id=\"REFERENCIA\" class=\"hide\">"+referencia+"</p></td>";
		string += "</tr></table></button></td></tr>";


		$("#messages").append(string.toString());


		counter += 1;

	}


	if($("#messages").text().toString().length < 26){
		$("#messages").append("<tr class=\"appended\"><td><img src=\"../images/mail.png\" id=\"emptyBox\"></tr></td>");
	}
	else{
		$(".appended").remove();
	}

	
	$("#messages #FROM").each(function(){

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


	$("#messages #SUBJECT").each(function(){

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

		
	$("#messages #MESSAGE").each(function(){

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

	$("#favorites").append("<td><button id=\"yellowShow\" class=\"yellowHIDE\" disabled></button></td>");
	$("#messages #msgBox #btFavorite").each(function(){

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


	$("#JunkMail").append("<td><button id=\"orangeShow\" class=\"orangeHIDE\" disabled></button></td>");
	$("#messages #msgBox #btSpam").each(function(){

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


	$("#DeletedItems").append("<td><button id=\"redShow\" class=\"redHIDE\" disabled></button></td>");
	$("#messages #msgBox #btDelete").each(function(){

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


	$("#messages #msgBox #btDelete").each(function(){

		$(this).click(function(){

			var hold = $(this).closest("#msgBox").find("td:eq(9)").text().toString(); // link email deste button.

			var dest = hold.replace("../xml/emails/", "");
			dest = dest.replace(emailVAR+"/inbox/", "");

			$.ajax({

				type: "POST",
				dataType: "json",
				url: "../php/deleteFile.php",
				async: false,
				data: {
					emailAtual: emailVAR, arcName: dest, actualPaste: "inbox",
				},
				success: function(confirm){

				}

			});

			notRED = true;

			window.open("../html/inbox.html", "_self");

		});

		notRED = true;

	});



	$("#messages #msgBox #btSpam").each(function(){

		$(this).click(function(){

			var hold = $(this).closest("#msgBox").find("td:eq(9)").text().toString(); // link email deste button.

			var dest = hold.replace("../xml/emails/", "");
			dest = dest.replace(emailVAR+"/inbox/", "");

			$.ajax({

				type: "POST",
				dataType: "json",
				url: "../php/markEmailAsSpam.php",
				async: false,
				data: {
					emailAtual: emailVAR, arcName: dest, actualPaste: "inbox",
				},
				success: function(confirm){

				}

			});

			notORANGE = true;

			window.open("../html/inbox.html", "_self");

		});

		notORANGE = true;

	});



	$("#messages #msgBox #btFavorite").each(function(){

		$(this).click(function(){

			var hold = $(this).closest("#msgBox").find("td:eq(9)").text().toString(); // link email deste button.

			var dest = hold.replace("../xml/emails/", "");
			dest = dest.replace(emailVAR+"/inbox/", "");

			if($(this).hasClass("transparentV")){ 
				$(this).removeClass("transparentV"); 
				$(this).addClass("yellow"); 

				$.ajax({

					type: "POST",
					dataType: "json",
					url: "../php/updateGreenCode.php",
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
					url: "../php/favoriteEmail.php",
					async: false,
					data: {
						value: "1", emailAtual: emailVAR, arcName: dest,
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
					url: "../php/updateGreenCode.php",
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
					url: "../php/favoriteEmail.php",
					async: false,
					data: {
						value: "0", emailAtual: emailVAR, arcName: dest,
					},
					success: function(confirm){

					}

				});

				
			}

			notYELLOW = true;

		});
		
		notYELLOW = true;

	})


	$("#messages #msgBox #btViewed").each(function(){

		$(this).click(function(){

			var hold = $(this).closest("#msgBox").find("td:eq(9)").text().toString(); // link email deste button.
			if($(this).hasClass("green")){ 
				$(this).removeClass("green"); 
				$(this).addClass("transparentV"); 

				$.ajax({

					type: "POST",
					dataType: "json",
					url: "../php/updateGreenCode.php",
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
					url: "../php/updateGreenCode.php",
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





	$("#messages #msgBox").click(function(){

		if(!notRED && !notYELLOW && !notGREEN && !notORANGE){

			$(this).click(function(){
				
				this.id = "test";

				
				var destS = $("#test #FROM").text().toString().replace("From: ", "");
				var assS = $("#test #SUBJECT").text().toString().replace("Subject: ", "");
				var msgS = $("#test #MESSAGE").text().toString().replace("Message: ", "");

				var linkS = $("#test #LINK").text().toString().replace("../xml/emails/", "");
				linkS = linkS.replace((emailVAR+"/inbox/"), "");


				var deQuem = "";
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "../php/getEmailInfo.php",
					async: false,
					data: {
						caminho: $("#test #LINK").text().toString(),
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
		        		dest: emailVAR, cc: deQuem, ass: assS, msg: msgS, fromScreen: "../html/inbox.html", link: linkS,
		        	},
		        	sucess: function(received){

		          	}

		        });

		        window.open("../html/viewMessageScreen.html", "_self");

			});
			

		}
		else{
			notRED = false; notYELLOW = false; notGREEN = false; notORANGE = false;
		}


	});


});

