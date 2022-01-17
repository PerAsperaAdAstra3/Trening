var COLOR_GREEN = 'rgb(192, 248, 185)';
	
var COLOR_YELLOW = 'rgb(239, 244, 138)';

var COLOR_GREEN_HEX = '#c0f8b9';

var COLOR_YELLOW_HEX = '#eff48a';

var COLOR_RED_HEX = '#ff8080';

var COLOR_GRAY_HEX = '#bfc0bf';

function openPDFFolder(){
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/openPDFFolder",
		data: JSON.stringify(),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
			
		},
		error: function (e) {
			alert('Desila se greska prilikom !')
		}
	})
}

function openLogFileLocation(){
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/openLogFile",
		data: JSON.stringify(),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
			
		},
		error: function (e) {
			alert('Desila se greska prilikom !')
		}
	})
}

function trainerTrainingsReportPrint(name, startDate, endDate, listOfTrainings, numberOfTrainings){
	var trainerTrainingReportDTO = {}
	
	clientTrainingReportDTO["highlightedTrainingId"] = name;
	clientTrainingReportDTO["startDate"] = startDate;
	clientTrainingReportDTO["endDate"] = endDate;
	clientTrainingReportDTO["trainingPrice"] = listOfTrainings;
	clientTrainingReportDTO["trainingPrice"] = numberOfTrainings;

	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/trainerTrainingsReportPrint",
		data: JSON.stringify(TrainerTrainingReportDataDTO),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
			$("#modalVaitMessage").hide();
		},
		error: function (e) {
			alert('Desila se greska prilikom !')
		}
	})
}

function trainerTrainingsReport(intervalStartDate, intervalEndDate, highlightedTrainingId, trainingPrice){
	var clientTrainingReportDTO = {}
	
	clientTrainingReportDTO["highlightedTrainingId"] = highlightedTrainingId;
	clientTrainingReportDTO["startDate"] = intervalStartDate;
	clientTrainingReportDTO["endDate"] = intervalEndDate;
	clientTrainingReportDTO["trainingPrice"] = trainingPrice;

	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/trainerTrainingsReport",
		data: JSON.stringify(clientTrainingReportDTO),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
			$("#modalVaitMessage").hide();
			$(".modal-title").text(data.successMessage);
			$("#modalVaitMessage").show();
			setTimeout(function() { $("#modalVaitMessage").show(); $("#modalVaitMessage").hide(); }, 8000);
			if(data.numberOfTrainings != "0"){
				trainerTrainingsReportPrint(data.name, data.startDate, data.endDate, data.listOfTrainings, data.numberOfTrainings)
			}
		},
		error: function (e) {
			alert('Desila se greska prilikom !')
		}
	})
}

function clientTrainingsReportPrint(name, startDate, endDate, trainingPrice, listOfTrainings, numberOfTrainings, oneTrainingPrice, numberOfBonusTrainings){
	var clientTrainingReportDataDTO = {}
	console.log(listOfTrainings);
	console.log(name);
	console.log(startDate);
	clientTrainingReportDataDTO["name"] = name;
	clientTrainingReportDataDTO["startDate"] = startDate;
	clientTrainingReportDataDTO["endDate"] = endDate;
	clientTrainingReportDataDTO["trainingPrice"] = trainingPrice;
	clientTrainingReportDataDTO["listOfTrainings"] = listOfTrainings;
	clientTrainingReportDataDTO["numberOfTrainings"] = numberOfTrainings;
	clientTrainingReportDataDTO["oneTrainingPrice"] = oneTrainingPrice;
	clientTrainingReportDataDTO["numberOfBonusTrainings"] = numberOfBonusTrainings;
	
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/clientTrainingsReportPrint",
		data: JSON.stringify(clientTrainingReportDataDTO),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
			$("#modalVaitMessage").hide();
		},
		error: function (e) {
			alert('Desila se greska prilikom štampanja PDF-a!')
		}
	})
}

