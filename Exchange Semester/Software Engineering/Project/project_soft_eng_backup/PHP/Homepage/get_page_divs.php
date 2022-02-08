<?php 
	

	$userID = $_POST['id'];

	$divs['confirm'] = "not ok";  $divs['all'] = "";  $divs["personal"] = "";  $divs["contact"] = "";  $divs["user"] = "";
	$divs["command"] = "";  $divs["photo"] = "";  $divs["service"] = "";  $divs["academic"] = "";  


	// ===================================================================================================================================================


	include("Divs/personal_information.php");
	include("Divs/contact_information.php");
	include("Divs/user_information.php");
	include("Divs/command_information.php");
	include("Divs/my_photo.php");
	include("Divs/service_information.php");
	include("Divs/academic_information.php");


	// ===================================================================================================================================================	


	$divs["all"] = (get_personal_information($userID, $divs["personal"])."\n".
					get_contact_information($userID, $divs["contact"])."\n".
					get_user_information($userID, $divs["user"])."\n".
					get_command_information($userID, $divs["command"])."\n".
					get_photo_information($userID, $divs["photo"])."\n".
					get_service_information($userID, $divs["service"])."\n".
					get_academic_information($userID, $divs["academic"]));
		

	// ===================================================================================================================================================


	$divs['confirm'] = "ok";

	echo json_encode($divs);


?>