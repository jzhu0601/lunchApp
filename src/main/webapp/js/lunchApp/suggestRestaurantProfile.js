define([
    "dojo/_base/declare",
    "dojo/text!./templates/suggestRestaurantProfile.html",
    "./restaurantRadarGraph",
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
    'dgrid/Selection'
], function (declare, template, restaurantRadarGraph, _ContentPaneResizeMixin, _TemplatedMixin, JsonRest, Memory,
        ObjectStore, _WidgetBase, Button, on, lang, DijitRegistry, OnDemandGrid, Pagination, Selection) {

//need to add restaurant info, map, graph

    return declare("lunchApp.suggestRestaurantProfile", [_WidgetBase,
        _TemplatedMixin, _ContentPaneResizeMixin], {
        templateString: template,
        constructor: function (args) {
            if (args.parent) {
                this._parent = args.parent;
            }
            if (args.restaurantName) {
                this.restaurantName = args.restaurantName;
            }
            if (args.data) {
                this.data = args.data;
            }
        },
        buildRendering: function () {
            this.inherited(arguments);
            this.containerNode = this.domNode;
            var newData = this.formatData(this.data);
            var graphWidget = restaurantRadarGraph({parent: this, data: newData});
            graphWidget.placeAt(this.restaurantGraph);
            
            
//            var newMapData= this.DDDDDDDDDD(this.MAPDDDDDDDDD); 
//            var mapWidget= restaurantMap({parent: this, data: newMapData});
//            mapWidget.placeAt(this.restaurantMap);
                        
        },
        formatData: function (unformattedData) {

            var formattedData = [];

            //User Profile Object
            var userProfile = {};
            userProfile.Time = unformattedData.timeFactor;
            userProfile.Cost = unformattedData.costFactor;
            userProfile.Fullness = unformattedData.postLunchFullnessFactor;
            userProfile.Deliciousness = unformattedData.deliciousnessFactor;
            userProfile.Discomfort = unformattedData.postLunchDiscomfortFactor;

            //Average Profile Object
            var averageProfile = {};
            averageProfile.Time = unformattedData.avgTimeFactor;
            averageProfile.Cost = unformattedData.avgCostFactor;
            averageProfile.Fullness = unformattedData.avgPostLunchFullnessFactor;
            averageProfile.Deliciousness = unformattedData.avgDeliciousnessFactor;
            averageProfile.Discomfort = unformattedData.avgPostLunchDiscomfortFactor;

            //Default Min and max added for bug fix reasons
            var minObject = {"Time": 0, "Cost": 0, "Fullness": 0, "Deliciousness": 0, "Discomfort": 0};
            var maxObject = {"Time": 10, "Cost": 10, "Fullness": 10, "Deliciousness": 10, "Discomfort": 10};

            formattedData.push(minObject);
            formattedData.push(maxObject);
            formattedData.push(userProfile);
            formattedData.push(averageProfile);
            return formattedData;
        },
        startup: function () {
            this.inherited(arguments);
        }
    });
});