function clientTrainingsReport(intervalStartDate, intervalEndDate, highlightedClientID, trainingPrice, bonusTraining){
	var clientTrainingReportDTO = {}
	
	clientTrainingReportDTO["highlightedClientId"] = highlightedClientID;
	clientTrainingReportDTO["startDate"] = intervalStartDate;
	clientTrainingReportDTO["endDate"] = intervalEndDate;
	clientTrainingReportDTO["trainingPrice"] = trainingPrice;
	clientTrainingReportDTO["bonusTraining"] = bonusTraining;
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/clientTrainingsReport",
		data: JSON.stringify(clientTrainingReportDTO),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
			$("#modalVaitMessage").hide();
			$(".modal-title").text(data.successMessage);
			$("#modalVaitMessage").show();
			setTimeout(function() { $("#modalVaitMessage").show(); $("#modalVaitMessage").hide(); }, 8000);
			if(data.numberOfTrainings != "0"){
				clientTrainingsReportPrint(data.name, data.startDate, data.endDate, data.trainingPrice, data.listOfTrainings, data.numberOfTrainings, data.oneTrainingPrice, data.numberOfBonusTrainings)
			}
		},
		error: function (e) {
			alert('Desila se greska prilikom štampanja PDF-a!')
		}
	})
}

function deletePackageElement(packageId){
		var packageElementDTO = {}
		
		packageElementDTO["packageElementID"] = packageId;
		
		$.ajax({
			type: "POST",
			contentType: "application/json",
			url:	"/deletePackageElementRest",
			data: JSON.stringify(packageElementDTO),
			dataType: 'json',
			cache: false,
			timeout: 600000,
			success: function (data){

			},
			error: function (e) {
				alert('Desila se greska prilikom brisanja elementa paketa!')
			}
		})
	}


function addPackageElement(name, description, isProtected){

		var packageDTO = {}
		
		packageDTO["packageElementName"] = name;
		packageDTO["description"] = description;
		packageDTO["isProtected"] = isProtected;
		
		$.ajax({
			type: "POST",
			contentType: "application/json",
			url:	"/addNewPackageElement",
			data: JSON.stringify(packageDTO),
			dataType: 'json',
			cache: false,
			timeout: 600000,
			success: function (data){
				$(".trPE").removeClass("highlighted");
				$('#packageElementBody').append('<tr id="tr-entity-listPE" class="trPE highlighted">'+
						'<td class="packageElementName tdPE">'+ data["elementName"] +'</td>'+
						'<td class="packageElementDescription tdPE">'+ data["elementDescriptions"] +'</td>'+
						'<td scope="row" class="packageElementId" style="display:none;">'+ data["elementID"] +'</td>'+
						'<td scope="row" class="packageElementPackageId" style="display:none;">'+ data["packageElementAJAX"]["packageId"] +'</td>'+
						'<td><button type="button" class="btn btn-danger deletePackageElement">Briši</button></td>'+
						'<td><button id="modal_button" type="button" class="btn btn-success" data-toggle="modal" data-target="#packagePageModal">Dodaj u paket</button></td></tr>');
			},
			error: function (e) {
				alert('Desila se greska prilikom rešavanja problema zaboravljene lozinke!')
			}
		})
	}

function forgotEmail(emailAddress){
//send password with email
	var operatorDTO = {}
	operatorDTO["email"] = emailAddress;
	
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/sendPasswordToEmail",
		data: JSON.stringify(operatorDTO),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
			
		},
		error: function (e) {
			alert('Desila se greska prilikom rešavanja problema zaboravljene lozinke!')
		}
	})
}

function changeClientPackageStatus(row){
// Change status
	var clientPackageDTO = {}
	clientPackageDTO["id"] = $(row).find(".clientPackageId").html();

	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/changeClientPackageStatus",
		data: JSON.stringify(clientPackageDTO),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
			
			var state = $(row).find(".packageStatus").html();
			var payed = $(row).find(".payedTable").prop("checked");
			if(state == 'Aktivan'){	
				if(payed){
					$(row).css('background-color', COLOR_GREEN_HEX);
				} else {
					$(row).css('background-color', COLOR_YELLOW_HEX);
				}
			} else {
				if(payed){
					$(row).css('background-color', COLOR_GRAY_HEX);
				} else {
					$(row).css('background-color', COLOR_RED_HEX);
				}
			}

			payedAndStatusCheck()
		
		},
		error: function (e) {
			alert('Desila se greska prilikom menjanja statusa klijentovog paketa!')
		}
	})
}

function deleteClientPackage(row){
// Delete clients package
	var clientPackageDTO = {}
	clientPackageDTO["id"] = $(row).find(".clientPackageId").html();

	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/deleteClientPackage",
		data: JSON.stringify(clientPackageDTO),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
			$(row).remove()
			
			$("#tr-entity-list .clientPackageElementId").each(function(){
				if($(row).find(".clientPackageId").html() == $(this).parent().find(".clientPackageElementClientPackageId").html()){
					$(this).parent().hide();
				}
			});
		},
		error: function (e) {
			alert('Desila se greska prilikom brisanja  klijentovog paketa!')
		}
	})
}

