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
					$(row).css('background-color', '#c0f8b9');
				} else {
					$(row).css('background-color', '#eff48a');
				}
			} else {
				if(payed){
					$(row).css('background-color', '#bfc0bf');
				} else {
					$(row).css('background-color', '#ff8080');
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
								$(this).parent().css('background-color', '#c0f8b9');
							} else {
								$(this).parent().css('background-color', '#eff48a');
							}
						} else {
							if(payed){
								$(this).parent().css('background-color', '#bfc0bf');
							} else {
								$(this).parent().css('background-color', '#ff8080');
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

function ajaxAddPackageToClient(packageId, packagePrice){
	import { COLOR_GREEN, COLOR_YELLOW } from 'constants';
//Add package to client
	var clientPackageDTO = {}
	clientPackageDTO["clientId"] = $("#clientId").val();
	clientPackageDTO["packageId"] = packageId;
	clientPackageDTO["payed"] = $(".payed").prop("checked");
	clientPackageDTO["priceOfClientPackage"] = packagePrice;
	
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
				colorVar = '"background-color: rgb(239, 244, 138);"'
			}
			
			var rowCountClientPackage = $('#clientPackageBody tr').length;

			if(rowCountClientPackage == 0){
				$('#clientPackageBody').append('<tr class="tr-entity-listAllClientPackages" style=' + colorVar + '><td style="width:15%;" class="packageName">'+ data["clientPackageJSON"]["nameOfPackage"] +
						'</td><td style="width:15%;" class="packageStatus">'+ data["clientPackageJSON"]["clientPackageActive"] +
						'</td><td><input type="checkbox" name="payedTable" class="payedTable" '+ checkedVarChar +'/></td><td style="width:15%;" class="packagePrice">'+ data["clientPackageJSON"]["priceOfClientPackage"] +
						'</td><td scope="row" class="clientPackageId" style="display:none;">'+ data["clientPackageJSON"]["id"] +'</td><td><button type="button" class="btn btn-danger deleteClientPackage">Briši</button></td></tr>');
			} else {
				$('#clientPackageBody tr:nth-child(1)').before('<tr class="tr-entity-listAllClientPackages" style=' + colorVar + '><td style="width:15%;" class="packageName">'+ data["clientPackageJSON"]["nameOfPackage"] +
						'</td><td style="width:15%;" class="packageStatus">'+ data["clientPackageJSON"]["clientPackageActive"] +
						'</td><td><input type="checkbox" name="payedTable" class="payedTable" '+ checkedVarChar +'/></td><td style="width:15%;" class="packagePrice">'+ data["clientPackageJSON"]["priceOfClientPackage"] +
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
						'</td><td scope="row" class="packElpackageId" style="display:none;">'+ data.packElpackageId +'</td><td scope="row" class="elemInPackagesId" style="display:none;">'+ data.elemInPackagesId +'</td></tr>');
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
			
			var isItCircular = $('#circularYN').val()

			if(isItCircular){
				$('#callGetTrainingCircular')[0].click();
			} else {
				$('#callGetTraining')[0].click();
			}
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
	    		'</td><td class="roundId" style="display:none;">'+ data.roundId +'</td><td class="exerciseExecId" style="display:none;">' + data.exerciseInRoundExerciseId + '</td><td><a href="/deleteExerciseInRound/'+data.exerciseExecId+'/'+idTraining+'"><button type="button" class="btn btn-danger">Briši</button></a></td></tr>');
       	$("#testTable tr:last").after('<tr><td>PreviTestElement</td><td>DrugiTestElement</td></tr>');
		$("#exerciseInRoundExerciseId").val(data.exerciseInRoundExerciseId);
		},
		error: function (e) {
			 var json = "<h4>Ajax Response</h4>";
	            $('#feedback').html(json);
		}
	})

	
}

function ajaxExerciseInRoundFill(dinamicSelectExerciseId){
	
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
	    		'</td><td class="exerciseExecId" style="display:none;">' + data.exerciseInRoundExerciseId + '</td><td><a href="/deleteExerciseInRound/'+data.exerciseExecId+'/'+idTraining+'"><button type="button" class="btn btn-danger">Briši</button></a></td></tr>');
       	$("#testTable tr:last").after('<tr><td>PreviTestElement</td><td>DrugiTestElement</td></tr>');
			
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
		    		'</td><td id="roundIdDel" class="roundId" style="display:none;">'+ data.selectedRoundId +'</td><td><a href="/deleteRound/'+data.selectedRoundId+'/'+idTraining+'"><button type="button" class="btn btn-danger">Briši</button></a></td></tr>');
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
		},
		error: function (e) {
			alert('Desila se greska prilikom brisanja kruga iz Ajax-a!')
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
			alert('Desila se greska prilikom brisanja vežbe u krugu!')
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
