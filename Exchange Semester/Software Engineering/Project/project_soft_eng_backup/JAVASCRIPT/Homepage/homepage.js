

$(document).ready(function(){

	// Pop-up model 1.
	$('body').append(
		"<div id=\"\" class=\"pop_up_overlay\">"+
        	"<div class=\"popup_content\">"+
        		"<input type=\"\" name=\"\" id=\"first_input_id\" placeholder=\"\" class=\"first_input\">"+
				"<input type=\"\" name=\"\" id=\"second_input_id\" placeholder=\"\" class=\"second_input\">"+
				"<button id=\"closer\" class=\"close first_button\">Close</button>"+
				"<button id=\"saver\" class=\"save second_button\">Save</button>"+
        	"</div>"+
      	"</div>"
	);

	// Pop-up model 2.
	$("body").append(
		"<div id=\"model2\" class=\"pop_up_overlay_pp2\">"+
        	"<div class=\"popup_content_pp2\">"+
        		"<p id=\"o_c_p_id\">Off-campus: </p>"+"<select id=\"o_c_sel_id\" class=\"select_oc\">"+
        											"<option value=\"no\">no</option>"+
        											"<option value=\"yes\">yes</option>"+
        										   "</select>"+
        		"<input type=\"\" name=\"\" id=\"street_id\" placeholder=\"\" class=\"first_pp2\">"+
				"<input type=\"\" name=\"\" id=\"apt_id\" placeholder=\"\" class=\"second_pp2\">"+
				"<input type=\"\" name=\"\" id=\"city_id\" placeholder=\"\" class=\"third_pp2\">"+
				"<input type=\"\" name=\"\" id=\"state_id\" placeholder=\"\" class=\"forth_pp2\">"+
				"<input type=\"\" name=\"\" id=\"zip_id\" placeholder=\"\" class=\"fifth_pp2\">"+
				"<button id=\"closer_pp2\" class=\"close_pp2 first_button_pp2\">Close</button>"+
				"<button id=\"saver_pp2\" class=\"save_pp2 second_button_pp2\">Save</button>"+
        	"</div>"+
      	"</div>"
	);


	// Cookies extraction:
	var cookies = document.cookie.toString().split(";"), username = "", password = "", id = "";
	for(var i = 0; i < cookies.length; i++){
		if(cookies[i].split("=")[0].trim() == "username"){ username = cookies[i].split("=")[1].trim();}
		if(cookies[i].split("=")[0].trim() == "password"){ password = cookies[i].split("=")[1].trim();}
		if(cookies[i].split("=")[0].trim() == "id"){ id = cookies[i].split("=")[1].trim();}
	}


	// Gets the divs of the homepage.
	$.ajax({

		type: "POST",
		dataType: "json",
		url: "../../PHP/Homepage/get_page_divs.php",
		async: false,
		
		data: {
			'id': id
		},

		success: function(divs){
			$('body').append(divs['all']);
		}

	});

	var first_input_placeholder = "", second_input_placeholder = "";

	$(document).click(function(e){

		if(e.target.closest(".pop_up_overlay") != null 
			|| e.target.closest(".pop_up_overlay_pp2") != null){
			return;
		}

		if(e.target == document.getElementById("password_click")
		|| e.target == document.getElementById("contact_click_phone")
		|| e.target == document.getElementById("contact_click_email")
		|| e.target == document.getElementById("contact_click_mpp")
		|| e.target == document.getElementById("contact_click_ph_res")){
			return;
		}

	  	$(".pop_up_overlay, .popup_content").removeClass("active");
	  	$(".pop_up_overlay_pp2, .popup_content_pp2").removeClass("active");

		if($(".second_input").hasClass("no_match")){ $(".second_input").removeClass("no_match"); }
		
		if($("body").hasClass("unscrollable")){ $("body").removeClass("unscrollable"); }
		
		if($(".pop_up_overlay").hasClass("position1")){$(".pop_up_overlay").removeClass("position1"); }
		if($(".pop_up_overlay").hasClass("position2")){$(".pop_up_overlay").removeClass("position2"); }
		
		if($("table, p").hasClass("blurred")){ $("table, p").removeClass("blurred"); }

	});


	$(document).click(function(e){

		if(e.target != document.getElementById("first_input_id") && $("#first_input_id").val() === ""){
			$("#first_input_id").val(first_input_placeholder);
		}
		
		if($("#first_input_id").hasClass("focused") && e.target != document.getElementById("first_input_id")){ 
			$("#first_input_id").removeClass("focused");
		}

		if(e.target != document.getElementById("second_input_id") && $("#second_input_id").val() === ""){
			$("#second_input_id").val(second_input_placeholder);
		}
		
		if($("#second_input_id").hasClass("focused") && e.target != document.getElementById("second_input_id")){
			$("#second_input_id").removeClass("focused"); 
		}
	
	});



	$("#first_input_id").click(function(){
		$("#first_input_id").addClass("focused");
	});

	$("#second_input_id").click(function(){
		$("#second_input_id").addClass("focused");
	});


	$("#password_click").click(function() {
		
		first_input_placeholder = "New Password"; 
		second_input_placeholder = "Confirm Password";
		
		$("#first_input_id").val("");
	  	$("#second_input_id").val("");
		
		if($(".pop_up_overlay_pp2").hasClass("active")){ $(".pop_up_overlay_pp2, .popup_content_pp2").removeClass("active"); }
		$(".pop_up_overlay").addClass("position1");
	  	$(".pop_up_overlay, .popup_content").addClass("active");
	  	$("body").addClass("unscrollable");
	  	var $target = $('html,body');
		$target.animate({scrollTop: 0}, 400);
		$("table, p").addClass("blurred");

	});

	$("#contact_click_phone").click(function(){

		first_input_placeholder = "New Phone"; 
		second_input_placeholder = "Confirm Phone";
		
		$("#first_input_id").val("");
	  	$("#second_input_id").val("");


	  	if($(".pop_up_overlay_pp2").hasClass("active")){ $(".pop_up_overlay_pp2, .popup_content_pp2").removeClass("active"); }
	  	$(".pop_up_overlay").addClass("position2");
		$(".pop_up_overlay, .popup_content").addClass("active");
		$("body").addClass("unscrollable");
		var $target = $('html,body');
		$target.animate({scrollTop: $target.height()/2.35}, 200);
		$("table, p").addClass("blurred");
	
	});



	$("#contact_click_email").click(function(){

		first_input_placeholder = "New Email"; 
		second_input_placeholder = "Confirm Email";
		
		$("#first_input_id").val("");
	  	$("#second_input_id").val("");


	  	if($(".pop_up_overlay_pp2").hasClass("active")){ $(".pop_up_overlay_pp2, .popup_content_pp2").removeClass("active"); }
	  	$(".pop_up_overlay").addClass("position2");
		$(".pop_up_overlay, .popup_content").addClass("active");
		$("body").addClass("unscrollable");
		var $target = $('html,body');
		$target.animate({scrollTop: $target.height()/2.35}, 200);
		$("table, p").addClass("blurred");
	
	});


	$("#contact_click_mpp").click(function(){

		first_input_placeholder = "New MPP"; 
		second_input_placeholder = "Confirm MPP";
		
		$("#first_input_id").val("");
	  	$("#second_input_id").val("");


	  	if($(".pop_up_overlay_pp2").hasClass("active")){ $(".pop_up_overlay_pp2, .popup_content_pp2").removeClass("active"); }
	  	$(".pop_up_overlay").addClass("position2");
		$(".pop_up_overlay, .popup_content").addClass("active");
		$("body").addClass("unscrollable");
		var $target = $('html,body');
		$target.animate({scrollTop: $target.height()/2.35}, 200);
		$("table, p").addClass("blurred");

	});

	


	$(".close, .popup_overlay").click(function() {
	  	$(".pop_up_overlay, .popup_content").removeClass("active");
		if($(".second_input").hasClass("no_match")){ $(".second_input").removeClass("no_match"); }
		if($("body").hasClass("unscrollable")){ $("body").removeClass("unscrollable"); }
		if($(".pop_up_overlay").hasClass("position1")){$(".pop_up_overlay").removeClass("position1"); }
		if($(".pop_up_overlay").hasClass("position2")){$(".pop_up_overlay").removeClass("position2"); }
		if($("table, p").hasClass("blurred")){ $("table, p").removeClass("blurred"); }
	});

	$("#first_input_id").click(function(){

		if($("#first_input_id").val() == first_input_placeholder){

			$("#first_input_id").val("");
		
		}

	});

	$("#second_input_id").click(function(){

		if($("#second_input_id").val() == second_input_placeholder){

			$("#second_input_id").val("");

		}

	});


	$(".second_button, .popup_overlay").click(function(){

		if($(".second_input").hasClass("no_match")){ $(".second_input").removeClass("no_match"); }

		// save php function to be defined.
		if($(".first_input").val() == $(".second_input").val() && $(".first_input").val().length > 0){

			
			$(".pop_up_overlay, .popup_content").removeClass("active");
			if($("body").hasClass("unscrollable")){ $("body").removeClass("unscrollable"); }
			if($(".pop_up_overlay").hasClass("position1")){$(".pop_up_overlay").removeClass("position1"); }
			if($(".pop_up_overlay").hasClass("position2")){$(".pop_up_overlay").removeClass("position2"); }
			if($("table, p").hasClass("blurred")){ $("table, p").removeClass("blurred"); }

		}

		if($(".first_input").val() != $(".second_input").val()){
			$(".second_input").addClass("no_match");
		}


	});	


	// ================================================================================================================================

	var first_pp2 = "", second_pp2 = "", third_pp2 = "", forth_pp2 = "", fifth_pp2 = "";


	$(document).click(function(e){

		if(e.target != document.getElementById("street_id") && $(".first_pp2").val() === ""){ $(".first_pp2").val(first_pp2); }
		if($(".first_pp2").hasClass("focused") && e.target != document.getElementById("street_id")){  $(".first_pp2").removeClass("focused"); }

		if(e.target != document.getElementById("apt_id") && $(".second_pp2").val() === ""){ $(".second_pp2").val(second_pp2); }
		if($(".second_pp2").hasClass("focused") && e.target != document.getElementById("apt_id")){  $(".second_pp2").removeClass("focused"); }

		if(e.target != document.getElementById("city_id") && $(".third_pp2").val() === ""){ $(".third_pp2").val(third_pp2); }
		if($(".third_pp2").hasClass("focused") && e.target != document.getElementById("city_id")){  $(".third_pp2").removeClass("focused"); }

		if(e.target != document.getElementById("state_id") && $(".forth_pp2").val() === ""){ $(".forth_pp2").val(forth_pp2); }
		if($(".forth_pp2").hasClass("focused") && e.target != document.getElementById("state_id")){  $(".forth_pp2").removeClass("focused"); }

		if(e.target != document.getElementById("zip_id") && $(".fifth_pp2").val() === ""){ $(".fifth_pp2").val(fifth_pp2); }
		if($(".fifth_pp2").hasClass("focused") && e.target != document.getElementById("zip_id")){  $(".fifth_pp2").removeClass("focused"); }
	
	});

	$(".first_pp2").click(function(){ $(".first_pp2").addClass("focused"); });
	$(".second_pp2").click(function(){ $(".second_pp2").addClass("focused"); });
	$(".third_pp2").click(function(){ $(".third_pp2").addClass("focused"); });
	$(".forth_pp2").click(function(){ $(".forth_pp2").addClass("focused"); });
	$(".fifth_pp2").click(function(){ $(".fifth_pp2").addClass("focused"); });
	

	$(document).click(function(){

		if($("#o_c_sel_id option:selected").text().toString() == "no"){
			$("#model2 input").prop('disabled', true);
		}
		else{
			$("#model2 input").prop('disabled', false);
		}

	});

	
	$("#contact_click_ph_res").click(function(){

		first_pp2 = "New Street"; second_pp2 = "New Apart. #"; third_pp2 = "New City";
		forth_pp2 = "New State"; fifth_pp2 = "new Zip"; 

		$(".first_pp2").val(""); $(".second_pp2").val("");
		$(".third_pp2").val("");  $(".forth_pp2").val("");  $(".fifth_pp2").val("");
	  		
		if($(".pop_up_overlay").hasClass("active")){ $(".pop_up_overlay, .popup_content").removeClass("active"); }
		if($(".pop_up_overlay").hasClass("position1")){$(".pop_up_overlay").removeClass("position1"); }
		if($(".pop_up_overlay").hasClass("position2")){$(".pop_up_overlay").removeClass("position2"); }

	  	$(".pop_up_overlay_pp2, .popup_content_pp2").addClass("active");
		$("body").addClass("unscrollable");

		var $target = $('html,body');
		$target.animate({scrollTop: $target.height()/2.35}, 200);
		
		$("table, p").addClass("blurred");
		$("#o_c_p_id").removeClass("blurred")
		
	});
	


	$(".close_pp2, .popup_overlay_pp2").click(function() {
	  	$(".pop_up_overlay_pp2, .popup_content_pp2").removeClass("active");
		if($("body").hasClass("unscrollable")){ $("body").removeClass("unscrollable"); }
		if($("table, p").hasClass("blurred")){ $("table, p").removeClass("blurred"); }
	});



	$(".first_pp2").click(function(){ if($(".first_pp2").val() == first_pp2){ $(".first_pp2").val(""); } });
	$(".second_pp2").click(function(){ if($(".second_pp2").val() == second_pp2){ $(".second_pp2").val(""); } });
	$(".third_pp2").click(function(){ if($(".third_pp2").val() == third_pp2){ $(".third_pp2").val(""); } });
	$(".forth_pp2").click(function(){ if($(".forth_pp2").val() == forth_pp2){ $(".forth_pp2").val(""); } });
	$(".fifth_pp2").click(function(){ if($(".fifth_pp2").val() == fifth_pp2){ $(".fifth_pp2").val(""); } });



	$(".second_button_pp2, .popup_overlay_pp2").click(function(){


		// save php function to be defined.
		if(true){

			
			$(".pop_up_overlay_pp2, .popup_content_pp2").removeClass("active");
			if($("body").hasClass("unscrollable")){ $("body").removeClass("unscrollable"); }
			if($("table, p").hasClass("blurred")){ $("table, p").removeClass("blurred"); }

		}


	});	





})

