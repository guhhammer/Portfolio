

$(document).ready(function(){

	$("#btForget").click(function(){

		window.open("../html/homeScreenOptions.html", "_self");

	});


	$("#btCreate").click(function(){

		window.open("../html/homeScreenOptions.html", "_self");

	});


	$("#btLogin").click(function(){


		if($("#user").val() == ""){
			alert("The user field must be filled in!");
		}
		else{
			if($("#password").val() == ""){
				alert("The password field must be filled in!");
			}
			else{
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "../php/validate.php",
					async: false,
					data: {
						email: $("#user").val(), password: $("#password").val(),
					},
					success: function(result){

						if(result["status"] == "emailfounded"){

							if(result["password"] == "match"){

								$.ajax({
									type: "POST",
									dataType: "json",
									url: "../php/setCurrentUser.php",
									async: false,
									data: {
										login: $("#user").val(),
									},
									success: function(retorno2){

									}

								});


								window.open("../html/enteringBubble.html", "_self");

							}	
							else{

								alert("The password entered does not match the password for the email:   "+$("#user").val()+". Please enter your password or change it by clicking \'I forgot my password\'!");

								$("#password").val("");

							}


						}
						else{

							alert("Email not found. Please sign in with another account or create one by clicking \'Create an account\'!");

							$("#user").val("");
							$("#password").val("");
						}

					}
					
				});

			}
		}

	});

	
});

