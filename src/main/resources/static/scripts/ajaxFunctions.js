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
		console.log(data);	
	    $("#exercisesInRoundTable tr:last").after('<tr class="hidden_input" id="tr-entity-list"><td class="exerciseName">'+ data.exerciseInRoundExerciseName +'</td><td class="exerciseInRoundId" style="display:none;">'+ data.exerciseExecId +'</td><td class="numberOfRepetitions">'+ data.exerciseInRoundNumberOfRepetitions  +'</td><td class="difficulty">'+ data.exerciseInRoundDifficulty +'</td><td class="note">'+ data.exerciseInRoundNote +'</td><td class="roundId" style="display:none;">'+ data.roundId +'</td><td class="exerciseExecId" style="display:none;">' + data.exerciseInRoundExerciseId + '</td><td><a href="/deleteExerciseInRound/'+data.exerciseExecId+'/'+idTraining+'"><button type="button" class="btn btn-danger">Briši</button></a></td></tr>');
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
		console.log(data);	
	    $("#exercisesInRoundTable tr:last").after('<tr class="hidden_input" id="tr-entity-list"><td class="exerciseName">'+ data.exerciseInRoundExerciseName +'</td><td class="exerciseInRoundId" style="display:none;">'+ data.exerciseExecId +'</td><td class="numberOfRepetitions">'+ data.exerciseInRoundNumberOfRepetitions  +'</td><td class="difficulty">'+ data.exerciseInRoundDifficulty +'</td><td class="note">'+ data.exerciseInRoundNote +'</td><td class="roundId" style="display:none;">'+ data.roundId +'</td><td class="exerciseExecId" style="display:none;">' + data.exerciseInRoundExerciseId + '</td><td><a href="/deleteExerciseInRound/'+data.exerciseExecId+'/'+idTraining+'"><button type="button" class="btn btn-danger">Briši</button></a></td></tr>');
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
			console.log(data);	
			var idTraining = $(".idTraining").val();			
				$("#roundsTable tr").each(function() {
					removeHighlights(this)
				});
		    $("#roundsTable tr:last").after('<tr id="round-id-sync" class="highlighted"><td id="roundRoundSequenceNumberId" class="roundRoundSequenceNumber">'+ data.roundRoundSequenceNumber +'</td><td id="roundIdDel" class="roundId" style="display:none;">'+ data.selectedRoundId +'</td><td><a href="/deleteRound/'+data.selectedRoundId+'/'+idTraining+'"><button type="button" class="btn btn-danger">Briši</button></a></td></tr>');
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

// AJAX Delete Exercise In Round

function ajaxDeleteExerciseInRound(roundId, thisObject){
	
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
