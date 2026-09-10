

	function goNext(){

		window.open("../html/inbox.html", "_self");

	}

	function wait(){

		setInterval(function() {goNext();}, Math.floor((Math.random() * 3500) + 600));

	}
