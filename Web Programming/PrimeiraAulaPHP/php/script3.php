




<?php



	/*
	$numeros = array(1,2,3,4,5);
	echo "<br><br>";
	foreach ($numeros as $valor){
		echo $valor."<br>";
	}


	$frutas = array(
		"vermelhas" => array("maca","melancia"),
		"amarelas" => array("banana","melão","abacaxi"),
		"verdes" => array("pera","limão","abacate")
	);

	foreach($frutas as $cores => $valor){
		echo "<br>".$cores." ==>";

		foreach($valor as $fruta){
			echo $fruta."     ";
		}
		echo "<br><br>";

	}*/



	$filmes = array(
		2017 => array("It - a coisa", "Logan", "Liga da Justiça", "Blade Runner", "Dunkirk"),
		2018 => array("Aquaman", "Venom", "Burning", "Creed 2", "Slender Man"),
		2019 => array("Toy Story 4", "It - A coisa parte 2", "Coringa", "Ad Astra", "Zombieland 2")
	);

	foreach($filmes as $ano => $nomes){

		echo "<br>"."$ano teve ".count($nomes)." filmes: ";
		
		$count = 0;
		foreach($nomes as $nome){

			echo ($count == count($nomes)-1) ? " e ".$nome.". " :( ($count == count($nomes)-2) ? $nome." " : $nome.", ");
			$count++;

		}

		echo "<br>";	

	}


?>