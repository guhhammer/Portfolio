document.addEventListener('DOMContentLoaded', function () {
    //TODO: Call AJAX to retrieve list of events
    var eventList = [
        { title: 'Staff Meeting 0530', date: '2020-04-01' },
        { title: 'PT 0545 Manchester', date: '2020-04-02' }
    ];

    var calendarDiv = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarDiv, {
        plugins: ['dayGrid'],
        events: eventList,
    });

    calendar.render();
});
