



$(document).ready(function(){

    $("#save").click(function(){

        var flag = false;
        $("#divEX2 input").each(function(){

            if($("#divEX2 input").val() == ""){
                flag = true;
            }

        });

        if(flag == true){
            alert("All fields must be filled in.");
        }
        else{
            alert("All data was saved successfully.");
        }
        
    });


    $("#clear").click(function(){

        $("#divEX2 input").each(function(){

            $("#divEX2 input").val("");
            
        });
    });


});