
function filterTablePackage(typeToFilter, pageOrigin){ 
	//Package Page
	if(pageOrigin === 6){
		if (typeToFilter === 0) {
			input = $("#inputFilterNamePackages").val();
			j = 0;
		}
	}

	filter = input.toUpperCase();

	$(".trP").each(function(){
		td = $(this).find(".tdP")[j];
		if (td) {
			if (td.innerHTML.toUpperCase().indexOf(filter) > -1) { //TODO replace innerHTML with JQuery equivalent
				$(this).show();
			} else {
				$(this).hide();
			}
		}
	});
}

function filterTablePE(typeToFilter, pageOrigin){
	//PackageElement Page
	if(pageOrigin === 7){
		if (typeToFilter === 0) {
			input = $("#inputFilterName").val();
			j = 0;
		} else if (typeToFilter === 1){
			input = $("#inputFilterDescription").val();
			j = 1;
		}
	}

	filter = input.toUpperCase();

	$(".trPE").each(function(){
		td = $(this).find(".tdPE")[j];
		if (td) {
			if (td.innerHTML.toUpperCase().indexOf(filter) > -1) { //TODO replace innerHTML with JQuery equivalent
				$(this).show();
			} else {
				$(this).hide();
			}
		}       
	});
}