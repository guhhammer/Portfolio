$(document).ready(function(){


	$("#somar").click(function(){
		var x = parseInt($("#soma1").val());
		var y = parseInt($("#soma2").val());

		var soma = x+y;

		$("#somRes").val(soma);
	});


	$("#subtrair").click(function(){
		var x = parseInt($("#subtracao1").val());
		var y = parseInt($("#subtracao2").val());

		var subtracao = x - y;

		$("#subRes").val(subtracao);
	});


	$("#multiplicar").click(function(){
		var x = parseInt($("#multiplicacao1").val());
		var y = parseInt($("#multiplicacao2").val());

		var multiplicacao = x * y;
		
		$("#mulRes").val(multiplicacao);
	});


	$("#dividir").click(function(){
		var x = parseInt($("#divisao1").val());
		var y = parseInt($("#divisao2").val());

		var divisao = x / y;

		$("#divRes").val(divisao);
	});


	$("#sumLimpar").click(function(){
		$("#soma1").val("");
		$("#soma2").val("")
		$("#somRes").val("");

	});


	$("#subLimpar").click(function(){
		$("#subtracao1").val("");
		$("#subtracao2").val("");
		$("#subRes").val("");
	});


	$("#mulLimpar").click(function(){
		$("#multiplicacao1").val("");
	    $("#multiplicacao2").val("");
	    $("#mulRes").val("");
	});


	$("#divLimpar").click(function(){
		$("#divisao1").val("");
		$("#divisao2").val("");
		$("#divRes").val("");
	});


});