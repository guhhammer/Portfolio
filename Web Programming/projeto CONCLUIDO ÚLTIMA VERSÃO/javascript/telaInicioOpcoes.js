

$(document).ready(function(){

	document.getElementById("choseCriar").style.display = "none";
	document.getElementById("choseAlterar").style.display = "none";
	document.getElementById("criarP").style.display = "none";
	document.getElementById("alterarP").style.display = "none";
	var criarVAR = 0;
	var alterarVAR = 0
	var flag1 =	0;
	var flag2 = 0;
	var flag3 = 0;
	var flag4 = 0;
	var flag5 = 0;


	$("#btRetroceder").click(function(){

		window.open("../html/telaInicio.html", "_self");

	});

	
	$("#btCriar").click(function(){

		flag1 = 0;
		flag2 = 0;
		flag3 = 0;
		flag4 = 0;
		flag5 = 0;
		$("#Alogin").removeClass("redBorder");
		$("#Asenha").removeClass("redBorder");
		$("#AconfirmarSenha").removeClass("redBorder");
		$("#AconfirmarSenha").removeClass("backgroundRedColor");
		$("#Alogin").val("");
		$("#Asenha").val("");
		$("#AconfirmarSenha").val("");
		$("#Clogin").removeClass("redBorder");
		$("#Csenha").removeClass("redBorder");
		$("#CconfirmarSenha").removeClass("redBorder");
		$("#CconfirmarSenha").removeClass("backgroundRedColor");
		$("#Clogin").val("");
		$("#Csenha").val("");
		$("#CconfirmarSenha").val("");
		document.getElementById("criarP").style.display = "none";
		document.getElementById("alterarP").style.display = "none";

		if(alterarVAR == 1){document.getElementById("choseAlterar").style.display = "none"; alterarVAR = 0}
		if(criarVAR == 1){ document.getElementById("choseCriar").style.display = "none"; criarVAR = 0; }
		else{	document.getElementById("choseCriar").style.display = "block";  criarVAR = 1; }

	});


	$("#btCriarLogin").click(function(){

		if($("#Clogin").val() == ""){ $("#Clogin").addClass("redBorder"); flag1 = 0; }
		else{ $("#Clogin").removeClass("redBorder"); flag1 = 1; }	


		if(flag1 == 1){
			
			$.ajax({
				type: "POST",
				dataType: "json",
				async: false,
				url: "../php/verificarLogin.php",
				data: {
					login: $("#Clogin").val(),
				},
				success: function(checker){
					if(checker["status"] == "alreadyExists"){
						$("#Clogin").addClass("redBorder"); 
						
						flag1 = 0;

						$string = "Email "+$("#Clogin").val()+" já possui dono. Tente novamente com outro email.";

						alert($string);

						$("#Clogin").val("");
						$("#Csenha").val("");
						$("#CconfirmarSenha").val("");

					}
					else{
						
						$("#Clogin").removeClass("redBorder"); flag1 = 1;

					}
				}

			});
		}
		
		if($("#Csenha").val() == ""){ $("#Csenha").addClass("redBorder"); flag2 = 0; }
		else{ $("#Csenha").removeClass("redBorder"); flag2 = 1; }

		if($("#CconfirmarSenha").val() == ""){ $("#CconfirmarSenha").addClass("redBorder"); flag3 = 0;}
		else{ $("#CconfirmarSenha").removeClass("redBorder"); flag3 = 1; }

		if($("#Csenha").val() != $("#CconfirmarSenha").val()){
		
			$("#CconfirmarSenha").addClass("backgroundRedColor");
			$("#CconfirmarSenha").val("");
			document.getElementById("criarP").style.display = "block";
			flag4 = 0;
		
		}
		else{
		
			$("#CconfirmarSenha").removeClass("backgroundRedColor");
			document.getElementById("criarP").style.display = "none";
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
				url: "../php/criarLogin.php",
				data: {
					login: $("#Clogin").val(),
					senha: $("#Csenha").val(),
				},
				success: function(confirm){
					
				}

			});


			alert("Email "+$("#Clogin").val()+" criado! Junte-se à Bolha! ");	
						
			window.open("../html/telaInicio.html", "_self");
	
		}
		else{

		}

	

	});


	$("#btAlterar").click(function(){

		flag1 = 0;
		flag2 = 0;
		flag3 = 0;
		flag4 = 0;
		flag5 = 0;

		$("#Clogin").removeClass("redBorder");
		$("#Csenha").removeClass("redBorder");
		$("#CconfirmarSenha").removeClass("redBorder");
		$("#CconfirmarSenha").removeClass("backgroundRedColor");
		$("#Clogin").val("");
		$("#Csenha").val("");
		$("#CconfirmarSenha").val("");
		$("#Alogin").removeClass("redBorder");
		$("#Asenha").removeClass("redBorder");
		$("#AconfirmarSenha").removeClass("redBorder");
		$("#AconfirmarSenha").removeClass("backgroundRedColor");
		$("#Alogin").val("");
		$("#Asenha").val("");
		$("#AconfirmarSenha").val("");
		document.getElementById("criarP").style.display = "none";
		document.getElementById("alterarP").style.display = "none";

		if(criarVAR == 1){ document.getElementById("choseCriar").style.display = "none"; criarVAR = 0; }
		if(alterarVAR == 1){ document.getElementById("choseAlterar").style.display = "none"; alterarVAR = 0; }
		else{	document.getElementById("choseAlterar").style.display = "block";  alterarVAR = 1; }

	});


	$("#btAlterarSenha").click(function(){

		if($("#Alogin").val() == ""){ $("#Alogin").addClass("redBorder"); flag1 = 0;}
		else{ $("#Alogin").removeClass("redBorder"); flag1 = 1;}

		if(flag1 == 1){
			
			$.ajax({
				type: "POST",
				dataType: "json",
				async: false,
				url: "../php/verificarLogin.php",
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

						$string = "Nenhum Email com este nome: "+$("#Alogin").val()+" foi encontrado. Digite o seu email novamente.";

						alert($string);

						$("#Alogin").val("");
						$("#Asenha").val("");
						$("#AconfirmarSenha").val("");

						flag1 = 0;

					}
				}

			});
		}

		if($("#Asenha").val() == ""){ $("#Asenha").addClass("redBorder"); flag2 = 0;}
		else{ $("#Asenha").removeClass("redBorder"); flag2 = 1;}

		if($("#AconfirmarSenha").val() == ""){ $("#AconfirmarSenha").addClass("redBorder"); flag3 = 0;}
		else{ $("#AconfirmarSenha").removeClass("redBorder"); flag3 = 1;}

		if($("#Asenha").val() != $("#AconfirmarSenha").val()){
			$("#AconfirmarSenha").addClass("backgroundRedColor");
			$("#AconfirmarSenha").val("");
			document.getElementById("alterarP").style.display = "block";
			flag4 = 0;
		}
		else{ $("#AconfirmarSenha").removeClass("backgroundRedColor"); 
			document.getElementById("alterarP").style.display = "none";
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
				url: "../php/alterarLogin.php",
				data: {
					login: $("#Alogin").val(),
					senha: $("#Asenha").val(),
				},
				success: function(process){
					
					alert("Email "+$("#Alogin").val()+" alterado! Junte-se à Bolha! ");	

						
					window.open("../html/telaInicio.html", "_self");
				
				}

			});
	
		}
		else{

		}


	});

	

});