function useClientPackageElement(row){
//Use up a package from client.
	var clientPackageElementDTO = {}
	clientPackageElementDTO["id"] = $(row).find(".clientPackageElementId").html();
	
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/useUpAPackageElement",
		data: JSON.stringify(clientPackageElementDTO),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
			$("#tr-entity-list .clientPackageElementId").each(function(){
				if($(this).html() == data.clientPackageElementId){
						$(this).parent().find(".clientPackageElementActiveLeft").html(data.activeLeft);
						$(this).parent().find(".clientPackageElementState").html(data.clientPackageElementState);
						if($(this).parent().find(".clientPackageElementActiveLeft").html() == 0){
							$(this).parent().find(".useUpAPackageElement").attr("disabled", false);
						}
					}
				});
			
			$(".tr-entity-listAllClientPackages .clientPackageId").each(function(){
				if($(this).html() == data.clientPackageId){
						$(this).parent().find(".packageStatus").html(data.clientPackageActive);
						var state = data.clientPackageActive;
						var payed = data.clientPackagePayed;

						if(state == 'Aktivan'){	
							if(payed){
								$(this).parent().css('background-color', COLOR_GREEN_HEX);
							} else {
								$(this).parent().css('background-color', COLOR_YELLOW_HEX);
							}
						} else {
							if(payed){
								$(this).parent().css('background-color', COLOR_GRAY_HEX);
							} else {
								$(this).parent().css('background-color', COLOR_RED_HEX);
							}
						}
					}
				});
			
			payedAndStatusCheck()
			
		},
		error: function (e) {
			alert('Desila se greska prilikom brisanja vežbe u krugu!')
		}
	})
}

