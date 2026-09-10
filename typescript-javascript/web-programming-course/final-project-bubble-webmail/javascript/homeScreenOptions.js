

$(document).ready(function(){

	document.getElementById("choseCreate").style.display = "none";
	document.getElementById("choseUpdate").style.display = "none";
	document.getElementById("createP").style.display = "none";
	document.getElementById("updateP").style.display = "none";
	var createVAR = 0;
	var updateVAR = 0
	var flag1 =	0;
	var flag2 = 0;
	var flag3 = 0;
	var flag4 = 0;
	var flag5 = 0;


	$("#btRetroceder").click(function(){

		window.open("../html/homeScreen.html", "_self");

	});

	
	$("#btCreate").click(function(){

		flag1 = 0;
		flag2 = 0;
		flag3 = 0;
		flag4 = 0;
		flag5 = 0;
		$("#Alogin").removeClass("redBorder");
		$("#updatePassword").removeClass("redBorder");
		$("#updateConfirmPassword").removeClass("redBorder");
		$("#updateConfirmPassword").removeClass("backgroundRedColor");
		$("#Alogin").val("");
		$("#updatePassword").val("");
		$("#updateConfirmPassword").val("");
		$("#Clogin").removeClass("redBorder");
		$("#createPassword").removeClass("redBorder");
		$("#createConfirmPassword").removeClass("redBorder");
		$("#createConfirmPassword").removeClass("backgroundRedColor");
		$("#Clogin").val("");
		$("#createPassword").val("");
		$("#createConfirmPassword").val("");
		document.getElementById("createP").style.display = "none";
		document.getElementById("updateP").style.display = "none";

		if(updateVAR == 1){document.getElementById("choseUpdate").style.display = "none"; updateVAR = 0}
		if(createVAR == 1){ document.getElementById("choseCreate").style.display = "none"; createVAR = 0; }
		else{	document.getElementById("choseCreate").style.display = "block";  createVAR = 1; }

	});


	$("#btCreateLogin").click(function(){

		if($("#Clogin").val() == ""){ $("#Clogin").addClass("redBorder"); flag1 = 0; }
		else{ $("#Clogin").removeClass("redBorder"); flag1 = 1; }	


		if(flag1 == 1){
			
			$.ajax({
				type: "POST",
				dataType: "json",
				async: false,
				url: "../php/checkLogin.php",
				data: {
					login: $("#Clogin").val(),
				},
				success: function(checker){
					if(checker["status"] == "alreadyExists"){
						$("#Clogin").addClass("redBorder"); 
						
						flag1 = 0;

						$string = "Email "+$("#Clogin").val()+" is already taken. Try again with another email.";

						alert($string);

						$("#Clogin").val("");
						$("#createPassword").val("");
						$("#createConfirmPassword").val("");

					}
					else{
						
						$("#Clogin").removeClass("redBorder"); flag1 = 1;

					}
				}

			});
		}
		
		if($("#createPassword").val() == ""){ $("#createPassword").addClass("redBorder"); flag2 = 0; }
		else{ $("#createPassword").removeClass("redBorder"); flag2 = 1; }

		if($("#createConfirmPassword").val() == ""){ $("#createConfirmPassword").addClass("redBorder"); flag3 = 0;}
		else{ $("#createConfirmPassword").removeClass("redBorder"); flag3 = 1; }

		if($("#createPassword").val() != $("#createConfirmPassword").val()){
		
			$("#createConfirmPassword").addClass("backgroundRedColor");
			$("#createConfirmPassword").val("");
			document.getElementById("createP").style.display = "block";
			flag4 = 0;
		
		}
		else{
		
			$("#createConfirmPassword").removeClass("backgroundRedColor");
			document.getElementById("createP").style.display = "none";
			flag4 = 1;
		
		}

		if((flag1 == 1) && (flag2 == 1) && (flag3 == 1) && (flag4 == 1)){
		
			flag5 = 1;

		}
		else{
		
			flag5 = 0;
		
		}

		if(flag5 == 1){
		
			$.ajax({
				type: "POST",
				url: "../php/createLogin.php",
				data: {
					login: $("#Clogin").val(),
					password: $("#createPassword").val(),
				},
				success: function(confirm){
					
				}

			});


			alert("Email "+$("#Clogin").val()+" created! Join the Bubble! ");	
						
			window.open("../html/homeScreen.html", "_self");
	
		}
		else{

		}

	

	});


	$("#btUpdate").click(function(){

		flag1 = 0;
		flag2 = 0;
		flag3 = 0;
		flag4 = 0;
		flag5 = 0;

		$("#Clogin").removeClass("redBorder");
		$("#createPassword").removeClass("redBorder");
		$("#createConfirmPassword").removeClass("redBorder");
		$("#createConfirmPassword").removeClass("backgroundRedColor");
		$("#Clogin").val("");
		$("#createPassword").val("");
		$("#createConfirmPassword").val("");
		$("#Alogin").removeClass("redBorder");
		$("#updatePassword").removeClass("redBorder");
		$("#updateConfirmPassword").removeClass("redBorder");
		$("#updateConfirmPassword").removeClass("backgroundRedColor");
		$("#Alogin").val("");
		$("#updatePassword").val("");
		$("#updateConfirmPassword").val("");
		document.getElementById("createP").style.display = "none";
		document.getElementById("updateP").style.display = "none";

		if(createVAR == 1){ document.getElementById("choseCreate").style.display = "none"; createVAR = 0; }
		if(updateVAR == 1){ document.getElementById("choseUpdate").style.display = "none"; updateVAR = 0; }
		else{	document.getElementById("choseUpdate").style.display = "block";  updateVAR = 1; }

	});


	$("#btUpdatePassword").click(function(){

		if($("#Alogin").val() == ""){ $("#Alogin").addClass("redBorder"); flag1 = 0;}
		else{ $("#Alogin").removeClass("redBorder"); flag1 = 1;}

		if(flag1 == 1){
			
			$.ajax({
				type: "POST",
				dataType: "json",
				async: false,
				url: "../php/checkLogin.php",
				data: {
					login: $("#Alogin").val(),
				},
				success: function(checker){
					if(checker["status"] == "alreadyExists"){
						$("#Alogin").removeClass("redBorder"); 
						
						flag1 = 1;

					}
					else{
						
						$("#Alogin").addClass("redBorder"); 

						$string = "No email with this name: "+$("#Alogin").val()+" was found. Enter your email again.";

						alert($string);

						$("#Alogin").val("");
						$("#updatePassword").val("");
						$("#updateConfirmPassword").val("");

						flag1 = 0;

					}
				}

			});
		}

		if($("#updatePassword").val() == ""){ $("#updatePassword").addClass("redBorder"); flag2 = 0;}
		else{ $("#updatePassword").removeClass("redBorder"); flag2 = 1;}

		if($("#updateConfirmPassword").val() == ""){ $("#updateConfirmPassword").addClass("redBorder"); flag3 = 0;}
		else{ $("#updateConfirmPassword").removeClass("redBorder"); flag3 = 1;}

		if($("#updatePassword").val() != $("#updateConfirmPassword").val()){
			$("#updateConfirmPassword").addClass("backgroundRedColor");
			$("#updateConfirmPassword").val("");
			document.getElementById("updateP").style.display = "block";
			flag4 = 0;
		}
		else{ $("#updateConfirmPassword").removeClass("backgroundRedColor"); 
			document.getElementById("updateP").style.display = "none";
			flag4 = 1;
		}

		if((flag1 == 1) && (flag2 == 1) && (flag3 == 1) && (flag4 == 1)){
			flag5 = 1;
		}
		else{
			flag5 = 0;
		}


		if(flag5 == 1){
		
			$.ajax({
				type: "POST",
				url: "../php/updateLogin.php",
				data: {
					login: $("#Alogin").val(),
					password: $("#updatePassword").val(),
				},
				success: function(process){
					
					alert("Email "+$("#Alogin").val()+" updated! Join the Bubble! ");	

						
					window.open("../html/homeScreen.html", "_self");
				
				}

			});
	
		}
		else{

		}


	});

	

});


