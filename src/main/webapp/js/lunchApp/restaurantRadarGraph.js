define([
    "dojo/_base/declare",
    "dojo/text!./templates/restaurantGraph.html",
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
    "dojox/charting/Chart",
    "dojox/charting/axis2d/Default",
    "dojox/charting/plot2d/Default",
    "dojox/charting/plot2d/Spider",
    "dojox/charting/axis2d/Base"
], function (declare, template, _ContentPaneResizeMixin, _TemplatedMixin, JsonRest, Memory,
        ObjectStore, _WidgetBase, Button, on, lang, DijitRegistry, OnDemandGrid, Pagination, Selection, Chart, Default, Default, Spider, Base) {

//need to get it to load the user rating and average rating into chart

    return declare("lunchApp.restaurantRadarGraph", [_WidgetBase, _TemplatedMixin, _ContentPaneResizeMixin], {
        templateString: template,
        testVar: "testing123",
        constructor: function (args) {
            if (args.parent) {
                this._parent = args.parent;
            }
            if (args.data) {
                this._data = args.data;
            }
        },
        buildRendering: function () {
            this.inherited(arguments);
            this.containerNode = this.domNode;
            this.chart = new Chart(this.restaurantGraphContainer, {title: 'Look a Chart'});
            this.chart.addPlot("default", {
                type: "Spider",
                labelOffset: -10,
                seriesFillAlpha: 0.2,
                markerSize: 3,
                precision: 0,
                spiderType: "polygon"
            });
            var data = this._data;
            this.chart.addSeries("min", {data: data[0]}, {fill: "white"});
            this.chart.addSeries("max", {data: data[1]}, {fill: "white"});
            this.chart.addSeries("UserReview", {data: data[2]}, {fill: "blue"});
            this.chart.addSeries("AverageReview", {data: data[3]}, {fill: "red"});
            this.chart.render();
            this.chart.removeSeries("min");
            this.chart.removeSeries("max");

        },
        startup: function () {
            this.inherited(arguments);
        }
    });
});










  