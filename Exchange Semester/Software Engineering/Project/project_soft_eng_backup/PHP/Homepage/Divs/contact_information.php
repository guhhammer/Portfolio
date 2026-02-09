<?php
 
	include_once("../Database/config.php");
	include_once("../Database/selector.php");

	function get_contact_information($userID, &$status){

		$phone_value = "(987) 654-3210"."";	
		$email_value = "username@domain.com"."";  
		$physical_residence_value = "Off-Campus"."";  
				
				$street_dorm_value = "123 Main St"."";  $apt_bld_value = "None"."";
				$city_value = "San Diego"."";  $state_value = "CA"."";  $zip_value = "92131"."";

		$mpp_value = "North".""; 

		/* TO BE DONE
		getValues( [&$phone_value, &$email_value, &$physical_residence_value, &$street_dorm_value, &$apt_bld_value, 
			                                                &$city_value, &$state_value, &$zip_value, &$mpp_value],
				   ["phone", "email", "physical_residence", "street", "apt", "city", "state", "zip", "musterPointPreference"],
				    "some-table", [["userID", $userID]], $status
				 );
		*/

		return
		(

			"<div id=\"contact_info\" class=\"home_div\">".
				"<p id=\"title\" class=\"title_c\">Contact Information</p>".
	            "<table align=\"center\">".	
					"<tr>".
						"<td><p id=\"phone\">Phone:</p></td>"."<td><p id=\"phone_value\">".$phone_value."</p></td>".
						"<td><button id=\"contact_click_phone\" class=\"pop_up_click_initial_button\">(edit)</button></td>".
					"</tr>".
				
					"<tr>".
						"<td><p id=\"email\">Email:</p></td>"."<td><p id=\"email_value\">".$email_value."</p></td>".
						"<td><button id=\"contact_click_email\" class=\"pop_up_click_initial_button\">(edit)</button></td>".
					"</tr>".

					"<tr>".
						"<td><p id=\"physical_residence\">Physical Residence:</p></td>"."<td><p id=\"physical_residence_value\">".$physical_residence_value."</p></td>".	
						"<td><button id=\"contact_click_ph_res\" class=\"pop_up_click_initial_button\">(edit)</button></td>".
					"</tr>".
						
					((strtolower($physical_residence_value) == "off-campus")  ?
						(

						"<tr id=\"off_campus_1\">"."<td><p id=\"street_dorm\">Street/Dorm #:</p></td>"."<td><p id=\"street_dorm_value\">".$street_dorm_value."</p></td>"."<td><p></p></td>"."</tr>".
			
						"<tr id=\"off_campus_2\">"."<td><p id=\"apt_bld\">Apt/Building:</p></td>"."<td><p id=\"apt_bld_value\">".$apt_bld_value."</p></td>"."<td><p></p></td>"."</tr>".
			
						"<tr id=\"off_campus_3\">"."<td><p id=\"city\">City:</p></td>"."<td><p id=\"city_value\">".$city_value."</p></td>"."<td><p></p></td>"."</tr>".
			
						"<tr id=\"off_campus_4\">"."<td><p id=\"state\">State:</p></td>"."<td><p id=\"state_value\">".$state_value."</p></td>"."<td><p></p></td>"."</tr>".
			
						"<tr id=\"off_campus_5\">"."<td><p id=\"zip\">Zip:</p></td>"."<td><p id=\"zip_value\">".$zip_value."</p></td>"."<td><p></p></td>"."</tr>"
			
						)
							: ""
					).

					"<tr>".
						"<td><p id=\"mpp\">Muster Point Preference:</p></td>"."<td><p id=\"mpp_value\">".$mpp_value."</p></td>".
						"<td><button id=\"contact_click_mpp\" class=\"pop_up_click_initial_button\">(edit)</button></td>".
					"</tr>".
				"</table>".
			"</div>"

		);


	}


?>