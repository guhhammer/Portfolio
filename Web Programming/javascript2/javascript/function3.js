

$(document).ready(function(){

	var lista = [];

	$("#adicionar").click(function(){

		lista.splice((lista.length()-1), 0, $("#nomeMusica").val());

	});


	$("#remover").click(function(){
		lista.splice($("#posicao").val(), $("#quantidade").val());
	});

	show();

});

function show(){

	$("#listaMusicas").val() = "";

	var delimitador = ", ";

	for (var i = 0; i < lista.length()-1; i++) {
		

		if(i == lista.length()-1){
			delimitador = "";
		}

		$("#listaMusicas").append(delimitador+lista);
	}

	$("#listaMusicas").append(lista);

}