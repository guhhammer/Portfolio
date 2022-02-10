



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
		
			$("#mensagens #msgBox #DE").each(function(){  $(this).highlight(search, "highlight"); });

		}

		if(nome == "rascunhos" || nome == "itensEnviados" || nome == "arquivoMorto" || nome == "itensExcluidos"){
		
			$("#mensagens #msgBox #PARA").each(function(){  $(this).highlight(search, "highlight"); });

		}

		$("#mensagens #msgBox #ASSUNTO").each(function(){  $(this).highlight(search, "highlight"); });

		$("#mensagens #msgBox #MENSAGEM").each(function(){  $(this).highlight(search, "highlight"); });

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




});