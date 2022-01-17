function filterTableTrainingClients(typeToFilter, pageOrigin){
	var input, filter, table, span, tr, td, i, j, counterV;

	//TrainingCreation
	if(pageOrigin === 9){
		if (typeToFilter === 0) {
			  input = $("#inputFilterNameClients").val();
		        j = 2;
		    }
	}
	 filter = input.toUpperCase();

	 $("tr").each(function(){

		    span = $(this).find(".spanClass").text();

		    	if (span.toUpperCase().indexOf(filter) > -1) { //TODO replace innerHTML with JQuery equivalent
		        $(this).show();
		      } else {
		        $(this).hide();
		      }

		 });
}