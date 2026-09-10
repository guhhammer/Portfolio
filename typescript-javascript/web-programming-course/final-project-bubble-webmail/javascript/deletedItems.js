

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
			caminho: ("../xml/emails/"+(emailVAR)+"/deletedItems/"),
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

		var link = ("../xml/emails/"+emailVAR+"/deletedItems/"+emailsJSONVAR[index]);
		var referencia = "../html/deletedItems.html";

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
		string += "<td><p id=\"TO\">"+show+"</p></td>";
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

	var notRED = false;
		

	$("#messages #msgBox #btDelete").each(function(){

		$(this).click(function(){

			var hold = $(this).closest("#msgBox").find("td:eq(6)").text().toString(); // link email deste button.

			var dest = hold.replace("../xml/emails/", "");
			dest = dest.replace(emailVAR+"/deletedItems/", "");

			$.ajax({

				type: "POST",
				dataType: "json",
				url: "../php/deleteFile.php",
				async: false,
				data: {
					emailAtual: emailVAR, arcName: dest, actualPaste: "deletedItems",
				},
				success: function(confirm){

				}

			});

			notRed = true;

			window.open("../html/deletedItems.html", "_self");

		});

		notRED = true;

	});


	$("#messages #msgBox").click(function(){

		if(!notRED){

			$(this).click(function(){
				
				this.id = "test";

				
				var destS = $("#test #TO").text().toString().replace("To: ", "");
				var assS = $("#test #SUBJECT").text().toString().replace("Subject: ", "");
				var msgS = $("#test #MESSAGE").text().toString().replace("Message: ", "");

				var linkS = $("#test #LINK").text().toString().replace("../xml/emails/", "");
				linkS = linkS.replace((emailVAR+"/deletedItems/"), "");

		        $.ajax({
		        	type: "POST",
		        	dataType: "json",
		        	url: "../php/makeBridgeShowMessage.php",
		        	async: false,
		        	data: {
		        		dest: destS, cc: emailVAR, ass: assS, msg: msgS, fromScreen: "../html/deletedItems.html", link: linkS,
		        	},
		        	sucess: function(received){

		          	}

		        });

		        window.open("../html/viewMessageScreen.html", "_self");

			});

		}
		else{
			notRED = false;
		}


	});


});

