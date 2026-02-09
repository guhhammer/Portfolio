

	function goNext(){

		window.open("../html/caixaDeEntrada.html", "_self");

	}

	function wait(){

		setInterval(function() {goNext();}, Math.floor((Math.random() * 3500) + 600));

	}
