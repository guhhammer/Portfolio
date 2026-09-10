



$(document).ready(function(){


	var click1 = false;

	$("#searchBar").focus();

	$("#searchBar").click(function(){

		if(click1){

			$("#searchBar").val("");

			location.reload();

			click1 = false;

		}

	});


	var name = location.pathname.replace("/projeto/html/","").replace(".html","");

	$("#btSearch").click(function(){

		var search = $("#searchBar").val().toString();

		if(name == "favorites" || name == "inbox" || name == "junkMail" || name == "deletedItems"){
		
			$("#messages #msgBox #FROM").each(function(){  $(this).highlight(search, "highlight"); });

		}

		if(name == "drafts" || name == "sentItems" || name == "archive" || name == "deletedItems"){
		
			$("#messages #msgBox #TO").each(function(){  $(this).highlight(search, "highlight"); });

		}

		$("#messages #msgBox #SUBJECT").each(function(){  $(this).highlight(search, "highlight"); });

		$("#messages #msgBox #MESSAGE").each(function(){  $(this).highlight(search, "highlight"); });

		click1 = true;

	});



	jQuery.fn.highlight = function (str, className) {
	    
	    var regex = new RegExp(str, "gi");
	    return this.each(function () {
	        $(this).contents().filter(function() {
	            return this.nodeType == 3 && regex.test(this.nodeValue);
	        }).replaceWith(function() {
	            return (this.nodeValue || "").replace(regex, function(match) {
	                return "<span class=\"" + className + "\">" + match + "</span>";
	            });
	        });
	    });

	};




});