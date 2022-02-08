<?php 

	include_once("../Database/config.php");
	include_once("../Database/selector.php");

	function get_command_information($userID, &$status){


		$chain_of_command_arr = [["Commanding Officer", "Rank LName", "FName"],["Executive Officer", "Rank LName", "FName"],["Command Senior Chief", "Rank LName", "FName"],
							["Officer Instructor", "Rank LName", "FName"],["Assistant OI", "Rank LName", "FName"],["Battalion CO", "Rank LName", "FName"],
							["Battalion XO", "Rank LName", "FName"],["Battalion CMC", "Rank LName", "FName"],["Company CO", "Rank LName", "FName"],
							["Company XO", "Rank LName", "FName"],["Company 1stSgt", "Rank LName", "FName"],["Platoon Commander", "Rank LName", "FName"],
							["Platoon Sergeant", "Rank LName", "FName"],["Squad Leader", "Rank LName", "FName"],["Member", "Rank LName", "FName"]];
	

		$chain_to_string = "<tr><td><p id=\"lvl_0\">Lvl</p></td><td><p id=\"bil_0\">Bilet</p></td><td><p id=\"name_0\">Name</p></td></tr>";
		$level = 0;

		for($i = 0; $i < sizeof($chain_of_command_arr); $i++){
			$level = $i+1;
			$chain_to_string = $chain_to_string.
							   ("<tr><td><p id=\"lvl_".$level."\">".$level."</p></td>".
								"<td><p id=\"bil_".$level."\">". $chain_of_command_arr[$i][0] ."</p></td>".
								"<td><p id=\"name_".$level."\">". $chain_of_command_arr[$i][1].", ".$chain_of_command_arr[$i][2]."</p></td></tr>");
		}

		/* TO BE DONE AND ADAPTED.
		getValues( [&$rank_, &$lastname, &$firstname, &$middleInitial, &$gender_value],
				   ["rank", "lastName", "firstName", "middleInitial", "gender"],
				    "users", 
				   [["id", $id]],
				    $divs["command"]
				 );
		*/

		return 
		(

			"<div id=\"command_info\" class=\"home_div\">".
				"<p id=\"title\" class=\"title_c\">Command Information</p>".
				"<p id=\"chain\">Chain of Command:</p>".
				"<table align=\"center\">".$chain_to_string."</table>".
			"</div>"

		);



	}


?>