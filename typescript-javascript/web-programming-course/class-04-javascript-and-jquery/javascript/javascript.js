





var data = [{name:"Alan", lastName:"Turing", age:"20", phone:"988821232"},
			 {name:"Oppe", lastName:"Nonere", age:"30", phone:"888211232"},
			 {name:"Umma", lastName:"Boring", age:"40", phone:"981231232"},
			 {name:"Berr", lastName:"Fdesas", age:"25", phone:"988828882"},
			 {name:"Sire", lastName:"Joking", age:"29", phone:"988812312"}];


$(document).ready(function(){

	showLista();

});


function showLista(){

	var rows = "";

	for (var i = 0; i < data.length; i++){ 

		rows = "";

		rows += "<tr>";
		rows += "<td>"+data[i].name+"</td>";
		rows += "<td>"+data[i].lastName+"</td>";
	 	rows += "<td>"+data[i].age+"</td>";
	 	rows += "<td>"+data[i].phone+"</td>";
	 	rows += "</tr>";


		$("#tbl").append(rows);

	}

}