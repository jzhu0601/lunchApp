define([
    "dojo/_base/declare",
    "dojo/text!./templates/suggestRestaurantContainer.html",
    "dijit/layout/_ContentPaneResizeMixin",
    "dijit/_TemplatedMixin",
    "dojo/store/JsonRest",
    "dojo/store/Memory",
    "dojo/data/ObjectStore",
    "dijit/_WidgetBase",
    "dijit/form/Button",
    "dojo/on",
    "dojo/_base/lang",
    "dgrid/extensions/DijitRegistry",
    "dgrid/OnDemandGrid",
    'dgrid/extensions/Pagination',
    'dgrid/Selection',
    "./suggestRestaurantProfile"
], function (declare, template, _ContentPaneResizeMixin, _TemplatedMixin, JsonRest, Memory,
        ObjectStore, _WidgetBase, Button, on, lang, DijitRegistry, OnDemandGrid, Pagination, Selection,suggestRestaurantProfile) {

//need to have it load the profile for each of the 3 profiles

    return declare("lunchApp.suggestRestaurantContainer", [_WidgetBase, _TemplatedMixin, _ContentPaneResizeMixin], {
        templateString: template,
        constructor: function (args) {
            if (args.parent) {
                this._parent = args.parent;
            }
        },
        buildRendering: function () {
            this.inherited(arguments);
            this.containerNode = this.domNode;
            lunchAppGlobal.suggestRestaurantProfile = this;
            //we need to reference this in the AJAX call
            var self = this;
            
            var xhrArgs = {
                url: "/lunchApp/services/restaurantgraphinfo",
                handleAs: "json",
                headers: {
                    "Content-Type": "application/json"
                },
                load: function (data) {
                    //DO Stuff after the POST is finished
                    for (var i = 0; i < data.length; i++) {
                        //Get the current row of data
                        var currentRow = data[i];
                        //Create new instance of the widget and pass in values it will need
                        var profileWidget = suggestRestaurantProfile({ parent: this,restaurantName:currentRow.restaurantName,data:currentRow });
                        //Place your new widget in the container
                        profileWidget.placeAt(self.restaurantSuggestionContainer);
                    }
                    lunchAppGlobal.mainTabContainer.resize();
                },
                error: function (error) {
                }
            };
            var deferred = dojo.xhrGet(xhrArgs);
        },
        startup: function () {
            this.inherited(arguments);
        }
    });
});



