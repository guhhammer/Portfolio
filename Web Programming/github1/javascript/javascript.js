





var dados = [{nome:"Alan", sobrenome:"Turing", idade:"20", telefone:"988821232"},
			 {nome:"Oppe", sobrenome:"Nonere", idade:"30", telefone:"888211232"},
			 {nome:"Umma", sobrenome:"Boring", idade:"40", telefone:"981231232"},
			 {nome:"Berr", sobrenome:"Fdesas", idade:"25", telefone:"988828882"},
			 {nome:"Sire", sobrenome:"Joking", idade:"29", telefone:"988812312"}];


$(document).ready(function(){

	showLista();

});


function showLista(){

	var linhas = "";

	for (var i = 0; i < dados.length; i++){ 

		linhas = "";

		linhas += "<tr>";
		linhas += "<td>"+dados[i].nome+"</td>";
		linhas += "<td>"+dados[i].sobrenome+"</td>";
	 	linhas += "<td>"+dados[i].idade+"</td>";
	 	linhas += "<td>"+dados[i].telefone+"</td>";
	 	linhas += "</tr>";


		$("#tbl").append(linhas);

	}

}