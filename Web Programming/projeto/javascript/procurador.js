



$(document).ready(function(){


	var click1 = false;

	$("#barraPesquisar").focus();

	$("#barraPesquisar").click(function(){

		if(click1){

			$("#barraPesquisar").val("");

			location.reload();

			click1 = false;

		}

	});


	var nome = location.pathname.replace("/projeto/html/","").replace(".html","");

	$("#btPesquisar").click(function(){

		var search = $("#barraPesquisar").val().toString();
		
		if(nome == "favoritos" || nome == "caixaDeEntrada" || nome == "lixoEletronico" || nome == "itensExcluidos"){
		
			$("#mensagens #msgBox #showDE").each(function(){  $(this).highlight(search, "highlight"); });

		}

		if(nome == "rascunhos" || nome == "itensEnviados" || nome == "arquivoMorto" || nome == "itensExcluidos"){
		
			$("#mensagens #msgBox #showPARA").each(function(){  $(this).highlight(search, "highlight"); });

		}

		$("#mensagens #msgBox #showASSUNTO").each(function(){  $(this).highlight(search, "highlight"); });

		$("#mensagens #msgBox #showMENSAGEM").each(function(){  $(this).highlight(search, "highlight"); });

		click1 = true;

	});



	jQuery.fn.highlight = function (str, className) {
	    
	    var regex = new RegExp(str, "gi");
	    return this.each(function () {
	        $(this).contents().filter(function() {
	            return this.nodeType == 3 && regex.test(this.nodeValue);
	        }).replaceWith(function() {
	            return (this.nodeValue || "").replace(regex, function(match) {
	                return "<span class=\"" + className + "\">" + match + "</span>";
	            });
	        });
	    });

	};


	$("#btPesquisar").click(function(){

		$("#mensagens #msgBox").each(function(){


			$(this).find("#showASSUNTO").each(function(){

				if(!$(this).find("span").hasClass("highlight")){ 

					$(this).closest("#msgBox").find("#showMENSAGEM").each(function(){

						if(!$(this).find("span").hasClass("highlight")){

							if($(this).closest("#msgBox").has("#showDE")){

								$(this).closest("#msgBox").find("#showDE").each(function(){

									if(!$(this).find("span").hasClass("highlight")){

										$(this).closest("#msgBox").remove();

									}

								});							

							}
							if($(this).closest("#msgBox").has("#showPARA")){

								$(this).closest("#msgBox").find("#showPARA").each(function(){

									if(!$(this).find("span").hasClass("highlight")){

										$(this).closest("#msgBox").remove();

									}

								});

							}
						
						}

					});

				}

			});

		});

	});

});