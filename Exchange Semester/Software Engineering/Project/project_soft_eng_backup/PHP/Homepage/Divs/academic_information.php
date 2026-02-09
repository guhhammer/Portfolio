<?php 
	
	
	include_once("../Database/config.php");
	include_once("../Database/selector.php");

	
	function get_academic_information($userID, &$status){
		
		$school_value = "CSUSM"."";  $grad_term_value = "2020 Sp"."";  $major_value = "Computer Science"."";  $t_view_href = ""."";  $t_new_href = ""."";
		$gpa_value = "4.00"."";  $d_view_href = ""."";  $d_new_href = ""."";  $curr_courses_value = "10 Units"."";  $c_edit_href = ""."";

		$subjects = [["CS441", "Software Engin.", "3", "MWF", "1230-1330"], ["CSxxx", "Title", "3", "TuTh", "1230-1330"],
					 ["CSxxx", "Title", "3", "MW", "1230-1330"], ["CSxxx", "Lab", "1", "F", "1000-1330"]];

		$subjects_string = ("<tr><td><p id=\"id_0\">ID</p></td><td><p id=\"title_0\">Title</p></td>".
							"<td><p id=\"units_0\">Units</p></td><td><p id=\"days_0\">Days</p></td>"."<td><p id=\"times_0\">Times</p></td></tr>");
		$counter = 0;
		for($i = 0; $i < sizeof($subjects); $i++){
			$counter = $i + 1;
			$subjects_string = $subjects_string.
							   ("<tr><td><p id=\"id_".$counter."\">".$subjects[$i][0]."</p></td>"."<td><p id=\"title_".$counter."\">".$subjects[$i][1]."</p></td>".
								"<td><p id=\"units_".$counter."\">".$subjects[$i][2]."</p></td>"."<td><p id=\"days_".$counter."\">".$subjects[$i][3]."</p></td>".
								"<td><p id=\"times_".$counter."\">".$subjects[$i][4]."</p></td></tr>");
		}
		
		/*
		getValues( [&$school_value, &$grad_term_value, &$major_value, &$gpa_value, &$curr_courses_value],
				   ["school", "gradTerm", "major", "gpa", "currCourses"],
				    "some-table", 
				   [["id", $id]],
				    $divs["academic"]
				 );*/

		return
		(

			"<div id=\"academic_info\" class=\"home_div\">".
				"<p id=\"title\" class=\"title_c\">Academic Information</p>".
				"<table align=\"center\" id=\"table_1\">".
					"<tr>".
						"<td><p id=\"school\">School:</p></td>"."<td><p id=\"school_value\">".$school_value."</p></td>".
						"<td><p id=\"grad_term\">Grad Term:</p></td>"."<td><p id=\"grad_term_value\">".$grad_term_value."</p></td>".
						"<td><p id=\"\"></p></td>".
					"</tr>".
					"<tr>".
						"<td><p id=\"major\">Major:</p></td>"."<td><p id=\"major_value\">".$major_value."</p></td>"."<td><p id=\"trancript\">Transcript:</p></td>".
						"<td><p id=\"t_view\" style=\"color: blue;\"><a href=\"".$t_view_href."\">(view)</a></p></td>".
						"<td><p id=\"t_new\" style=\"color: blue;\"><a href=\"".$t_new_href."\">(new)</a></p></td>".
					"</tr>".
					"<tr>".
						"<td><p id=\"gpa\">Cumulative GPA:</p></td>"."<td><p id=\"gpa_value\">".$gpa_value."</p></td>".
						"<td><p id=\"degree_plan\">Degree Plan:</p></td>"."<td><p id=\"d_view\"><a href=\"".$d_view_href."\">(view)</a></p></td>".
						"<td><p id=\"d_new\"><a href=\"".$d_new_href."\">(new)</a></p></td>".
					"</tr>".
					"<tr>".
						"<td><p id=\"curr_courses\" style=\"width: 20%;\">Current Courses:</p></td>".
						"<td><p id=\"curr_courses_value\" style=\"width: 20%;\">".$curr_courses_value."</p></td>".
						"<td><p id=\"c_edit\" style=\"width: 60%; color: blue;\"><a href=\"".$c_edit_href."\">(edit courses)</a></p></td>".
					"</tr>".
				"</table>".
				"<table align=\"center\" id=\"table_2\">".$subjects_string."</table>".
			"</div>"

		);


	}


?>