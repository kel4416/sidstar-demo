$(document).ready(function () {
    $("#apikeyForm").submit(function(e){
        e.preventDefault()
        if($(apikeyInput).val() != ""){
           $.post("/api/getAirports",{"apiKey": $("#apikeyInput").val()}, function(data){
                if (data.hasOwnProperty("Error")){
                  alert("There is an error with retrieving data with this API KEY. Please try with another key")
                }else{
                  setupDataTable()
                }
           });

        }else{
          alert("API-KEY input is empty! Please key in the API KEY before pressing start!")
        }

    });

    $( "#sidStarFormSelect" ).change(function() {
        stdIntrutmentType = $( "#sidStarFormSelect option:selected").val()
        if(stdIntrutmentType == "sids" || stdIntrutmentType == "stars"){
            $.post("/api/getTopTwoAssoWaypoints",{"icao":$("#sidStarFormSTinput").val(),"stdIntrutmentType":stdIntrutmentType,"apiKey":$("#apikeyInput").val()},function(data){
                if(data.hasOwnProperty("Error")){
                    alert("There is an error with retrieving data with this API KEY. Please try with another key")
                }else{
                    var content = '<div class="container"><table class="table">'
                    content += "<thead class='thead-dark'><th>Waypoint name</th><th>Count</th></thead><tbody>"
                    for(i=0; i<data["topWaypoints"].length; i++){
                        toAdd = '<tr><td>' + data["topWaypoints"][i]["name"] + "</td><td>" + data["topWaypoints"][i]["count"] + '</td></tr>';
                        content += toAdd
                    }
                    content += "</tbody></table>"

                    jsonRepresentation = "<h5>JSON DATA</h5><p id='jsonDataRetrieved'>" + JSON.stringify(data,null,2) + "'</p>"
                    content += jsonRepresentation

                    kafkaButton = '<button type="button" id="kafkaButton" class="btn btn-danger">Post to Kafka</button><br/></br/>'
                    content += kafkaButton

                    content += "</div>"
                    $("#sidStarModalInfo").html(content)

                    $("#kafkaButton").click(function(d){
                        var kafkaPwd=prompt("Please enter your Kafka password:");
                            if (kafkaPwd!=null){
                              kafkaMessage = '{"stdInstru":"' + $("#sidStarFormSelect").val().slice(0,-1).toUpperCase() + '",' + $("#jsonDataRetrieved").html().slice(1,-1) + '}'

                              $.post("/api/postSIDSTARKafkaMessage",{"topic": "sidstar","jsonData":kafkaMessage,"password":kafkaPwd}, function(data){
                                      if (data.hasOwnProperty("Error")){
                                        alert("There is an error with posting to Kafka Server")
                                      }else{
                                        alert("Succesfully posted to Kafka Server")
                                      }
                                 });
                            }else{
                                alert("Password cannot be empty")
                            }
                    })
                }
            })
        }else{
            $("#sidStarModalInfo").html("")
        }
    });
});

function setupDataTable(){
    var airportsTable = $('#airportsTable').DataTable({
          ajax: {
             url: '/api/getAirports',
             type: 'POST',
             data:{
                 "apiKey": $("#apikeyInput").val()
             }
           },
           columns: [
               { data: 'icao' },
               { data: 'lat' },
               { data: 'lng' },
               { data: 'alt' },
               { data: 'iata' }
           ],
     });

     $('#airportsTable tbody').on('click', 'tr', function () {

             var data = airportsTable.row(this).data();
             $("#sidStarModalTitle").html(data["icao"])
             $("#sidStarFormSTinput").val(data["icao"])
             const sidStarModal = new bootstrap.Modal(document.getElementById('sidStarModal'), {})
             $("#sidStarFormSelect").val("default")
             $("#sidStarModalInfo").html("")
             sidStarModal.show()
      });
}