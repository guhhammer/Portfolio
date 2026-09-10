




$(document).ready(function(){


	var email = "";

	$.ajax({
		type: "POST",
		dataType: "json",
		url: "../php/findCurrentUser.php",
		async: false,
		data: {

		},
		success: function(result){

			email = result["email"];

		}

	});


	var btTabBar = "1";
	var btFavorites = "1";
	var btInbox = "1";
	var btJunkMail = "1";
	var btDrafts = "1";
	var btSentItems = "1";
	var btDeletedItems = "1";
	var btArchive = "1";

	$.ajax({
		type: "POST",
		dataType: "json",
		url: "../php/getCurrentValuesFromUser.php",
		async: false,
		data: {
			email: email,
		},
		success: function(result){

			btTabBar = result["btTabBar"];
			btFavorites = result["btFavorites"];
			btInbox = result["btInbox"];
			btJunkMail = result["btJunkMail"];
			btDrafts = result["btDrafts"];
			btSentItems = result["btSentItems"];
			btDeletedItems = result["btDeletedItems"];
			btArchive = result["btArchive"];
			
			if(result["btTabBar"] == "1"){ document.getElementById("guiaBarra").style.display = "block"; }
			else{ document.getElementById("guiaBarra").style.display = "none"; }
			
			if(result["btFavorites"] == "1"){ document.getElementById("btFavorites").style.display = "block"; }
			else{ document.getElementById("btFavorites").style.display = "none"; }

			if(result["btInbox"] == "1"){ document.getElementById("btInbox").style.display = "block"; }
			else{ document.getElementById("btInbox").style.display = "none"; }

			if(result["btJunkMail"] == "1"){ document.getElementById("btJunkMail").style.display = "block"; }
			else{ document.getElementById("btJunkMail").style.display = "none"; }

			if(result["btDrafts"] == "1"){ document.getElementById("btDrafts").style.display = "block"; }
			else{ document.getElementById("btDrafts").style.display = "none"; }

			if(result["btSentItems"] == "1"){ document.getElementById("btSentItems").style.display = "block"; }
			else{ document.getElementById("btSentItems").style.display = "none"; }

			if(result["btDeletedItems"] == "1"){ document.getElementById("btDeletedItems").style.display = "block"; }
			else{ document.getElementById("btDeletedItems").style.display = "none"; }

			if(result["btArchive"] == "1"){ document.getElementById("btArchive").style.display = "block"; }
			else{ document.getElementById("btArchive").style.display = "none"; }
		}

	});

	function updateValueOf(email, name, value){

		$.ajax({
			type: "POST",
			dataType: "json",
			url: "../php/updateButtonValue.php",
			data: {
				email: email, buttonName: name, value: value,
			},
			success: function(result){

			}

		});

	}


	$("#btRecolher").click(function(){

		if(btTabBar == "0"){	
			document.getElementById("guiaBarra").style.display = "block";
			btTabBar = "1";
			updateValueOf(email, "btTabBar", 1);
			$("#messages").removeClass("withoutGuide");
			$("#messages").addClass("withGuide");
			$("#composeMessages").removeClass("withoutGuide_createMessage");
			$("#composeMessages").addClass("withGuide_createMessage");
			$("#viewMessages").removeClass("withoutGuide_seeMessage");
			$("#viewMessages").addClass("withGuide_seeMessage");

	    }
		else{	
			document.getElementById("guiaBarra").style.display = "none"; 
			btTabBar = "0"; 
			updateValueOf(email, "btTabBar", 0);
			$("#messages").removeClass("withGuide");
			$("#messages").addClass("withoutGuide");
			$("#composeMessages").removeClass("withGuide_createMessage");
			$("#composeMessages").addClass("withoutGuide_createMessage");
			$("#viewMessages").removeClass("withGuide_seeMessage");
			$("#viewMessages").addClass("withoutGuide_seeMessage");

		}

	});

	$("#primeiro").click(function(){

		if(btFavorites == "1"){	document.getElementById("btFavorites").style.display = "none"; btFavorites = "0"; updateValueOf(email, "btFavorites", 0); }
		else{	document.getElementById("btFavorites").style.display = "block";  btFavorites = "1"; updateValueOf(email, "btFavorites", 1); }

	});

	$("#segundo").click(function(){

		if(btInbox == "1"){	document.getElementById("btInbox").style.display = "none";	 btInbox = "0"; updateValueOf(email, "btInbox", 0); }
		else{	document.getElementById("btInbox").style.display = "block"; btInbox = "1";	updateValueOf(email, "btInbox", 1); }

	});

	$("#terceiro").click(function(){

		if(btJunkMail == "1"){	document.getElementById("btJunkMail").style.display = "none";	 btJunkMail = "0";	updateValueOf(email, "btJunkMail", 0); }
		else{	document.getElementById("btJunkMail").style.display = "block";  btJunkMail = "1";	updateValueOf(email, "btJunkMail", 1); }

	});

	$("#quarto").click(function(){

		if(btDrafts == "1"){	document.getElementById("btDrafts").style.display = "none";	btDrafts = "0"; updateValueOf(email, "btDrafts", 0); }
		else{	document.getElementById("btDrafts").style.display = "block";  btDrafts = "1"; updateValueOf(email, "btDrafts", 1); }

	});

	$("#quinto").click(function(){

		if(btSentItems == "1"){	document.getElementById("btSentItems").style.display = "none";	 btSentItems = "0"; updateValueOf(email, "btSentItems", 0); }
		else{	document.getElementById("btSentItems").style.display = "block";  btSentItems = "1"; updateValueOf(email, "btSentItems", 1); }

	});

	$("#sexto").click(function(){

		if(btDeletedItems == "1"){	document.getElementById("btDeletedItems").style.display = "none";	 btDeletedItems = "0"; updateValueOf(email, "btDeletedItems", 0); }
		else{	document.getElementById("btDeletedItems").style.display = "block";  btDeletedItems = "1";	updateValueOf(email, "btDeletedItems", 1); }

	});

	$("#setimo").click(function(){

		if(btArchive == "1"){	document.getElementById("btArchive").style.display = "none"; btArchive = "0"; updateValueOf(email, "btArchive", 0); }
		else{	document.getElementById("btArchive").style.display = "block";  btArchive = "1";	updateValueOf(email, "btArchive", 1); }

	});

});



