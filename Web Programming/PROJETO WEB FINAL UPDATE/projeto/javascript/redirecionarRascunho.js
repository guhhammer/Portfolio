

$(document).ready(function(){

	function createRascunho(){

		var emailPara = $("#emailDestinatario").val();
		var emailDe = $("#emailCc").val();
		var assunto = $("#emailAssunto").val();
		var mensagem = $("#emailMensagem").val();
		
		var codRascunho = "";

		$.ajax({
			type: "POST",
			dataType: "json",
			url: "../php/emailCodigo/getCodigo.php",
			async: false,
			data: {
				path: ("../../xml/emails/"+emailDe+"/rascunhos/info/codigo.xml"),
			},
			success: function(codigo){
				codRascunho = codigo["valor"];
			}

		});

		$.ajax({
			type: "POST",
			dataType: "json",
			url: "../php/makeRascunho.php",
			async: false,
			data: {
				rascunho_codigo: codRascunho, 
				destinatario: emailPara, emailCc: emailDe, 
				assunto: assunto, mensagem: mensagem,
				pathRascunho: ("../xml/emails/"+emailDe+"/rascunhos/emailRascunhos"+codRascunho+".xml"),
			},
			success: function(confirm){
			}

		});

		$.ajax({
			type: "POST",
			dataType: "json",
			url: "../php/emailCodigo/alterarCodigo.php",
			async: false,
			data: {
				path: ("../../xml/emails/"+emailDe+"/rascunhos/info/codigo.xml"),
			},
			success: function(confirm){
			}

		});
	}

	$("#btCriarMensagem").click(function(){ createRascunho(); });


	$("#btFavoritos").click(function(){ createRascunho(); });


	$("#btCaixaDeEntrada").click(function(){ createRascunho(); });


	$("#btItensEnviados").click(function(){ createRascunho(); });


	$("#btItensExcluidos").click(function(){ createRascunho(); });


	$("#btLixoEletronico").click(function(){ createRascunho(); });


	$("#btRascunhos").click(function(){ createRascunho(); });


	$("#btArquivoMorto").click(function(){ createRascunho(); });


});