function ajaxAddPackageToClient(packageId, packagePrice, packageName){

//Add package to client
	var clientPackageDTO = {}
	clientPackageDTO["clientId"] = $("#clientId").val();
	clientPackageDTO["packageId"] = packageId;
	clientPackageDTO["payed"] = $(".payed").prop("checked");
	clientPackageDTO["priceOfClientPackage"] = packagePrice;
	clientPackageDTO["nameOfPackage"] = packageName;
	
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/addPackageToClient",
		data: JSON.stringify(clientPackageDTO),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
	
			var checkedVar = true;
			
			if(data["clientPackageJSON"]["payed"] == "true"){
				checkedVarChar = "checked";
			} else {
				checkedVarChar = "";
			}
			var state = data["clientPackageJSON"]["clientPackageActive"]
			var payed = data["clientPackageJSON"]["payed"];
			if(payed == "true"){
				colorVar = '"background-color: '+ COLOR_GREEN +';"'
			} else {
				colorVar = '"background-color: '+ COLOR_YELLOW +';"'
			}
			
			var rowCountClientPackage = $('#clientPackageBody tr').length;

			if(rowCountClientPackage == 0){
				$('#clientPackageBody').append('<tr class="tr-entity-listAllClientPackages" style=' + colorVar + '><td style="width:15%;" class="packageName">'+ data["clientPackageJSON"]["nameOfPackage"] +
						'</td><td style="width:15%;" class="packageStatus">'+ data["clientPackageJSON"]["clientPackageActive"] +
						'</td><td><input type="checkbox" name="payedTable" class="payedTable" '+ checkedVarChar +'/></td><td style="width:15%;" class="packagePrice">'+ data["clientPackageJSON"]["priceOfClientPackage"] +'</td><td style="width:15%;" class="packagePurchaseDate">'+ data["clientPackageJSON"]["purchaseDate"] +
						'</td><td scope="row" class="clientPackageId" style="display:none;">'+ data["clientPackageJSON"]["id"] +'</td><td><button type="button" class="btn btn-danger deleteClientPackage">Briši</button></td></tr>');
			} else {
				$('#clientPackageBody tr:nth-child(1)').before('<tr class="tr-entity-listAllClientPackages" style=' + colorVar + '><td style="width:15%;" class="packageName">'+ data["clientPackageJSON"]["nameOfPackage"] +
						'</td><td style="width:15%;" class="packageStatus">'+ data["clientPackageJSON"]["clientPackageActive"] +
						'</td><td><input type="checkbox" name="payedTable" class="payedTable" '+ checkedVarChar +'/></td><td style="width:15%;" class="packagePrice">'+ data["clientPackageJSON"]["priceOfClientPackage"] +'</td><td style="width:15%;" class="packagePurchaseDate">'+ data["clientPackageJSON"]["purchaseDate"] +
						'</td><td scope="row" class="clientPackageId" style="display:none;">'+ data["clientPackageJSON"]["id"] +'</td><td><button type="button" class="btn btn-danger deleteClientPackage">Briši</button></td></tr>');
			}
					
			var rowCountClientPackageElements = $('#clientPackageElementBody tr').length;
			
			for(i = 0; i <= data["clientPackageElementsJSON"].length - 1; i++){
				//clientPackageElementBody
				if(rowCountClientPackageElements == 0){
					$('#clientPackageElementBody').append('<tr id="tr-entity-list" class="clientPackageElementsTR"><td scope="row" class="clientPackageElementName">'+data["clientPackageElementsJSON"][i]["name"] +
							'</td><td scope="row" class="clientPackageElementDescription">'+data["clientPackageElementsJSON"][i]["description"] +'</td><td scope="row" class="clientPackageElementCount">'+data["clientPackageElementsJSON"][i]["count"] +
							'</td><td scope="row" class="clientPackageElementActiveLeft">'+data["clientPackageElementsJSON"][i]["activeLeft"] +'</td><td scope="row" class="clientPackageElementState">'+data["clientPackageElementsJSON"][i]["clientPackageElementStatus"] +
							'</td><td scope="row" class="clientPackageElementId" style="display:none;">'+data["clientPackageElementsJSON"][i]["id"] +'</td><td scope="row" class="clientPackageElementClientPackageId" style="display:none;">'+data["clientPackageElementsJSON"][i]["clientPackageId"] +
							'</td><td><button type="button" class="btn btn-default useUpAPackageElement">Potroši</button></td></tr>');
				} else {
					$('#clientPackageElementBody tr:nth-child(1)').before('<tr id="tr-entity-list" class="clientPackageElementsTR"><td scope="row" class="clientPackageElementName">'+data["clientPackageElementsJSON"][i]["name"] +
							'</td><td scope="row" class="clientPackageElementDescription">'+data["clientPackageElementsJSON"][i]["description"] +'</td><td scope="row" class="clientPackageElementCount">'+data["clientPackageElementsJSON"][i]["count"] +
							'</td><td scope="row" class="clientPackageElementActiveLeft">'+data["clientPackageElementsJSON"][i]["activeLeft"] +'</td><td scope="row" class="clientPackageElementState">'+data["clientPackageElementsJSON"][i]["clientPackageElementStatus"] +
							'</td><td scope="row" class="clientPackageElementId" style="display:none;">'+data["clientPackageElementsJSON"][i]["id"] +'</td><td scope="row" class="clientPackageElementClientPackageId" style="display:none;">'+data["clientPackageElementsJSON"][i]["clientPackageId"] +
							'</td><td><button type="button" class="btn btn-default useUpAPackageElement">Potroši</button></td></tr>');
				}
			}
			
			payedAndStatusCheck()
			
		},
		error: function (e) {
			alert('Desila se greska prilikom brisanja vežbe u krugu!')
		}
	})
}

function ajaxDeleteElementsInPackages(elementsInPackagesId, newNumber, thisObject){
// NEW Delete elements in packages
	
	var elementsInPackagesDTOAjax = {}
	elementsInPackagesDTOAjax["id"] = elementsInPackagesId;
	elementsInPackagesDTOAjax["number"] = newNumber;
	
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/deleteElementsInPackages",
		data: JSON.stringify(elementsInPackagesDTOAjax),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
			if(data.zeroLeft){
			  thisObject.parent().parent().remove();
			}
		},
		error: function (e) {
			alert('Desila se greska prilikom brisanja vežbe u krugu!')
		}
	})
}

function packageAddPackageElementNumber(packageElementId){
	
	var packageUnit = {}
	var roundExerciseId = $("#exerciseInRoundExerciseId").val();
	var numberOfElements = $(".numberOfElements").val();
	
	packageUnit["id"] = $("#idPackage").val();
	packageUnit["nameOfPackage"] = $("#nameOfPackage").val();
	packageUnit["price"] = $("#price").val();
	packageUnit["packageElementId"] = packageElementId;
	packageUnit["numnerOfElements"] = numberOfElements;
	
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/addPackagePackageElement",
		data: JSON.stringify(packageUnit),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
			var idTraining = $(".idTraining").val();
			if(data.modOfOperation == "add"){
				$("#elementsInPackagesTable tr:last").after('<tr id="tr-entity-list" class="trow"><td class="packElName">'+ data.packElName +'</td><td class="packElDescription">'+ data.packElDescription +
						'</td><td><input type="number" class="packElNumber" name="quantity" min="1" max="99" value="' + data.elementsInPackagesNumber + '"/></td><td scope="row" class="packElId" style="display:none;">'+ data.packElId  +
						'</td><td scope="row" class="packElpackageId" style="display:none;">'+ data.packElpackageId +'</td><td scope="row" class="elemInPackagesId" style="display:none;">'+ data.elemInPackagesId +'</td><td><button type="button" class="btn btn-danger buttonDelete">Briši</button></td></tr>');
			} else {
				$("#tr-entity-list .elemInPackagesId").each(function(){
					if($(this).html() == data.elemInPackagesId){
							$(this).parent().find(".packElNumber").val(data.elementsInPackagesNumber);
						}
					});
			}
		},
		error: function (e) {
			 var json = "<h4>Ajax Response</h4>";
	            $('#feedback').html(json);
		}
	})
	
}

