document.addEventListener('DOMContentLoaded', function () {
    function AppViewModel() {
        this.to = ko.observable();
        this.subject = ko.observable();
        this.message = ko.observable();

        this.entries = [
            { subject: "Notification of Chit Approval", message: "Your chit's been approved", from: "System", date: "Today" },
            { subject: "Notification of Chit Denial", message: "Your chit's been denied", from: "System", date: "Yesterday" },

        ];

        this.sendMessage = function() {
            alert("TODO: Use Ajax, to send this message\nTo: " + this.to() + "\nSubject: " + this.subject() + "\nMessage: " + this.message());
        }
    }

    ko.applyBindings(new AppViewModel());
});