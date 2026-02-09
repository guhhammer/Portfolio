

$(document).ready(function(){


	$("#somar").click(function(){
		var x = parseInt($("#soma1").val());
		var y = parseInt($("#soma2").val());

		var soma = x+y;

		alert(soma);
	});


	$("#subtrair").click(function(){
		var x = parseInt($("#subtracao1").val());
		var y = parseInt($("#subtracao2").val());

		var subtracao = x - y;

		alert(subtracao);
	});


	$("#multiplicar").click(function(){
		var x = parseInt($("#multiplicacao1").val());
		var y = parseInt($("#multiplicacao2").val());

		var multiplicacao = x * y;
		
		alert(multiplicacao);
	});


	$("#dividir").click(function(){
		var x = parseInt($("#divisao1").val());
		var y = parseInt($("#divisao2").val());

		var divisao = x / y;

		alert(divisao);
	});

});