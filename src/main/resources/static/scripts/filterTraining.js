function filterTable(typeToFilter, pageOrigin){
	var input, filter, table, tr, td, i, j;

	//TrainingCreation
	if(pageOrigin === 5){
		if (typeToFilter === 2) {
			  input = $("#inputFilterByName").val();
		        j = 2;
		    } else if (typeToFilter === 3){
		    	 input = $("#inputFilterByDescription").val();
		    	j = 3;
		    } else if (typeToFilter === 4){
		  	  input = $("#inputFilterByGroup").val();
		    	j = 4;
		    }
	}
	 filter = input.toUpperCase();
	  
	 $(".modal-table-coloring").each(function(){
		    td = $(this).find("td")[j];
		    if (td) {
		      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) { //TODO replace innerHTML with JQuery equivalent
		        $(this).show();
		      } else {
		        $(this).hide();
		      }
		    }       
		 });
}
