




<?php



	/*
	$numeros = array(1,2,3,4,5);
	echo "<br><br>";
	foreach ($numeros as $valor){
		echo $valor."<br>";
	}


	$frutas = array(
		"vermelhas" => array("maca","melancia"),
		"yellow" => array("banana","melon","pineapple"),
		"green" => array("pear","lime","avocado")
	);

	foreach($frutas as $cores => $valor){
		echo "<br>".$cores." ==>";

		foreach($valor as $fruta){
			echo $fruta."     ";
		}
		echo "<br><br>";

	}*/



	$movies = array(
		2017 => array("It", "Logan", "Justice League", "Blade Runner", "Dunkirk"),
		2018 => array("Aquaman", "Venom", "Burning", "Creed 2", "Slender Man"),
		2019 => array("Toy Story 4", "It - A coisa parte 2", "Coringa", "Ad Astra", "Zombieland 2")
	);

	foreach($movies as $year => $names){

		echo "<br>"."$year teve ".count($names)." movies: ";
		
		$count = 0;
		foreach($names as $name){

			echo ($count == count($names)-1) ? " e ".$name.". " :( ($count == count($names)-2) ? $name." " : $name.", ");
			$count++;

		}

		echo "<br>";	

	}


?>