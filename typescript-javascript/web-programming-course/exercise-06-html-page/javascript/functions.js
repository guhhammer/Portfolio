


/* NAME: GUSTAVO HAMMERSCHMIDT */


$(document).ready(function(){


	var listDeMusicas = [];


	$("#songName").focus(function(){


		$("#divRemove").addClass("blured");
		$("#songList").addClass("blured");

	}).blur(function(){

		$("#divRemove").removeClass("blured");
		$("#songList").removeClass("blured");

	});


	$("#removePosition").focus(function(){


		$("#divAdd").addClass("blured");
		$("#songList").addClass("blured");

	}).blur(function(){

		$("#divAdd").removeClass("blured");
		$("#songList").removeClass("blured");
		
	});

	$("#removeQuantage").focus(function(){


		$("#divAdd").addClass("blured");
		$("#songList").addClass("blured");

	}).blur(function(){

		$("#divAdd").removeClass("blured");
		$("#songList").removeClass("blured");
		
	});


	$("#btAdd").click(function(){

		if($("#songName").val() != ""){
		
			listDeMusicas.splice(listDeMusicas.length, 0, $("#songName").val().toString());
			
			$("#songName").val("");

			showLista();
		}		

	});



		
	$("#removePosition").change(function(){

		var name = ("#cell"+$("#removePosition").val()).toString();

		$(name).addClass("highlight");

		if($("#removeQuantage").val() != ""){

			var min = parseInt($("#removePosition").val());
			var max = parseInt($("#removeQuantage").val());


			for(var i = min; i < (min+max); i++){

				var name = ("#cell"+i).toString();
				
				$(name).addClass("highlight");
			
			}

		}


		if($("#removePosition").val() == ""){
				
			$("#removeQuantage").val("");

			$("#listM tr td").each(function(){
				$(this).removeClass("highlight");
			});
		
		}

	});

	


	$("#removeQuantage").change(function(){

		var min = parseInt($("#removePosition").val());
		var max = parseInt($("#removeQuantage").val());


		for(var i = min; i < (min+max); i++){

			var name = ("#cell"+i).toString();
			
			$(name).addClass("highlight");
		
		}

		if($("#removeQuantage").val() == ""){
			
			$("#removePosition").val("");

			$("#listM tr td").each(function(){
				$(this).removeClass("highlight");
			});
			

		}
		
	});

	

	$("#btRemove").click(function(){

		if(($("#removePosition").val() != "") && 
			($("#removeQuantage").val() != "")){


			listDeMusicas.splice(($("#removePosition").val()), ($("#removeQuantage").val()));

			$("#removePosition").val("");
			$("#removeQuantage").val("");

			showLista();

		}
		else{
			alert("Set a position and a quantity");
		}

	});




	function showLista(){

		var songs = "";

		$("#listM tr").remove();

		for (var i = 0; i < listDeMusicas.length; i++){ 


			if(i % 10 == 0){
				songs += "<tr>";
			}

			
			songs += ("<td id=\"cell"+i+"\">"+listDeMusicas[i]+"</td>").toString();
			
			if((i % 10 == 9) || (i == listDeMusicas.length-1)){
				songs += "</tr>";
			}

		}

		$("#listM").append(songs);


	};

});


