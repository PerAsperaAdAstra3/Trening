function addPersonalTrainer(){
	var personalTrainer = {}
	
	personalTrainer["givenName"] = $("#givenName").val();
	personalTrainer["familyName"] = $("#familyName").val();
	personalTrainer["emailTrainer"] = $("#emailTrainer").val();
	personalTrainer["telephoneTrainer"] = $("#telephoneTrainer").val();
	
$.ajax({
	type: "POST",
	contentType: "application/json",
	url:	"/##callSignal##",
	data: JSON.stringify(personalTrainer),
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
	}
})
	
}