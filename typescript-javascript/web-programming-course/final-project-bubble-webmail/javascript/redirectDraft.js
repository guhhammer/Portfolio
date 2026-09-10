

$(document).ready(function(){

	function createDraft(){

		var emailPara = $("#emailRecipient").val();
		var emailDe = $("#emailCc").val();
		var subject = $("#emailSubject").val();
		var message = $("#emailMessage").val();
		
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
	}

	$("#btComposeMessage").click(function(){ createDraft(); });


	$("#btFavorites").click(function(){ createDraft(); });


	$("#btInbox").click(function(){ createDraft(); });


	$("#btSentItems").click(function(){ createDraft(); });


	$("#btDeletedItems").click(function(){ createDraft(); });


	$("#btJunkMail").click(function(){ createDraft(); });


	$("#btDrafts").click(function(){ createDraft(); });


	$("#btArchive").click(function(){ createDraft(); });


});

