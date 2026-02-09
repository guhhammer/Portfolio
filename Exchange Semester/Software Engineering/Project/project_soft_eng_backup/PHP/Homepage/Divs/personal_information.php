<?php 
	
	include_once("../Database/config.php");
	include_once("../Database/selector.php");
	

	function get_personal_information($userID, &$status){

		$rank_ = "Rank"; // Defined.
		$lastname = "LName"; // Defined.
		$firstname = "FNAME"; // Defined.
		$middleInitial = "MI"; // Defined.

		$dob_value = "NOT DEFINED YET";  // 06/25/2001

		$gender_value = "M"; // DEFINED.

		$cps_value = "NOT DEFINED YET"; // A/1/1 
		$ccmdb_value = "NOT DEFINED YET";  // Squad Leader
		$ccltb_value = "NOT DEFINED YET"; // None

		$selector = new Selector();
		$selector->getValues( [&$rank_, &$lastname, &$firstname, &$middleInitial, &$gender_value],
				  			  ["rank", "lastName", "firstName", "middleInitial", "gender"],
				              "users", [["userID", $userID]], $status );
		

		$rank_lname = $rank_." ".$lastname.","; // DEFINED.
		$rank_fname = $firstname." ".$middleInitial;  // DEFINED.

		return
		(
			
			"<div id=\"personal_info\" class=\"home_div\">".	
				"<p id=\"title\" class=\"title_c\">Personal Information</p>".
				"<table align=\"center\">".
					
					"<tr>"."<td><p id=\"rank_lname\">".$rank_lname."</p></td>"."<td><p id=\"rank_fname\">".$rank_fname."</p></td>"."</tr>".

					"<tr>"."<td><p id=\"dob\">DOB:</p></td>"."<td><p id=\"dob_value\">".$dob_value."</p></td>"."</tr>".
					
					"<tr>"."<td><p id=\"gender\">GENDER:</p></td>"."<td><p id=\"gender_value\">".$gender_value."</p></td>"."</tr>".

					"<tr>"."<td><p id=\"cps\">COMP / PIT / Sqd:</p></td>"."<td><p id=\"cps_value\">".$cps_value."</p></td>"."</tr>".
					
					"<tr>"."<td><p id=\"ccmdb\">Current Command Billet:</p></td>"."<td><p id=\"ccmdb_value\">".$ccmdb_value."</p></td>"."</tr>".

					"<tr>"."<td><p id=\"ccltb\">Current Collateral Billet:</p></td>"."<td><p id=\"ccltb_value\">".$ccltb_value."</p></td>"."</tr>".

				"</table>".
			"</div>"

		);


	}


?>