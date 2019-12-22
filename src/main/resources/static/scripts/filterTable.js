function filterTable(typeToFilter, pageOrigin){
	var input, filter, table, tr, td, i, j;
	//Client
	if(pageOrigin === 1){
	if (typeToFilter === 0) {
		  input = $("#inputFilterName").val();
		  	j = 0;
	    } else if (typeToFilter === 1){
	    	 input = $("#inputFilterDescription").val();
	    	j = 1;
	    } else if (typeToFilter === 2){
	  	  input = $("#inputFilterEmail").val();
	    	j = 2;
	    } else if (typeToFilter === 3){
		  	  input = $("#inputFilterPhone").val();
		    	j = 3;
		    }
	}
	
	//Exercise
	if(pageOrigin === 2){
 	if (typeToFilter === 0) {
		  input = $("#inputFilterName").val()
	        j = 0;
	    } else if (typeToFilter === 1){
	    	 input = $("#inputFilterDescription").val()
	    	j = 1;
	    } else if (typeToFilter === 2){
	  	  input = $("#inputFilterExerciseGroup").val()
	    	j = 2;
	    }
	}
 	
 	//Exercise Group
	if(pageOrigin === 3){
	  input = $("#inputFilterName").val();
	  j = 0;
	}
	
	//Training
	if(pageOrigin === 4){ 	
	 	if (typeToFilter === 0) {
			  input = $("#inputFilterDate").val();
		        j = 0;
		    } else if (typeToFilter === 1){
		    	 input = $("#inputFilterNumberOfTrainings").val();
		    	j = 1;
		    } else if (typeToFilter === 2){
		  	  input = $("#inputFilterClientName").val();
		    	j = 2;
		    } else if (typeToFilter === 3){
			  input = $("#inputFilterFamilyName").val();
			    j = 3;
		    }
	}

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
	  
	 $("tr").each(function(){
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
