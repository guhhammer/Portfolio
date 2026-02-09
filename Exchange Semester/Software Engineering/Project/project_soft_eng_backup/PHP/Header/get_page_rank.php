<?php 


	$userID = $_POST["id"];

	$rank_lname = "Rank LName";

	include_once("../Database/config.php");
	include_once("../Database/selector.php");

	$rank_ = "Rank"; 
	$lastname = "LName";
	
	$selector = new Selector();
	$selector->getValues( [&$rank_, &$lastname],
					   	  ["rank", "lastName"],
					       "users", [["userID", $userID]], $status
					     );
	

	$rank = $rank_."   ".$lastname; 


	echo json_encode($rank);


?>