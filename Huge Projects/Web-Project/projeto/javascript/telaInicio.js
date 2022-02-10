

$(document).ready(function(){

	$("#btForget").click(function(){

		window.open("../html/telaInicioOpcoes.html", "_self");

	});


	$("#btCreate").click(function(){

		window.open("../html/telaInicioOpcoes.html", "_self");

	});


	$("#btLogin").click(function(){


		if($("#usuario").val() == ""){
			alert("Campo usuário precisa ser preenchido!");
		}
		else{
			if($("#senha").val() == ""){
				alert("Campo senha precisa ser preenchido!");
			}
			else{
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "../php/validar.php",
					async: false,
					data: {
						email: $("#usuario").val(), senha: $("#senha").val(),
					},
					success: function(retorno){

						if(retorno["status"] == "emailfounded"){

							if(retorno["password"] == "match"){

								$.ajax({
									type: "POST",
									dataType: "json",
									url: "../php/definirUsuarioAtual.php",
									async: false,
									data: {
										login: $("#usuario").val(),
									},
									success: function(retorno2){

									}

								});


								window.open("../html/entrandoBubble.html", "_self");

							}	
							else{

								alert("Senha digitada não corresponde a senha do email:   "+$("#usuario").val()+". Por favor, digite sua senha ou altere-a clicando em \'Esqueci minha senha\'!");

								$("#senha").val("");

							}


						}
						else{

							alert("Email não foi encontrado. Por favor, entre com outra conta ou crie uma clicando em \'Criar uma conta\'!");

							$("#usuario").val("");
							$("#senha").val("");
						}

					}
					
				});

			}
		}

	});

	
});

