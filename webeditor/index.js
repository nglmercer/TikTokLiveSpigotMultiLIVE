

var tikTokEvents = [];



async function loadAndDisplayEvents() {

  const response = await fetch('resources/TikTokEventsSchema.json');

  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }

  tikTokEvents = await response.json();
  displayEvents(tikTokEvents)
  createEventList(tikTokEvents);
}

function displayEvents(tikTokEvents) {
  let eventsContainer = $('#eventsContainer');
  eventsContainer.empty();
  tikTokEvents.forEach(event => {
    let eventCard = `
      <div class="card mb-3">
      <div class="card-header">${event.javaClassName}</div>
      <div class="card-body">
        <h5 class="card-title">${event.name}</h5>
        <ul class="field-list">
          ${Object
        .keys(event.fields)
        .map(field => createFieldNameElement(event, field))
        .join('')}
        </ul>
      </div>
      </div>
      `
    eventsContainer.append(eventCard);
  });

  $('.field-name').hover(
    function () {
      $(this).addClass('highlight');
    },
    function () {
      $(this).removeClass('highlight');
    }
  );

  $('.field-name').click(function () {
    let value = $(this).data('value');
    value = "${" + value + "}"
    navigator.clipboard.writeText(value).then(function () {
    }).catch(function (error) {
      alert('Failed to copy text: ' + error);
    });
  });
}




function createFieldNameElement(event, field) {
  return `<div  class="field-item" >
     <div  class= "row">

     <span class="badge field-type col-1">
       ${event.fields[field]}
     </span>

     <div data-value="${field}" class="field-name">
        ${field}
     </div> 
    
     </div>
    
     </div>`
}

function createEventList(tikTokEvents) {
  let eventsList = $('#events-list');
  tikTokEvents.forEach(event => {
    let eventCard = `
      <li class="list-group-item" data-value="${event.name}" >${event.name}</li>
      `
    eventsList.append(eventCard);
  });

  $('.list-group-item').hover(
    function () {
      $(this).addClass('active');
    },
    function () {
      $(this).removeClass('active');
    }
  );

  $('.list-group-item').click(function () {
    let value = $(this).data('value');
    $('#searchBar').val(value);
    $('#searchBar').trigger("input");
    $('#searchBar').val("");
  });
}


$('#searchBar').on('input', function () {
  const query = $(this).val().toLowerCase();
  const filteredEvents = tikTokEvents.filter(event => event.name.toLowerCase().includes(query));
  displayEvents(filteredEvents);
  console.log("ASDAS")
});




$(document).ready(function () {
  loadAndDisplayEvents();
});