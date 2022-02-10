


	function goNext(){

		window.open("./html/telaInicio.html", "_self");

	}
	

	function wait(){

		setInterval(function() {goNext();}, Math.floor((Math.random() * 3500) + 600));

	}

