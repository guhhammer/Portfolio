document.addEventListener('DOMContentLoaded', function () {
    $( "#dialog-message" ).dialog({
        autoOpen: false,
        modal: true,
        buttons: {
          Close: function() {
            $( this ).dialog( "close" );
          },
          Save: function() {
            $( this ).dialog( "close" );
            alert("TODO: AJAX new password reset");
          }
        }
      });

    function AppViewModel() {
        var self = this;

        this.resetUsername = ko.observable();
        this.resetNewPassword = ko.observable();

        this.entries = [
            { name: "Rank LName", aid: "A11", status: "Active", OI: "Capt LName", commandBillet: "Squad Leader", collateralBillet: "None"  },
            { name: "Rank LName", aid: "A11", status: "Active", OI: "Capt LName", commandBillet: "Squad Leader", collateralBillet: "None"  },
            { name: "Rank LName", aid: "A11", status: "Active", OI: "Capt LName", commandBillet: "Squad Leader", collateralBillet: "None"  },
            { name: "Rank LName", aid: "A11", status: "Active", OI: "Capt LName", commandBillet: "Squad Leader", collateralBillet: "None"  },
            { name: "Rank LName", aid: "A11", status: "Active", OI: "Capt LName", commandBillet: "Squad Leader", collateralBillet: "None"  },
        ];

        this.resetPassword = function(e) {
            self.resetUsername(e.name);
            self.resetNewPassword("");
            $( "#dialog-message" ).dialog('open');
        }
        
        this.viewUser = function(e) {
            alert("TODO: View User");
        }

        //TODO: Poke week to do first update
    }

    ko.applyBindings(new AppViewModel());
});