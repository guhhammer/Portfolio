document.addEventListener('DOMContentLoaded', function () {
    function AppViewModel() {
        var self = this;
        this.currentDirectory = ko.observable();
        this.currentFiles = ko.observableArray();

        this.newFileName = ko.observable();
        this.newDirectoryName = ko.observable();

        this.uploadFile = function() {
            //TODO: Upload File
        }

        this.createDirectory = function() {
            //TODO: Upload File
        }

        this.entries = {
            directories: [
                {
                    name: "Public Documents",
                    fullPath: "Public Documents",
                    directories: [
                        { 
                            name: "Unit References",
                            fullPath: "Public Documents/Unit References",
                            directories: [],
                            files: [
                                {name: "test.txt", url: "Public Documents/Unit References/test.txt"}
                            ]
                        }
                    ],
                    files: []
                },
                {
                    name: "Private Documents",
                    fullPath: "Private Documents",
                    directories: [],
                    files: []
                }
            ]
        };

        this.changeDirectory = function (e) {
            self.currentDirectory(e.fullPath);
            self.currentFiles(e.files);
        }

    }

    ko.applyBindings(new AppViewModel());
});