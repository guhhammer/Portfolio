


/* NOME: GUSTAVO HAMMERSCHMIDT */


$(document).ready(function(){


	var listaDeMusicas = [];


	$("#nomeMusica").focus(function(){


		$("#divRemover").addClass("blured");
		$("#listaMusicas").addClass("blured");

	}).blur(function(){

		$("#divRemover").removeClass("blured");
		$("#listaMusicas").removeClass("blured");

	});


	$("#removerPosicao").focus(function(){


		$("#divAdicionar").addClass("blured");
		$("#listaMusicas").addClass("blured");

	}).blur(function(){

		$("#divAdicionar").removeClass("blured");
		$("#listaMusicas").removeClass("blured");
		
	});

	$("#removerQuantidade").focus(function(){


		$("#divAdicionar").addClass("blured");
		$("#listaMusicas").addClass("blured");

	}).blur(function(){

		$("#divAdicionar").removeClass("blured");
		$("#listaMusicas").removeClass("blured");
		
	});


	$("#btAdicionar").click(function(){

		if($("#nomeMusica").val() != ""){
		
			listaDeMusicas.splice(listaDeMusicas.length, 0, $("#nomeMusica").val().toString());
			
			$("#nomeMusica").val("");

			showLista();
		}		

	});



		
	$("#removerPosicao").change(function(){

		var nome = ("#cell"+$("#removerPosicao").val()).toString();

		$(nome).addClass("highlight");

		if($("#removerQuantidade").val() != ""){

			var min = parseInt($("#removerPosicao").val());
			var max = parseInt($("#removerQuantidade").val());


			for(var i = min; i < (min+max); i++){

				var nome = ("#cell"+i).toString();
				
				$(nome).addClass("highlight");
			
			}

		}


		if($("#removerPosicao").val() == ""){
				
			$("#removerQuantidade").val("");

			$("#listaM tr td").each(function(){
				$(this).removeClass("highlight");
			});
		
		}

	});

	


	$("#removerQuantidade").change(function(){

		var min = parseInt($("#removerPosicao").val());
		var max = parseInt($("#removerQuantidade").val());


		for(var i = min; i < (min+max); i++){

			var nome = ("#cell"+i).toString();
			
			$(nome).addClass("highlight");
		
		}

		if($("#removerQuantidade").val() == ""){
			
			$("#removerPosicao").val("");

			$("#listaM tr td").each(function(){
				$(this).removeClass("highlight");
			});
			

		}
		
	});

	

	$("#btRemover").click(function(){

		if(($("#removerPosicao").val() != "") && 
			($("#removerQuantidade").val() != "")){


			listaDeMusicas.splice(($("#removerPosicao").val()), ($("#removerQuantidade").val()));

			$("#removerPosicao").val("");
			$("#removerQuantidade").val("");

			showLista();

		}
		else{
			alert("Defina uma posição e uma quantidade");
		}

	});




	function showLista(){

		var musicas = "";

		$("#listaM tr").remove();

		for (var i = 0; i < listaDeMusicas.length; i++){ 


			if(i % 10 == 0){
				musicas += "<tr>";
			}

			
			musicas += ("<td id=\"cell"+i+"\">"+listaDeMusicas[i]+"</td>").toString();
			
			if((i % 10 == 9) || (i == listaDeMusicas.length-1)){
				musicas += "</tr>";
			}

		}

		$("#listaM").append(musicas);


	};

});


