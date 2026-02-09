


<?php


	function adicao($valor1, $valor2){

		return $valor1 + $valor2;

	}

	function subtracao($valor1, $valor2){

		return $valor1 - $valor2;

	}

	function multiplicacao($valor1, $valor2){

		return $valor1 * $valor2;

	}

	function divisao($valor1, $valor2){

		return $valor1 / $valor2;

	}

	function escolher($op, $x, $y){

		switch($op){

			case "adicao":

				echo "<br>Adição de $x e $y: ".adicao($x, $y);

				break;

			case "subtracao":

				echo "<br>Subtração de $x e $y: ".subtracao($x, $y);

				break;

			case "multiplicacao":

				echo "<br>Multiplicação de $x e $y: ".multiplicacao($x, $y);

				break;

			case "divisao":

				echo "<br>Divisão de $x e $y: ".divisao($x, $y);

				break;

		}

	}


	escolher("adicao", 6, 4);

?>