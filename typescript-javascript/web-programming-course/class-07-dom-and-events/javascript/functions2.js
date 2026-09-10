


$(document).ready(function(){

	$("#salvar").click(function(){
		 /////4/90

		if($("#name").val() == ""){
			$("#name").addClass("RED");
		}
		else{
			$("#name").removeClass("RED");	
		}

		if($("#lastName").val() == ""){
			$("#lastName").addClass("RED");
		}
		else{
			$("#lastName").removeClass("RED");	
		}
		

		if($("#email").val() == ""){
			$("#email").addClass("RED");
		}
		else{
			$("#email").removeClass("RED");	
		}

		if($("#studentId").val() == ""){
			$("#studentId").addClass("RED");
		}
		else{
			$("#studentId").removeClass("RED");	
		}


		if($("#user").val() == ""){
			$("#user").addClass("RED");
		}
		else{
			$("#user").removeClass("RED");	
		}
	
		if($("#password").val() == ""){
			$("#password").addClass("RED");
		}
		else{
			$("#password").removeClass("RED");
		}
	
		if($("#confirmPassword").val() == ""){
			$("#confirmPassword").addClass("RED");
		}
		else{
			$("#confimarpassword").removeClass("RED");
		}

		if($("#confirmPassword").val() == $("#password").val()){
		
			$("#password").removeClass("RED");
			$("#confimarpassword").removeClass("RED");
		
		}
		else{

			$("#password").addClass("RED");
			$("#confimarpassword").addClass("RED");
			alert("Passwords diferentes!!!")

		}



	});



});