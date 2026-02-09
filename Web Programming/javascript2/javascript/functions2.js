


$(document).ready(function(){

	$("#salvar").click(function(){
		 /////4/90

		if($("#nome").val() == ""){
			$("#nome").addClass("RED");
		}
		else{
			$("#nome").removeClass("RED");	
		}

		if($("#sobrenome").val() == ""){
			$("#sobrenome").addClass("RED");
		}
		else{
			$("#sobrenome").removeClass("RED");	
		}
		

		if($("#email").val() == ""){
			$("#email").addClass("RED");
		}
		else{
			$("#email").removeClass("RED");	
		}

		if($("#matricula").val() == ""){
			$("#matricula").addClass("RED");
		}
		else{
			$("#matricula").removeClass("RED");	
		}


		if($("#usuario").val() == ""){
			$("#usuario").addClass("RED");
		}
		else{
			$("#usuario").removeClass("RED");	
		}
	
		if($("#senha").val() == ""){
			$("#senha").addClass("RED");
		}
		else{
			$("#senha").removeClass("RED");
		}
	
		if($("#confirmarsenha").val() == ""){
			$("#confirmarsenha").addClass("RED");
		}
		else{
			$("#confimarsenha").removeClass("RED");
		}

		if($("#confirmarsenha").val() == $("#senha").val()){
		
			$("#senha").removeClass("RED");
			$("#confimarsenha").removeClass("RED");
		
		}
		else{

			$("#senha").addClass("RED");
			$("#confimarsenha").addClass("RED");
			alert("Senhas diferentes!!!")

		}



	});



});