function packageAddPackageElement(packageElementId){
	
	var packageUnit = {}
	var roundExerciseId = $("#exerciseInRoundExerciseId").val();
	packageUnit["id"] = $("#idPackage").val();
	packageUnit["nameOfPackage"] = $("#nameOfPackage").val();
	packageUnit["packageElementId"] = packageElementId;
	
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/addPackagePackageElement",
		data: JSON.stringify(packageUnit),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){

		    $("#elementsInPackagesTable tr:last").after('<tr id="tr-entity-list"><td class="packElName">'+ data.packElName +'</td><td class="packElDescription">'+ data.packElDescription +
		    		'</td><td scope="row" class="packElId" style="display:none;">'+ data.packElId  +'</td><td scope="row" class="packElpackageId" style="display:none;">'+ data.packElpackageId +
		    		'</td><td scope="row" class="elemInPackagesId" style="display:none;">'+ data.elemInPackagesId +'</td></tr>');
		},
		error: function (e) {
			 var json = "<h4>Ajax Response</h4>";
	            $('#feedback').html(json);
		}
	})
	
}

function ajaxAddMultipleExerciseInRound(attrList , attr, highlightedRoundID){
	var multipleExerciseInRound = {}

	multipleExerciseInRound["exerciseIDList"] = attrList;
	multipleExerciseInRound["trainingId"] = attr;
	multipleExerciseInRound["highlightedRoundId"] = highlightedRoundID;
	multipleExerciseInRound["circularRoundYN"] = $('#circularYN').val();
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/addMultipleExerciseInRound",
		data: JSON.stringify(multipleExerciseInRound),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
			//TODO Transform in to standard AJAX success handling - remove code related to this current solution.
			


			if(data["circularYN"] != "yes"){
				if(data["roundIds"] != null){
				console.log(data["sizeDifference"])
				console.log(data["roundSecNumber"])
				for(i = data["roundIds"].length - data["sizeDifference"]; i <= data["roundIds"].length - 1; i++){
			
				$("#roundsTable tr:last").after('<tr id="round-id-sync" class="highlighted"><td id="roundRoundSequenceNumberId" class="roundRoundSequenceNumber">'+ data["roundSecNumber"][i] +
						'</td><td id="roundIdDel" class="roundId" style="display:none;">'+ data["roundIds"][i] +'</td><td><button id="roundDeleteButton" type="button" class="btn btn-danger">Briši</button></td></tr>');
				selectRoundIdOnRowClick($(".highlighted"));
				}
				}
			}
			var idTraining = $(".idTraining").val();
			if(data["exerciseInRoundJSON"] != null){
				for(i = 0; i <= data["exerciseInRoundJSON"].length - 1; i++){ 
					$("#exercisesInRoundTable tr:last").after('<tr class="hidden_input tr-entity-list" id="tr-entity-list"><td class="exerciseName">'+ data["exerciseInRoundJSON"][i]["exerciseInRoundExerciseName"] +'</td><td class="exerciseInRoundId" style="display:none;">'+ data["exerciseInRoundJSON"][i]["id"] +
			    			'</td><td class="numberOfRepetitions">'+ data["exerciseInRoundJSON"][i]["numberOfRepetitions"]  +'</td><td class="difficulty">'+ data["exerciseInRoundJSON"][i]["difficulty"] +'</td><td class="note">'+ data["exerciseInRoundJSON"][i]["note"] +
			    			'</td><td class="roundId" style="display:none;">'+ data["exerciseInRoundJSON"][i]["roundId"] +'</td><td class="exerciseExecId" style="display:none;">' + data["exerciseInRoundJSON"][i]["exerciseInRoundExerciseId"] + '</td><td><button id="deleteExerciseInRound" type="button" class="btn btn-danger">Briši</button></td></tr>');
					$("#testTable tr:last").after('<tr><td>PreviTestElement</td><td>DrugiTestElement</td></tr>');
				}
			} else {
				$("#exercisesInRoundTable tr:last").after('<tr class="hidden_input tr-entity-list" id="tr-entity-list"><td class="exerciseName">'+ data["exercInRound"]["exerciseInRoundExerciseName"] +'</td><td class="exerciseInRoundId" style="display:none;">'+ data["exercInRound"]["id"] +
		    			'</td><td class="numberOfRepetitions">'+ data["exercInRound"]["numberOfRepetitions"]  +'</td><td class="difficulty">'+ data["exercInRound"]["difficulty"] +'</td><td class="note">'+ data["exercInRound"]["note"] +
		    			'</td><td class="roundId" style="display:none;">'+ data["exercInRound"]["roundId"] +'</td><td class="exerciseExecId" style="display:none;">' + data["exercInRound"]["exerciseInRoundExerciseId"] + '</td><td><button id="deleteExerciseInRound" type="button" class="btn btn-danger">Briši</button></td></tr>');
				$("#testTable tr:last").after('<tr><td>PreviTestElement</td><td>DrugiTestElement</td></tr>');
			}

			$("#round-id-sync").parent().find(".highlighted").removeClass("highlighted");
			$("#round-id-sync").removeClass("highlighted");
			$(".trainingChk").prop('checked', false);
			document.body.style.cursor  = 'default';
		},
		error: function (e) {
			 var json = "<h4>Ajax Response</h4>";
	            $('#feedback').html(json);
		}
	})

}

