$(document).ready(function(){


	$("#sumr").click(function(){
		var x = parseInt($("#sum1").val());
		var y = parseInt($("#sum2").val());

		var sum = x+y;

		$("#somRes").val(sum);
	});


	$("#subtrair").click(function(){
		var x = parseInt($("#subtraction1").val());
		var y = parseInt($("#subtraction2").val());

		var subtraction = x - y;

		$("#subRes").val(subtraction);
	});


	$("#multiplicar").click(function(){
		var x = parseInt($("#multiplication1").val());
		var y = parseInt($("#multiplication2").val());

		var multiplication = x * y;
		
		$("#mulRes").val(multiplication);
	});


	$("#dividir").click(function(){
		var x = parseInt($("#division1").val());
		var y = parseInt($("#division2").val());

		var division = x / y;

		$("#divRes").val(division);
	});


	$("#sumClear").click(function(){
		$("#sum1").val("");
		$("#sum2").val("")
		$("#somRes").val("");

	});


	$("#subClear").click(function(){
		$("#subtraction1").val("");
		$("#subtraction2").val("");
		$("#subRes").val("");
	});


	$("#mulClear").click(function(){
		$("#multiplication1").val("");
	    $("#multiplication2").val("");
	    $("#mulRes").val("");
	});


	$("#divClear").click(function(){
		$("#division1").val("");
		$("#division2").val("");
		$("#divRes").val("");
	});


});