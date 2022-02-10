<?php
	include_once("Database/config.php");
	
	$conn = new mysqli(getDatabaseServerAddress(), getDatabaseUsername(), getDatabasePassword(), getDatabaseName());

	//LIST OF COMPLETED FUNCTIONS AVAILABLE:
	/*	get_term($requested_date) returns the requested termID (i.e. the semester)
		get_user_billet($userID, $term) returns the billetID of the given user
		get_next_Coc($origin_userID,$userID,$term) returns the userID of the next user in the chain of command of the given user
		get_aid($userID,$term) returns a string of the aid (concatendated company platoon squad) of a given user
		get_CoC_up($userID,$term) returns a string to display a table of a given user's complete chain of command
		get_OI($userID) returns the userID of the assigned OI of a given user
		get_user_cmd_billet($userID, $term) returns a string of the title of the command billet held by the given user
		get_user_collateral_billet($userID, $term) returns a string of all collateral billets currently held by the given user
		
		function get_user_rank_name($userID) Retuns a string of the ($userID)'s abbreviated rank last name, first and middle initials (ex: LDCR Last, F.M.)
		function get_user_dob($userID) Returns a date value of the ($userID)'s date of birth
		function get_user_gender($userID) Returns a string of the ($userID)'s gender
		function get_personal_info($userID) Retuns a table displaying the user's personal information.
		function get_current_courses($userID) Retuns a table displaying the ($userID)'s current courses for the current term only
	*/
	//NOT COMPLETE YET:
	/*
		function set_OI($userID, $OI)
		function set_new_term($semester,$begin,$end)
		function assign_billet($term, $billetID,$company,$platoon,$squad,$userID)
		function add_student($user_array)
		function add_staff()
		function update_phone($userID, $new_phone)
		function update_email($userID, $new_email)
		function update_address($userID,$new_street,$new_apt,$new_city,$new_residence)
		function update_muster($userID,$new_muster)
		function update_password($userID,$new_password)
		function update_photo($userID,$new_photo)
		function set_new_event($event_title,$event_date,$event_duration,$event_type,$event_owner,$event_notes)
		function update_event()
		function get_event_roster($eventID)
	*/

	//Status: Works
	//ToDo:
	//Function: 
	function get_term($requested_date) {
		
		global $conn;

		if($conn->connect_error){ die("Connection failed: ".$conn->connect_error); }

		$current_term;
		//$requested_date = strtotime($requested_date);

		$find_term = "SELECT * FROM term";

		$result = $conn->query($find_term);

		if($result->num_rows >0){

			while($row = $result->fetch_assoc()){
				if((strtotime($requested_date) < strtotime($row['enddate'])) && (strtotime($requested_date) > strtotime($row['begindate'])) ) {
					$current_term = $row['termID'];
				}
			}
		}

		if($current_term == NULL){
			//throw some error here
			echo "Contact your adminstrator to add term";
		}
		else{
			return $current_term;
			}
	}

	//Status: Works
	//ToDo:
	//Function: 
	function get_user_billet($userID, $term){

		global $conn;

		$billet;

		$find_billet_stmt = 'SELECT billet 
			FROM billet_term_roster
			WHERE user = '.$userID.'
			    AND term ='.$term;

		$result = $conn->query($find_billet_stmt);
		if($result->num_rows > 0){

			while($row = $result->fetch_assoc()){
				$billet = $row['billet'];
			}
		}
		else{
			//Throw error here.
			echo "Contact your administrator. No billet assigned for the defined term.";
		}

		return $billet;

	}

	//Status: Works
	//ToDo:
	//Function: 
	function get_next_Coc($origin_userID,$userID,$term){

		global $conn;

		$nextCoC;
		$user_billet = get_user_billet($userID,$term);
		$user_aid = get_aid($userID,$term);	
		$oi = get_OI($origin_userID);

		$find_next_CoC_stmt = 'SELECT *
			FROM billet_term_roster
			WHERE term ='.$term.'
			    ORDER BY billet DESC, company ASC, platoon ASC, squad ASC';

		$result = $conn->query($find_next_CoC_stmt);
		$match_col;
		$match_val;

		while($row = $result->fetch_assoc() ) {
			if($result->num_rows > 0 ){
				if($user_billet > 231){ //match aid and next billet not equal
					$match_col = $row['company'].$row['platoon'].$row['squad'];
					$match_val = $user_aid;
				}
				elseif($user_billet > 221){ //match comp and plt and next billet not equal
					$match_col = $row['company'].$row['platoon'];
					$split = str_split($user_aid,1);
					$match_val = $split[0].$split[1];
				}
				elseif($user_billet > 211){ //match comp and next billet not equal
					$match_col = $row['company'];
					$split = str_split($user_aid,1);
					$match_val = $split[0];
				}
				elseif($user_billet > 201){ //match next billet not equal
					$match_col = "";
					$match_val = "";
				}
				elseif($user_billet == 112){ //match $oi to $row['user']
					$match_col = $row['user'];
					$match_val = $oi;
				}
				elseif($user_billet > 112){ //match next lower
					$match_col = "";
					$match_val = "";
				}
				elseif($user_billet == 101){//done
					return null;
				}
				
				if( ($user_billet > $row['billet']) && ($match_col == $match_val) ) {
					$nextCoC = $row['user'];
					return $nextCoC; 
				}
			}
		
			else{
				//Throw error here.
				echo "Contact your administrator. Error when searching for next member in the Chain of Command.";
			}
		}
	
	}//END OF get_next_CoC	

	//Status: Works
	//ToDo:
	//Function: 
	function get_aid($userID,$term){
		
		global $conn;

		$findAIDstmt = 'SELECT DISTINCT company, platoon, squad 
				FROM billet_term_roster
				WHERE user = '.$userID.'
				    AND term ='.$term;
		
		$result = $conn->query($findAIDstmt);
		
		$company;
		$platoon;
		$squad;
		
		if($result->num_rows >0){

			while($row = $result->fetch_assoc()){
			$company = $row['company'];
			$platoon = $row['platoon'];
			$squad = $row['squad'];
			}
		}
		else{
			//Throw error here.
		}

		$aid = $company.$platoon.$squad;

		return $aid;
	}


	// Gustavo's extended information necessity call function for personal information div.
	function extended_personal_info($userID){
		$term = get_term(date("Y-m-d"));
		return [get_aid($userID, $term),  get_user_cmd_billet($userID, $term),  get_user_collateral_billet($userID, $term)];
	} 


	//Status: Works
	//ToDo:
	//Function: 
	function get_CoC_up($userID,$term){
		
		global $conn;

		$OI = get_OI($userID);
		$aid = get_aid($userID,$term);
		$aidarray = str_split($aid,1);

		$find_CoC_stmt = 'SELECT billet, company, squad, abbrevRank, lastName, firstName, userID
			FROM billet_term_roster, users, ranktbl
			WHERE term ='.$term.'
				AND user = userID
				AND user_rank = rankID
				ORDER BY billet';
	
		$result = $conn->query($find_CoC_stmt);
		$output = "<table><tr> <th> Lvl </th> <th> Billet </th> <th> Name </th> </tr>";
		$lvl = 1;

		if($result->num_rows >0){

			while($row = $result->fetch_assoc()){
				if($row['firstName']!=''){ 
					$array2 = str_split($row['firstName'],1); 
				}
				else {
					$array2[0] = '';
				}
				$caten_name = $row['abbrevRank']." ".$row['lastName'].", ".$array2[0];

				if($row['billet'] == 101){ 
					$output = $output."<tr> <td> ".$lvl." </td> <td> NROTC CO </td> <td> ".$caten_name.".</td></tr>"; $lvl++;
				}
				
				elseif($row['billet']==102){
					$output = $output."<tr> <td> ".$lvl." </td> <td> NROTC XO </td> <td> ".$caten_name.".</td></tr>"; $lvl++;	
				}
				elseif($row['billet']==103){
					$output = $output."<tr> <td> ".$lvl." </td> <td> NROTC CMDCS </td> <td> ".$caten_name.".</td></tr>"; $lvl++;	
				}
				elseif(($row['billet']==111)&&($row['userID']==$OI)){
					$output = $output."<tr> <td> ".$lvl." </td> <td> OI </td> <td> ".$caten_name.".</td></tr>"; $lvl++;		
				}
				elseif($row['billet']==112){
					$output = $output."<tr> <td> ".$lvl." </td> <td> AMOI </td> <td> ".$caten_name.".</td></tr>"; $lvl++;		
				}
				elseif($row['billet']==113){
					$output = $output."<tr> <td> ".$lvl." </td> <td> AOI </td> <td> ".$caten_name.".</td></tr>"; $lvl++;	
				}
				elseif($row['billet']==201){
					$output = $output."<tr> <td> ".$lvl." </td> <td> BN CO </td> <td> ".$caten_name.".</td></tr>"; $lvl++;		
				}
				elseif($row['billet']==202){
					$output = $output."<tr> <td> ".$lvl." </td> <td> BN XO </td> <td> ".$caten_name.".</td></tr>"; $lvl++;	
				}
				elseif(($row['billet']==211)&&($aidarray[0]==$row['company'])){
					$output = $output."<tr> <td> ".$lvl." </td> <td> ".$aidarray[0]." CO CDR </td> <td> ".$caten_name.".</td></tr>"; $lvl++;	
				}
				elseif(($row['billet']==212)&&($aidarray[0]==$row['company'])){
					$output = $output."<tr> <td> ".$lvl." </td> <td> ".$aidarray[0]." CO XO </td> <td> ".$caten_name.".</td></tr>"; $lvl++;	
				}
				elseif(($row['billet']==221)&&($aidarray[0]==$row['company'])&&($aidarray[1]==$row['platoon'])){
					$output = $output."<tr> <td> ".$lvl." </td> <td> ".$aidarray[0].$aidarray[1]." PLT CDR </td> <td> ".$caten_name.".</td></tr>"; $lvl++;		
				}
				elseif(($row['billet']==221)&&($aidarray[0]==$row['company'])&&($aidarray[1]==$row['platoon'])){
					$output = $output."<tr> <td> ".$lvl." </td> <td> ".$aidarray[0].$aidarray[1]." PLT Sgt </td> <td> ".$caten_name.".</td></tr>"; $lvl++;	
				}
				elseif(($row['billet']==221)&&($aidarray[0]==$row['company'])&&($aidarray[1]==$row['platoon'])&&($aidarray[2]==$row['squad'])){
					$output = $output."<tr> <td> ".$lvl." </td> <td> ".$aidarray[0].$aidarray[1].$aidarray[2]." Sqd Ldr </td> <td> ".$caten_name.".</td></tr>"; $lvl++;	
				}
				elseif($row['userID']==$userID){
					$output = $output."<tr> <td> ".$lvl." </td> <td> Member </td> <td> ".$caten_name.".</td></tr>"; $lvl++;	
					return $output;
				}
				else {}
			}//end of while
		}//end of if
		else{
			//Throw error here.
			echo "Error: Contact your administrator!";
		}
		//$output = $output."</table>";
		return $output;
	}


	// Gustavo's extended information necessity call function for command information div.
	function extended_command_info($userID){

		global $conn;

		$term = get_term(date("Y-m-d"));

		$OI = get_OI($userID);	$aid = get_aid($userID,$term);  $aidarray = str_split($aid,1);
		
		$find_CoC_stmt = 'SELECT billet, company, squad, abbrevRank, lastName, firstName, userID
			FROM billet_term_roster, users, ranktbl
			WHERE term ='.$term.'
				AND user = userID
				AND user_rank = rankID
				ORDER BY billet';
	
		$result = $conn->query($find_CoC_stmt);   $output = [];

		if($result->num_rows >0){

			while($row = $result->fetch_assoc()){
				
				if($row['firstName']!=''){  $array2 = str_split($row['firstName'],1);  }  else { $array2[0] = ''; }
				$caten_name = $row['abbrevRank']." ".$row['lastName'].", ".$array2[0];

				if($row['billet'] == 101){ array_push($output, ["NROTC CO", $caten_name]); }
				
				elseif($row['billet']==102){ array_push($output, ["NROTC XO", $caten_name]); }

				elseif($row['billet']==103){ array_push($output, ["NROTC CMDCS", $caten_name]); }

				elseif(($row['billet']==111)&&($row['userID']==$OI)){ array_push($output, ["OI", $caten_name]); }

				elseif($row['billet']==112){ array_push($output, ["AMOI", $caten_name]); }

				elseif($row['billet']==113){ array_push($output, ["AOI", $caten_name]); }

				elseif($row['billet']==201){ array_push($output, ["BN CO", $caten_name]); }

				elseif($row['billet']==202){ array_push($output, ["BN XO", $caten_name]); }

				elseif(($row['billet']==211)&&($aidarray[0]==$row['company'])){ array_push($output, [$aidarray[0]." CO CDR", $caten_name]); }
				
				elseif(($row['billet']==212)&&($aidarray[0]==$row['company'])){ array_push($output, [$aidarray[0]." CO XO", $caten_name]); }
				
				elseif(($row['billet']==221)&&($aidarray[0]==$row['company'])&&($aidarray[1]==$row['platoon'])){
					array_push($output, [$aidarray[0].$aidarray[1]." PLT CDR", $caten_name]); 
				}

				elseif(($row['billet']==221)&&($aidarray[0]==$row['company'])&&($aidarray[1]==$row['platoon'])){ 
					array_push($output, [$aidarray[0].$aidarray[1]." PLT Sgt", $caten_name]);
				}

				elseif(($row['billet']==221)&&($aidarray[0]==$row['company'])&&($aidarray[1]==$row['platoon'])&&($aidarray[2]==$row['squad'])){ 
					array_push($output, [$aidarray[0].$aidarray[1].$aidarray[2]." Sqd Ldr", $caten_name]);
				}
				
				elseif($row['userID']==$userID){
					array_push($output, ["Member", $caten_name]);  return $output;
				}

				else {}

			}//end of while

		}//end of if
		
		else{
			//Throw error here.
			echo "Error: Contact your administrator!";
		}

		return $output;

	}


	//Status: Works
	//ToDo:
	//Function: 
	function get_OI($userID){

		global $conn;

		$current_term = get_term(date("Y-m-d"));
		$OI;
		
		$find_OI_stmt = 'SELECT user, oi.username as OI_Name, oi.userID as OI, stud.userID as student
			FROM billet_term_roster, users as oi, users as stud
			WHERE term = '.$current_term.'
				AND stud.assigned_OI = rosterID
				AND user = oi.userID
				AND stud.userID = '.$userID;
	
		$result = $conn->query($find_OI_stmt);

		if($result->num_rows >0){

			while($row = $result->fetch_assoc()){
				$OI = $row['OI'];
				return $OI;
			}
		}
		
		else{
			//Throw error here.
			echo"Contact your adminstrator to add OI";
		}
	}

	//Status: Works
	//ToDo:
	//Function: 
	function get_user_cmd_billet($userID, $term){
		
		global $conn;

		$billetID = get_user_billet($userID,$term);

		$find_cmd_billet_stmt = 'SELECT billetName 
			FROM billet_term_roster, billets
			WHERE user = '.$userID.'
			    AND term ='.$term.'
			    AND billet = billetID
			    AND command = 1';

		$result = $conn->query($find_cmd_billet_stmt);
		if($result->num_rows > 0){

			while($row = $result->fetch_assoc()){
				$billet_title = $row['billetName'];
			}
		}
		else{
			$billet_title = "None";
		}
		return $billet_title;
	}

	//Status: Works
	//ToDo:
	//Function: 
	function get_user_collateral_billet($userID, $term){
		
		global $conn;
		
		$billetID = get_user_billet($userID,$term);

		$stmt = 'SELECT billetName 
			FROM billet_term_roster, billets
			WHERE user = '.$userID.'
			    AND term ='.$term.'
			    AND billet = billetID
			    AND command = 0';

		$result = $conn->query($stmt);
		$num_rows = $result->num_rows;

		if($num_rows > 0){
			while($row = $result->fetch_assoc()){
				$billet_title = $billet_title.$row['billetName'];
				if($num_rows > 1){$billet_title = $billet_title.', '; $num_rows--;}

			}
		}
		else{
			$billet_title = "None";
		}
		return $billet_title;
	}

	//Status: Works
	//ToDo:
	//Function: Retuns a string of the ($userID)'s abbreviated rank last name, first and middle initials (ex: LDCR Last, F.M.)
	function get_user_rank_name($userID){
	
		global $conn;
	
		$output;

		$stmt = 'SELECT abbrevRank, lastName, firstName, middleInitial
					FROM users, ranktbl
					WHERE userID = '.$userID.'
					    AND  user_rank = rankID';

		$result = $conn->query($stmt);

		if($result->num_rows > 0){
			
			while($row = $result->fetch_assoc()) {
				
				$split = str_split($row['firstName'], 1);

				$output = $row['abbrevRank']." ".$row['lastName'].", ".$split[0].".";
				
				if($row['middleInitial'] != ""){
					$output = $output.$row['middleInitial'].".";
				}
			}
		}

		return $output;
	}

	//Status: Works
	//ToDo:
	//Function: Returns a date value of the ($userID)'s date of birth
	function get_user_dob($userID){
		
		global $conn;

		$output;

		$stmt = 'SELECT dob FROM users WHERE userID = '.$userID;

		$result = $conn->query($stmt);

		if($result->num_rows > 0){
			
			while($row = $result->fetch_assoc()) {
				$output = $row['dob'];
			}
		}

		return $output;
	}

	//Status: Works
	//ToDo:
	//Function: Returns a string of the ($userID)'s gender
	function get_user_gender($userID){
		
		global $conn;

		$output;

		$stmt = 'SELECT sex.gender as sex FROM users as lookup, gender as sex WHERE userID = '.$userID.' AND genderID = lookup.gender';

		$result = $conn->query($stmt);

		if($result->num_rows > 0){
			
			while($row = $result->fetch_assoc()) {
				$output = $row['sex'];
			}
		}

		return $output;
	}

	//Status: Works
	//ToDo: None
	//Function: Retuns a table displaying the user's personal information.
	function get_personal_info($userID){
		$term = get_term(date("Y-m-d"));
		$output = "<table>
				<tr><td> Name: ".get_user_rank_name($userID)."</td></tr>
				<tr><td> DOB: ".get_user_dob($userID)."</td></tr>
				<tr><td> Gender: ".get_user_gender($userID)."</td></tr>
				<tr><td> Company/Platoon/Squad: ".get_aid($userID,$term)."</td></tr>
				<tr><td> Command Billet: ".get_user_cmd_billet($userID,$term)."</td></tr>
				<tr><td> Collateral Billets: ".get_user_collateral_billet($userID,$term)."</td></tr>
			</table>";
		return $output;
	}

	//Status: Works
	//ToDo: None
	//Function: Retuns a table displaying the ($userID)'s current courses for the current term only
	function get_current_courses($userID){

		global $conn;

		$term = get_term(date("Y-m-d"));
		$output = "<table><tr>
				<td><th>CID</th></td>
				<td><th>Course Title</th></td>
				<td><th>Units</th></td>
				<td><th>Days</th></td>
				<td><th>Times</th> </td>
			</tr></table>";

		$stmt = 'SELECT courseID, courseTitle, units, mon, tues, wed, thurs, fri, sat, sun, timebegin, timeend
				FROM academics, course_entries 
				WHERE user = '.$userID.' 
					AND current_term ='.$term;
						
		$result = $conn->query($stmt);
		$num_courses = $result->num_rows;
		$total_units = 0;
		
		if($result->num_rows > 0){
			while($row = $result->fetch_assoc()) {
				$total_units = $total_units+$row['units'];
				$days = ""; 
				if($row['mon'] == 1) {$days = $days.'M';}
				if($row['tues'] == 1) {$days = $days.'Tu';}
				if($row['wed'] == 1) {$days = $days.'W';}
				if($row['thurs'] == 1) {$days = $days.'Th';}
				if($row['fri'] == 1) {$days = $days.'F';}
				if($row['sat'] == 1) {$days = $days.'Sa';}
				if($row['sun'] == 1) {$days = $days.'Su';}
				$output = $output."<tr>
					<td>".$row['courseID']."</td>
					<td>".$row['courseTitle']."</td>
					<td>".$row['units']."</td>
					<td>".$days."</td>
					<td>".$row['timebegin']."-".$row['timeend']."</td>
					</tr>";
			}
			$output = $output."</table>";
		}
		return $output;
	}




	// Gustavo's extended information necessity call function for academic information div.
	function extended_academic_info($userID){

		global $conn;  $term = get_term(date("Y-m-d")); $output = [];

		$stmt = 'SELECT courseID, courseTitle, units, mon, tues, wed, thurs, fri, sat, sun, timebegin, timeend
				FROM academics, course_entries 
				WHERE user = '.$userID.' 
					AND current_term ='.$term;
						
		$result = $conn->query($stmt);

		if($result->num_rows > 0){
			while($row = $result->fetch_assoc()) {
				$days = ""; 
				if($row['mon'] == 1) {$days = $days.'M';}
				if($row['tues'] == 1) {$days = $days.'Tu';}
				if($row['wed'] == 1) {$days = $days.'W';}
				if($row['thurs'] == 1) {$days = $days.'Th';}
				if($row['fri'] == 1) {$days = $days.'F';}
				if($row['sat'] == 1) {$days = $days.'Sa';}
				if($row['sun'] == 1) {$days = $days.'Su';}
				
				array_push($output, [$row['courseID'], $row['courseTitle'], $row['units'], $days, $row['timebegin']."-".$row['timeend']]);

			}
		}

		return $output;

	}



	function insert_course($userID,$term,$cid,$tile,$units,$days,$times){
		
	}


	///TEST AREA
		//echo get_current_courses(1);   // PS: Don't leave echo's on the commit: they interfere with the page display. Just add "//" to them.
		//echo "Term is: ".get_term(date("Y-m-d"));
		//echo "<br>Collateral Billet is: ".get_user_collateral_billet(1,get_term(date("Y-m-d")));
		//echo get_OI(18);
		//echo get_personal_info(2);


	/*
		function set_OI($userID, $OI){
			//get current term
			//find rosterID of OI for that term on billet_term_roster tbl
			//update user assigned_OI in users tbl
		}

		function set_new_term($semester,$begin,$end){
			//get calendar year from begin date
			//get academic year from semester and calendar year
		}

		function assign_billet($term, $billetID,$company,$platoon,$squad,$userID){
			//check if user already has a billet for the term and update if so
			//check if only one billetholder for that billet is held (if necessary)
			//insert into db
			//confirm success/failure

		}

		function add_student($user_array){
			//Indices: 0-username, 1-firstName, 2-middleInitial, 3-lastName, 4-dob, 5-gender, 6-email, 7-residence, 8-street, 9-apt,10-city,11-state,12-zip,13-muster,14-phone,15-rank,16-joinDate,17-school,18-program,19-status,20-serviceOption,21-contract,22-OI
			$column_array = ['username','firstName','middleInitial','lastName','dob','gender','email','residence','street','apt','city','state','zip','muster','phone','rank','joinDate','school','program','status','service_option','contract','assigned_OI'];

			$column_array[0] = $user_array[1]."."$user_array[3];
			//Count other users with same name
			$count_stmt = "SELECT COUNT(*) FROM users HAVING username = "

			$insert_stmt = "INSERT INTO users (";
			$values_stmt = ") VALUES (";
			$comma_bool = false;

			for($i = 0; $i < 22, $i++){
				if($user_array[$i] != null){
					
					if( ($comma_bool)) {
						$insert_stmt = $insert_stmt.", ".$column_array[$i];
						$values_stmt = $values_stmt.", ".$user_array[$i];
					}
					elseif($user_array[$i] != null){
						$insert_stmt = $insert_stmt.$column_array[$i];
						$values_stmt = $values_stmt.$user_array[$i];
					}

					$comma_bool = true; 
				}
			}	
			$insert_stmt = $insert_stmt.$values_stmt;

			//Check connection
			if($conn->connect_error){ die("Connection failed: ".$conn->connect_error); }
			
			//User validation
			if($conn->query($stmt) == TRUE){ echo "New user add sucessful."; }
			else{ echo "Error updating muster location: ". $conn->error; }

		}
		function add_staff(){}

		function update_phone($userID, $new_phone){
			//Check connection
			if($conn->connect_error){ die("Connection failed: ".$conn->connect_error); }
			//Update Sql statement
			$stmt = 'UPDATE users SET phone = '.$new_phone.' WHERE users.userID ='.$userID;
			//User validation
			if($conn->query($stmt) == TRUE){ echo "Phone number update sucessful."; }
			else{ echo "Error updating phone number: ". $conn->error; }
		}

		function update_email($userID, $new_email){
			//Check connection
			if($conn->connect_error){ die("Connection failed: ".$conn->connect_error); }
			//Update Sql statement
			$stmt = 'UPDATE users SET email = '.$new_email.' WHERE users.userID ='.$userID;
			//User validation
			if($conn->query($stmt) == TRUE){ echo "Email update sucessful."; }
			else{ echo "Error updating email: ". $conn->error; }
		}

		function update_address($userID,$new_street,$new_apt,$new_city,$new_residence){
			//Check connection
			if($conn->connect_error){ die("Connection failed: ".$conn->connect_error); }

			$setstmt = [];
			$setupdate = "";
			$count = 0;
			if($new_street != null) {$setstmt[$count] = "street = ".$new_street; $count++;}
			if($new_apt != null) {$setstmt[$count] = "apt = ".$new_apt; $count++;}
			if($new_city != null) {$setstmt[$count] = "city = ".$new_city; $count++;}
			if($new_residence != null) {$setstmt[$count] = "residence = ".$new_residence; }
			
			for($i = $count-1; $i > -1; $i--){
				$setupdate = $setupdate.$setstmt[$i].", ";
			}
			$setupdate = $setupdate.$setstmt[$count];

			//Update Sql statement
			$updatestmt = 'UPDATE users SET '.$setupdate.' WHERE users.userID ='.$userID;

			//User validation
			if($conn->query($stmt) == TRUE){ echo "Address update sucessful."; }
			else{ echo "Error updating address: ". $conn->error; }
		}

		function update_muster($userID,$new_muster){
			//Check connection
			if($conn->connect_error){ die("Connection failed: ".$conn->connect_error); }
			//Update Sql statement
			$stmt = 'UPDATE users SET muster = '.$new_muster.' WHERE users.userID ='.$userID;
			//User validation
			if($conn->query($stmt) == TRUE){ echo "Muster location update sucessful."; }
			else{ echo "Error updating muster location: ". $conn->error; }
		}

		function update_password($userID,$new_password){
			//Check connection
			if($conn->connect_error){ die("Connection failed: ".$conn->connect_error); }
			//Update Sql statement
			$stmt = 'UPDATE users SET password = '.$new_password.' WHERE users.userID ='.$userID;
			//User validation
			if($conn->query($stmt) == TRUE){ echo "Password update sucessful."; }
			else{ echo "Error updating password: ". $conn->error; }
		}

		function update_photo($userID,$new_photo){
			//Check connection
			if($conn->connect_error){ die("Connection failed: ".$conn->connect_error); }
			//Update Sql statement
			$stmt = 'UPDATE users SET photo = '.$new_photo.' WHERE users.userID ='.$userID;
			//User validation
			if($conn->query($stmt) == TRUE){ echo "Photo update sucessful."; }
			else{ echo "Error updating photo: ". $conn->error; }
		}

		function set_new_event($event_title,$event_date,$event_duration,$event_type,$event_owner,$event_notes){
			$term = get_term($event_date);

			$stmt = 'INSERT INTO events (
				eventID, eventTitle,eventDate,eventDuration,term,eventType,eventOwner,eventNotes) 
			VALUES (
				NULL, '.$event_title.', '.$event_date.', 
				'.$event_duration.', '.$term.', '.$event_type.',
				 '.$event_owner.', '.$event_notes.')';

			if($conn->query($stmt) == TRUE){
				echo "Event successfully submitted";
			}
			else{
				echo "Error: ". $stmt . "<br>". $conn->error;
			}
		}

		function update_event(){}
		

		function get_event_roster($eventID){}
	*/

		
?>