function ajaxExerciseInRoundAddExercise(){
	var exerciseInRound = {}
	var roundExerciseId = $("#exerciseInRoundExerciseId").val();
	
	exerciseInRound["name"] = $("#name").val();
	exerciseInRound["description"] = $("#description").val();
	exerciseInRound["exerciseGroupId"] = $("#exerciseGroupId").val();
	
	exerciseInRound["exerciseInRoundExerciseName"] = $("#exerciseInRoundExerciseName").val();
	exerciseInRound["exerciseInRoundExerciseId"] = $("#exerciseInRoundExerciseId").val();
	exerciseInRound["idExerciseInRound"] = $("#idExerciseInRound").val();
	exerciseInRound["exerciseInRoundNote"] = $("#exerciseInRoundNote").val();
	exerciseInRound["exerciseInRoundNumberOfRepetitions"] = $("#exerciseInRoundNumberOfRepetitions").val();
	exerciseInRound["exerciseInRoundDifficulty"] = $("#exerciseInRoundDifficulty").val();
	exerciseInRound["roundId"] = $("#hiddenRoundInTraining").val();
	
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/addExerciseInRoundAjaxAddRound",
		data: JSON.stringify(exerciseInRound),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
			
		var idTraining = $(".idTraining").val();
	    $("#exercisesInRoundTable tr:last").after('<tr class="hidden_input" id="tr-entity-list"><td class="exerciseName">'+ data.exerciseInRoundExerciseName +'</td><td class="exerciseInRoundId" style="display:none;">'+ data.exerciseExecId +
	    		'</td><td class="numberOfRepetitions">'+ data.exerciseInRoundNumberOfRepetitions  +'</td><td class="difficulty">'+ data.exerciseInRoundDifficulty +'</td><td class="note">'+ data.exerciseInRoundNote +
	    		'</td><td class="roundId" style="display:none;">'+ data.roundId +'</td><td class="exerciseExecId" style="display:none;">' + data.exerciseInRoundExerciseId + '</td><td><button id="deleteExerciseInRound" type="button" class="btn btn-danger">Briši</button></td></tr>');
       	$("#testTable tr:last").after('<tr><td>PreviTestElement</td><td>DrugiTestElement</td></tr>');
		$("#exerciseInRoundExerciseId").val(data.exerciseInRoundExerciseId);
		},
		error: function (e) {
			 var json = "<h4>Ajax Response</h4>";
	            $('#feedback').html(json);
		}
	})

	
}

function ajaxExerciseInRoundFill(dinamicSelectExerciseId, myDropdownVar){
	
	var exerciseInRound = {}
	
	exerciseInRound["clientId"] = $(".id").val();
	exerciseInRound["exerciseId"] = dinamicSelectExerciseId;
	
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/addExerciseInRoundFillFields",
		data: JSON.stringify(exerciseInRound),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
		    $("#exerciseInRoundNote").val(data.exerciseInRoundNote);
		    $("#exerciseInRoundNumberOfRepetitions").val(data.exerciseInRoundNumberOfRepetitions);
		    $("#exerciseInRoundDifficulty").val(data.exerciseInRoundDifficulty);
		    $(myDropdownVar).toggle("show");
		},
		error: function (e) {
			 var json = "<h4>Ajax Response</h4>";
	            $('#feedback').html(json);
		}
	})
}

