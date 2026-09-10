

$(document).ready(function(){


	$("#sumr").click(function(){
		var x = parseInt($("#sum1").val());
		var y = parseInt($("#sum2").val());

		var sum = x+y;

		alert(sum);
	});


	$("#subtrair").click(function(){
		var x = parseInt($("#subtraction1").val());
		var y = parseInt($("#subtraction2").val());

		var subtraction = x - y;

		alert(subtraction);
	});


	$("#multiplicar").click(function(){
		var x = parseInt($("#multiplication1").val());
		var y = parseInt($("#multiplication2").val());

		var multiplication = x * y;
		
		alert(multiplication);
	});


	$("#dividir").click(function(){
		var x = parseInt($("#division1").val());
		var y = parseInt($("#division2").val());

		var division = x / y;

		alert(division);
	});

});