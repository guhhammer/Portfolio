

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
			caminho: ("../xml/emails/"+(emailVAR)+"/junkMail/"),
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

		var link = ("../xml/emails/"+emailVAR+"/junkMail/"+emailsJSONVAR[index]);
		var referencia = "../html/junkMail.html";

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
			}
		
		});

		var show = "";
		if(emailCc != emailVAR){ show = "From: "+emailCc; }
		else{ show = "To: "+recipient}

		var string = "";

		string += "<tr id=\"trNum"+counter+"\"><td><button id=\"msgBox\" class=\"draftDesign\"><table><tr>";
		string += "<td><button id=\"btDelete\" class=\"delete\"></button></td>";
		string += "<td><button id=\"btSpam\" class=\"spam\"></button></td>";		
		string += "<td><p id=\"FROM\">"+show+"</p></td>";
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



	$("#messages #TO").each(function(){

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


	$("#messages #SUBJECT").each(function(){

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

		
	$("#messages #MESSAGE").each(function(){

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


	var notRED = false;
	var notORANGE = false;
		

	$("#messages #msgBox #btDelete").each(function(){

		$(this).click(function(){

			var hold = $(this).closest("#msgBox").find("td:eq(7)").text().toString(); // link email deste button.

			var dest = hold.replace("../xml/emails/", "");
			dest = dest.replace(emailVAR+"/junkMail/", "");

			$.ajax({

				type: "POST",
				dataType: "json",
				url: "../php/deleteFile.php",
				async: false,
				data: {
					emailAtual: emailVAR, arcName: dest, actualPaste: "junkMail",
				},
				success: function(confirm){

				}

			});

			notRED = true;

			window.open("../html/junkMail.html", "_self");

		});

		notRED = true;

	});

	$("#messages #msgBox #btSpam").each(function(){

		$(this).click(function(){

			var hold = $(this).closest("#msgBox").find("td:eq(7)").text().toString(); // link email deste button.

			var dest = hold.replace("../xml/emails/", "");
			dest = dest.replace(emailVAR+"/junkMail/", "");

			$.ajax({

				type: "POST",
				dataType: "json",
				url: "../php/markEmailAsSpam.php",
				async: false,
				data: {
					emailAtual: emailVAR, arcName: dest, actualPaste: "junkMail",
				},
				success: function(confirm){

				}

			});

			notORANGE = true;

			window.open("../html/inbox.html", "_self");

		});

		notORANGE = true;

	});


	$("#messages #msgBox").click(function(){

		if(!notRED && !notORANGE){

			$(this).click(function(){
				
				this.id = "test";

				
				var destS = $("#test #FROM").text().toString().replace("From: ", "");
				var assS = $("#test #SUBJECT").text().toString().replace("Subject: ", "");
				var msgS = $("#test #MESSAGE").text().toString().replace("Message: ", "");

				var linkS = $("#test #LINK").text().toString().replace("../xml/emails/", "");
				linkS = linkS.replace((emailVAR+"/junkMail/"), "");

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
		        		dest: emailVAR, cc: deQuem, ass: assS, msg: msgS, fromScreen: "../html/junkMail.html", link: linkS,
		        	},
		        	sucess: function(received){

		          	}

		        });

		        window.open("../html/viewMessageScreen.html", "_self");

			});

		}
		else{
			notRED = false; notORANGE = false;
		}


	});


});