function ajaxExerciseInRound(){

	var exerciseInRound = {}
	var roundExerciseId = $("#exerciseInRoundExerciseId").val();
	
	exerciseInRound["exerciseInRoundExerciseName"] = $("#exerciseInRoundExerciseName").val();
	exerciseInRound["exerciseInRoundExerciseId"] = $("#exerciseInRoundExerciseId").val();
	exerciseInRound["idExerciseInRound"] = $("#idExerciseInRound").val();
	exerciseInRound["exerciseInRoundNote"] = $("#exerciseInRoundNote").val();
	exerciseInRound["exerciseInRoundNumberOfRepetitions"] = $("#exerciseInRoundNumberOfRepetitions").val();
	exerciseInRound["exerciseInRoundDifficulty"] = $("#exerciseInRoundDifficulty").val();
	exerciseInRound["roundId"] = $("#hiddenRoundInTraining").val();
	
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/addExerciseInRoundAjax",
		data: JSON.stringify(exerciseInRound),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
			
		var idTraining = $(".idTraining").val();
	    $("#exercisesInRoundTable tr:last").after('<tr class="hidden_input" id="tr-entity-list"><td class="exerciseName">'+ data.exerciseInRoundExerciseName +
	    		'</td><td class="exerciseInRoundId" style="display:none;">'+ data.exerciseExecId +'</td><td class="numberOfRepetitions">'+ data.exerciseInRoundNumberOfRepetitions  +
	    		'</td><td class="difficulty">'+ data.exerciseInRoundDifficulty +'</td><td class="note">'+ data.exerciseInRoundNote +'</td><td class="roundId" style="display:none;">'+ data.roundId +
	    		'</td><td class="exerciseExecId" style="display:none;">' + data.exerciseInRoundExerciseId + '</td><td><button id="deleteExerciseInRound" type="button" class="btn btn-danger">Briši</button></td></tr>');
       	$("#testTable tr:last").after('<tr><td>PreviTestElement</td><td>DrugiTestElement</td></tr>');
       	document.body.style.cursor  = 'default';
		},
		error: function (e) {
			 var json = "<h4>Ajax Response</h4>";
	            $('#feedback').html(json);
		}
	})

}


function ajaxExerciseInRoundChange(){

	var exerciseInRound = {}
	
	var roundExerciseId = $("#exerciseInRoundExerciseId").val();
	
	exerciseInRound["exerciseInRoundExerciseName"] = $("#exerciseInRoundExerciseName").val();
	exerciseInRound["exerciseInRoundExerciseId"] = $("#exerciseInRoundExerciseId").val();
	exerciseInRound["idExerciseInRound"] = $("#idExerciseInRound").val();
	exerciseInRound["exerciseInRoundNote"] = $("#exerciseInRoundNote").val();
	exerciseInRound["exerciseInRoundNumberOfRepetitions"] = $("#exerciseInRoundNumberOfRepetitions").val();
	exerciseInRound["exerciseInRoundDifficulty"] = $("#exerciseInRoundDifficulty").val();
	exerciseInRound["roundId"] = $("#hiddenRoundInTraining").val();
	exerciseInRound["exerciseExecId"] = $("#idExerciseInRound").val();
	
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/changeExerciseInRoundAjax",
		data: JSON.stringify(exerciseInRound),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
			
			var tableRow = $(".exerciseInRoundId").filter(function() {
			    return $(this).text() == data.exerciseExecId;
			}).closest("tr");
			
			var exerciseNameTest = tableRow.find(".exerciseName").html();
			var exerciseInRoundIdTest = tableRow.find(".exerciseInRoundId").html();
			var numberOfRepetitionsTest = tableRow.find(".numberOfRepetitions").html();
			var difficultyTest = tableRow.find(".difficulty").html();
			var noteTest = tableRow.find(".note").html();
			var roundIdTest = tableRow.find(".roundId").html();
			var exerciseExecIdTest = tableRow.find(".exerciseExecId").html();
			var exerciseNameTest = tableRow.find(".exerciseName").html();
			
			if(exerciseNameTest != data.exerciseInRoundExerciseName){
				tableRow.find(".exerciseName").html(data.exerciseInRoundExerciseName);
			}

			if(noteTest != data.exerciseInRoundNote){
				tableRow.find(".note").html(data.exerciseInRoundNote);
			}
			
			if(numberOfRepetitionsTest != data.exerciseInRoundNumberOfRepetitions){
				tableRow.find(".numberOfRepetitions").html(data.exerciseInRoundNumberOfRepetitions);
			}
			
			if(difficultyTest != data.exerciseInRoundDifficulty){
				tableRow.find(".difficulty").html(data.exerciseInRoundDifficulty);
			}
			document.body.style.cursor  = 'default';
		},
		error: function (e) {
			var json = "<h4>Ajax Response</h4>";
	            $('#feedback').html(json);
		}
	})

}

