function highlightRow(row){
	if(!$(row).hasClass("header")){
		$(row).parent().find(".highlighted").removeClass("highlighted");
    	$(row).addClass("highlighted");
    	sync($(row));
    }
}

function highlightRowPackage(row){
	if(!$(row).hasClass("header")){
		$(row).parent().find(".highlighted").removeClass("highlighted");
    	$(row).addClass("highlighted");
    	syncPackage($(row));
    }
}

function goFirst(){
	item = $("table tr:nth-child(2)");
	$(".highlighted").removeClass("highlighted");
	item.addClass("highlighted");
	sync(item);
}

function goNext(){
	highlighted = $(".highlighted");
	var count = $("tr").length;
	if (count == 0)
		return;
	index =  $("tr").index(highlighted);
	if (index < 0)
		return;
	selectChild = 2;
	if (index < count - 1)
		selectChild = index + 2;
	item = $("tr:nth-child(" + selectChild + ")");
	$(".highlighted").removeClass("highlighted");
	item.addClass("highlighted");
	sync(item);
}
