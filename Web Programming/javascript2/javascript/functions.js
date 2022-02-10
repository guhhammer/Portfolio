
$(document).ready(function(){


	var impar = 1;
	var par = 0;


	$("#showimpar").val(impar);
	$("#showpar").val(par);

	$("#impar").click(function(){

		if( impar < $("#max").val()){
			
			if(impar < $("#max").val()-1){

			impar += 2;

			}

		}
		else{

		}

		$("#showimpar").val(impar);

	});


	$("#par").click(function(){

		if( par < $("#max").val()){
			
			if(par+1 != $("#max").val()){
			
				par += 2;
			
			}

		}
		else{

		}

		$("#showpar").val(par);

	});


	$("#imparFor").click(function(){
		fcolocaNumerosImpares();
	});



	function fcolocaNumerosImpares(){


		var max = parseInt($("#max").val());

		$("#showImparFor").html("");

		var delimitador = ", ";

		for(var i = 0; i <= max; i++){

			if(i % 2 == 1){

				if( i == max-1){

					delimitador = "";

				}

				$("showImparFor").append(i + delimitador);

			}

		}

	}


	$("#parFor").click(function(){
		fcolocaNumerosPares();
	});



	function fcolocaNumerosPares(){


		var max = parseInt($("#max").val());

		$("#showParFor").html("");

		var delimitador = ", ";

		for(var i = 0; i <= max; i++){

			if(i % 2 == 0){

				if( i == max-1){

					delimitador = "";

				}

				$("showParFor").append(i + delimitador);

			}

		}

	}


});