function ajaxAddRound(){

	var round = {}
	round["id"] = $("#id").val();
	
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/addRoundAjax",
		data: JSON.stringify(round),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
			var idTraining = $(".idTraining").val();			
				$("#roundsTable tr").each(function() {
					removeHighlights(this)
				});
		    $("#roundsTable tr:last").after('<tr id="round-id-sync" class="highlighted"><td id="roundRoundSequenceNumberId" class="roundRoundSequenceNumber">'+ data.roundRoundSequenceNumber +
		    		'</td><td id="roundIdDel" class="roundId" style="display:none;">'+ data.selectedRoundId +'</td><td><button id="roundDeleteButton" type="button" class="btn btn-danger">Briši</button></td></tr>');
		    selectRoundIdOnRowClick($(".highlighted"));
		},
		error: function (e) {
			var json = "<h4>Ajax Response</h4>";
	            $('#feedback').html(json);
		}
	})
}

function removeHighlights(row){
	if(!$(row).hasClass("header")){
		$(row).parent().find(".highlighted").removeClass("highlighted");
		sync1($(row));
	}
}

function ajaxDeleteRound(roundId, thisObject){	
	var round = {}
	
	round["id"] = roundId;

	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/deleteRoundAjax",
		data: JSON.stringify(round),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
			thisObject.parent().parent().remove();
			
			$(".tr-entity-list").each(function() {
				if ($(this).find(".roundId").text() == roundId)
				{			
					$(this).remove();
				} else {
				}
			});
		},
		error: function (e) {
			//alert('Desila se greska prilikom brisanja kruga iz Ajax-a!')
		}
	})
}

function ajaxDeleteExerciseInRound(roundId, thisObject){
// AJAX Delete Exercise In Round
	var round = {}
	round["id"] = roundId;
	
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/deleteExerciseInRoundAjax",
		data: JSON.stringify(round),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
			thisObject.parent().parent().remove();
		},
		error: function (e) {
		//	alert('Desila se greska prilikom brisanja vežbe u krugu!')
		}
	})
}

function PDFprintAjax(){
	
	var idTraining = {}
	idTraining["id"] =	$(".idTraining").val();
	
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/printPDFAjax",
		data: JSON.stringify(idTraining),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
			alert('PDF je uspešno odštampan!')
		},
		error: function (e) {
			alert('Desila se greška prilikom štampanja PDF-a - dokument otvoren!')
		}
	})
}

function PDFprintAjaxListPage(trainingId){
	
	var idTraining = {}
	idTraining["id"] =	trainingId;
	
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url:	"/printPDFAjax",
		data: JSON.stringify(idTraining),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data){
			alert('PDF je uspešno odštampan!')
		},
		error: function (e) {
			alert('Desila se greška prilikom štampanja PDF-a - dokument otvoren!')
		}
	})
}

function payedAndStatusCheck(){
	var isItActiveNotPayed = false;
	var isItInactiveNotPayed = false;
	$(".tr-entity-listAllClientPackages").each(function(){
		var state1 = $(this).find('.packageStatus').html();
		var payed1 = $(this).find(".payedTable").prop("checked");
		if(state1 == 'Aktivan'){
			if(!payed1){
				isItActiveNotPayed = true;
			}	
		} else {
			if(!payed1){
				isItInactiveNotPayed = true;
			}
		}
	});
	if(isItActiveNotPayed){
		$(document).find(".activeNotPayed").removeClass("visibleClientPackage");
	} else {
		$(document).find(".activeNotPayed").addClass("visibleClientPackage");
	}

	if(isItInactiveNotPayed){
		$(document).find(".inactiveNotPayed").removeClass("visibleClientPackage");
	} else {
		$(document).find(".inactiveNotPayed").addClass("visibleClientPackage");
	}
}
