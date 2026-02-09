
$(document).ready(function(){

	var notGREEN = false;
	var notRED = false;
	var notYELLOW = false;


	var limit1 = 18;

	var space1 = $("#xx #email #de").text().length;
	if(space1 > limit1){

		var value = $("#xx #email #de").text();

		var string = "";
		var counter = 0;
		while(counter < 20){

			string = (string+value.charAt(counter));
			counter = counter + 1;

		}
		$("#xx #email #de").text(string+"...");

	}

	if($("#xx #email #assunto").text().length > 25){

		var value = $("#xx #email #assunto").text();

		var string = "";
		var counter = 0;
		while(counter < 23){

			string = (string+value.charAt(counter));
			counter = counter + 1;

		}
		$("#xx #email #assunto").text(string+"...");

	}


	if($("#xx #email #mensagem").text().length > 50){

		var value = $("#xx #email #mensagem").text();

		var string = "";
		var counter = 0;
		while(counter < 47){

			string = (string+value.charAt(counter));
			counter = counter + 1;

		}
		$("#xx #email #mensagem").text(string+"...");

	}




	$("#xx #email #check").click(function(){

		if($("#xx #email #check").hasClass("green")){

			$("#xx #email #check").removeClass("green");
			$("#xx #email #check").addClass("transparent");

		}
		else{

			$("#xx #email #check").removeClass("transparent");
			$("#xx #email #check").addClass("green");

		}

		notGREEN = true;

	});


	$("#xx #email #exclude").click(function(){

		notRED = true;

	});


	$("#xx #email #paraRascunho").click(function(){

		if($("#xx #email #paraRascunho").hasClass("transparent")){

			$("#xx #email #paraRascunho").removeClass("transparent");
			$("#xx #email #paraRascunho").addClass("yellow");

		}
		else{

			$("#xx #email #paraRascunho").removeClass("yellow");
			$("#xx #email #paraRascunho").addClass("transparent");

		}

		notYELLOW = true;

	});



	$("#xx #email").click(function(){

		if(!notRED && !notGREEN && !notYELLOW){

			window.open("https://www.youtube.com", "_self");

		}
		else{
			notRED = false; notGREEN = false; notYELLOW = false;
		}


	});

	

});