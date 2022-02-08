<?php 
	

	include_once("../Database/config.php");
	include_once("../Database/selector.php");
	
	function get_photo_information($userID, &$status){


		$change_photo_href = "";

		$data = "";
		$path = "../../IMAGES/Default_User_Image"; 
		$name = "user.PNG";

		/*
		getValues( [&$data, &$name],
				   ["data", "name"],
				    "documents_files", 
				   [["uploadedby", $userID]],
				    $divs["personal"]
				 );
		
		$path = "../../IMAGES/Users/user_".$userID;
		mkdir($path);

		file_put_contents($path."/".$name, base64_decode($data));
		*/
		return 
		(

			"<div id=\"my_photo\" class=\"home_div\">".
				"<table align=\"center\">".
					"<tr>"."<td colspan=\"4\" style=\"width: 100%;\">"."<img src=\"".$path."/".$name."\" id=\"photo\" style=\"width: 350px; height: 350px;\">"."</td>"."</tr>".
					"<tr>".
						"<td style=\"width: 25%;\"><p style=\"color: blue;\" id=\"link\"><a href=\"".$change_photo_href."\">(edit)</a></p></td>".
						"<td style=\"width: 25%;\"><p></p></td>"."<td style=\"width: 25%;\"><p></p></td>"."<td style=\"width: 25%;\"><p></p></td>".
					"</tr>".
				"</table>".
			"</div>"

		);


	}


?>