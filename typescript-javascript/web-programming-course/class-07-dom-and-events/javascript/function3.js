

$(document).ready(function(){

	var list = [];

	$("#add").click(function(){

		list.splice((list.length()-1), 0, $("#songName").val());

	});


	$("#remove").click(function(){
		list.splice($("#position").val(), $("#quantity").val());
	});

	show();

});

function show(){

	$("#songList").val() = "";

	var delimiter = ", ";

	for (var i = 0; i < list.length()-1; i++) {
		

		if(i == list.length()-1){
			delimiter = "";
		}

		$("#songList").append(delimiter+list);
	}

	$("#songList").append(list);

}