


<?php


	function addition($valor1, $valor2){

		return $valor1 + $valor2;

	}

	function subtraction($valor1, $valor2){

		return $valor1 - $valor2;

	}

	function multiplication($valor1, $valor2){

		return $valor1 * $valor2;

	}

	function division($valor1, $valor2){

		return $valor1 / $valor2;

	}

	function escolher($op, $x, $y){

		switch($op){

			case "addition":

				echo "<br>Addition de $x e $y: ".addition($x, $y);

				break;

			case "subtraction":

				echo "<br>Subtraction de $x e $y: ".subtraction($x, $y);

				break;

			case "multiplication":

				echo "<br>Multiplication de $x e $y: ".multiplication($x, $y);

				break;

			case "division":

				echo "<br>Division de $x e $y: ".division($x, $y);

				break;

		}

	}


	escolher("addition", 6, 4);

?>