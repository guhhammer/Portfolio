


$(document).ready(function(){


	var backVAR = "";

	var userAtual = "";

	var linkEmail = "";


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


	// AJAX call that returns the sender user (Email Cc).
	$.ajax({

		type: "POST",
		dataType: "json",
		url: "../php/getBridgeShowMessage.php",
		async: false,
		data: {

		},
		success: function(infos){

			$("#emailRecipient").val(infos["recipient"]);
			
			$("#emailCc").val(infos["emailCc"]);
			userAtual = infos["emailCc"];
			$("#emailCc").prop("disabled", true);


			$("#emailSubject").val(infos["subject"]);
			$("#emailMessage").val(infos["message"]);

			backVAR = infos["screen"];

			linkEmail = infos["link"];

		}

	});


	$("#btBack").click(function(){

		window.open(backVAR, "_self");

	});


	$("#btDelete").click(function(){

		var clean = backVAR.replace("../html/", "");
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

		window.open(backVAR, "_self");

	});


});