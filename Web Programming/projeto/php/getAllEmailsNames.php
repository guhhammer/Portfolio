


<?php 

	
	$path = $_POST["caminho"];

	$diretorio = dir($path);

	$counter = 0;
	while($arq = $diretorio->read()){	

		if($arq != "info"){
			if( $arq != "." && $arq != ".."){
				$emailsJSON["$counter"] = $arq;
				$counter += 1; 
			}
		}
	}

	echo json_encode($emailsJSON);


?>