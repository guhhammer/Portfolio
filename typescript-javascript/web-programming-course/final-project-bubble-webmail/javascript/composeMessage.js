
$(document).ready(function(){

	// AJAX call that returns the sender user (Email Cc).
	$.ajax({

		type: "POST",
		dataType: "json",
		url: "../php/findCurrentUser.php",
		async: false,
		data: {

		},
		success: function(result){
			$("#emailCc").val(result["email"]);
		}

	});


	var emailRecipientVar = 1;

	$("#emailRecipient").click(function(){ $("#emailRecipient").removeClass("redBorder"); $("#emailRecipient").val(""); });


	$("#emailRecipient").change(function(){


		$.ajax({

			type: "POST",
			dataType: "json",
			url: "../php/findRecipient.php",
			data: {
				email: $("#emailRecipient").val(),
			},
			success: function(verify){

				if(verify["status"] == "doesNotExist"){ emailRecipientVar = 0; }
				else{ emailRecipientVar = 1; }

			}

		});

	});



	$("#btSend").click(function(){

		if($("#emailRecipient").val() == ""){ emailRecipientVar = 0; }

		if(emailRecipientVar == 0){ 

			alert("The recipient email does not exist. Please enter an existing email.");
			$("#emailRecipient").addClass("redBorder");
		
		}
		else{
				

			var dest = $("#emailRecipient").val();		
			var cc = $("#emailCc").val();
			var ass = $("#emailSubject").val();
			var msg = $("#emailMessage").val();

			var codCc = "";
			var codDest = "";

			$.ajax({
				type: "POST",
				dataType: "json",
				url: "../php/emailCode/getCode.php",
				async: false,
				data: {
					path: ("../../xml/emails/"+cc+"/sentItems/info/code.xml"),
				},
				success: function(code){
					codCc = code["value"];
				}

			});

			$.ajax({
				type: "POST",
				dataType: "json",
				url: "../php/emailCode/getCode.php",
				async: false,
				data: {
					path: ("../../xml/emails/"+dest+"/inbox/info/code.xml"),
				},
				success: function(code){
					codDest = code["value"];
				}

			});

			$.ajax({
				type: "POST",
				dataType: "json",
				url: "../php/makeEmail.php",
				async: false,
				data: {
					recipient: dest, recipient_code: codDest, 
					emailCc: cc, emailCc_code: codCc,
					subject: ass, message: msg,
					pathFrom: ("../xml/emails/"+cc+"/sentItems/emailSentItems"+codCc+".xml"),
					pathTo: ("../xml/emails/"+dest+"/inbox/emailInbox"+codDest+".xml"),
				},
				success: function(confirm){
				
				}

			});

			$.ajax({
				type: "POST",
				dataType: "json",
				url: "../php/emailCode/updateCode.php",
				async: false,
				data: {
					path: ("../../xml/emails/"+cc+"/sentItems/info/code.xml"),
				},
				success: function(confirm){
				
				}

			});

			$.ajax({
				type: "POST",
				dataType: "json",
				url: "../php/emailCode/updateCode.php",
				async: false,
				data: {
					path: ("../../xml/emails/"+dest+"/inbox/info/code.xml"),
				},
				success: function(confirm){
				
				}

			});

			window.open("../html/inbox.html", "_self");

		}

	});


	$("#btSaveDraft").click(function(){

		var emailPara = $("#emailRecipient").val();
		var emailDe = $("#emailCc").val();
		var subject = $("#emailSubject").val();
		var message = $("#emailMessage").val();

		if(emailPara == ""){ emailPara = " ";}
		if(subject == ""){ subject = " ";}
		if(message == ""){ message = " ";}

		var draftCode = "";

		$.ajax({
			type: "POST",
			dataType: "json",
			url: "../php/emailCode/getCode.php",
			async: false,
			data: {
				path: ("../../xml/emails/"+emailDe+"/drafts/info/code.xml"),
			},
			success: function(code){
				draftCode = code["value"];
			}

		});

		$.ajax({
			type: "POST",
			dataType: "json",
			url: "../php/makeDraft.php",
			async: false,
			data: {
				draft_code: draftCode, 
				recipient: emailPara, emailCc: emailDe, 
				subject: subject, message: message,
				draftPath: ("../xml/emails/"+emailDe+"/drafts/emailDrafts"+draftCode+".xml"),
			},
			success: function(confirm){
			}

		});

		$.ajax({
			type: "POST",
			dataType: "json",
			url: "../php/emailCode/updateCode.php",
			async: false,
			data: {
				path: ("../../xml/emails/"+emailDe+"/drafts/info/code.xml"),
			},
			success: function(confirm){
			}

		});

		window.open("../html/drafts.html", "_self");

	});


	$("#btAbortar").click(function(){

		var emailPara = $("#emailRecipient").val();
		var emailDe = $("#emailCc").val();
		var subject = $("#emailSubject").val();
		var message = $("#emailMessage").val();

		if(emailPara == ""){ emailPara = " ";}
		if(subject == ""){ subject = " ";}
		if(message == ""){ message = " ";}

		var codArchive = "";

		$.ajax({
			type: "POST",
			dataType: "json",
			url: "../php/emailCode/getCode.php",
			async: false,
			data: {
				path: ("../../xml/emails/"+emailDe+"/archive/info/code.xml"),
			},
			success: function(code){
				codArchive = code["value"];
			}

		});


		// The same .php script is used to archive the message. 
		$.ajax({
			type: "POST",
			dataType: "json",
			url: "../php/makeDraft.php",
			async: false,
			data: {
				draft_code: codArchive, 
				recipient: emailPara, emailCc: emailDe, 
				subject: subject, message: message,
				draftPath: ("../xml/emails/"+emailDe+"/archive/emailArchive"+codArchive+".xml"),
			},
			success: function(confirm){

			}

		});

		$.ajax({
			type: "POST",
			dataType: "json",
			url: "../php/emailCode/updateCode.php",
			async: false,
			data: {
				path: ("../../xml/emails/"+emailDe+"/archive/info/code.xml"),
			},
			success: function(confirm){
			
			}

		});

		window.open("../html/inbox.html", "_self");

	});



});