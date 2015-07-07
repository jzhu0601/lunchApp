define([
    "dojo/_base/declare",
    "dojo/text!./templates/restaurantProfileGrid.html",
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
], function (declare,template,_ContentPaneResizeMixin,_TemplatedMixin,JsonRest,Memory,
ObjectStore,_WidgetBase,Button,on,lang,DijitRegistry,OnDemandGrid,Pagination,Selection) {

    return declare("lunchApp.restaurantProfileGrid", [_WidgetBase, _TemplatedMixin, _ContentPaneResizeMixin], {

        templateString: template,
        constructor: function (args) {
            if (args.parent) {
                this._parent = args.parent;
            }
        },
        buildRendering: function () {
            this.inherited(arguments);
            this.containerNode = this.domNode;
            //This gives us access to this widget from the console
            //having access to this variable makes it much easier to debug your widgets
            //This variable can also be easily used to call this widget from other widgets methods
            lunchAppGlobal.restaurantProfileGrid = this;

            //Rest store to initialize the grid with
            //Make sure your idProperty in unqiue
            var restStore = JsonRest({
                target: "/lunchApp/services/vreadrestaurantprofile",
                idProperty: 'restaurantProfileId',
                sortParam: "sortBy"
            });

            this.grid = new OnDemandGrid({
                id: 'restaurantProfileId',
                collection: restStore,
                allowTextSelection:true,
                selectionMode: 'single',
                minRowsPerPage: 2000,
                maxRowsPerPage: 2000,
                //http://dgrid.io/tutorials/0.4/defining_grid_structures/
                columns: {
                    //Make sure these correspond to a Column in your Dataset 
                    //Only exception is this is not necessary if using the renderCell Property
                    restaurantName: {
                        label: "Restaurant Name"
                    },
                    timeFactor: {
                        label: "Time Factor"
                    },
                    costFactor: {         
                        label: "Cost Factor"
                    },
                    postLunchFullnessFactor: {
                        label: 'Post Lunch Fullness Factor'
                    },
                    deliciousnessFactor: {
                        label: 'Deliciousness Factor'
                    },
                    postLunchDiscomfortFactor: {
                        label: 'Post Lunch Discomfort Factor'
                    },
                    optIn: {
                        label:'Opt In?'
                    }
                }
            },this.restaurantProfileGridContainer);
            
            //Attach an event listener to the grid select
            this.grid.on('dgrid-select', function (event) {
                console.log('grid selected');
            });
            //Attach an event listener to the grid deselect
            this.grid.on('dgrid-deselect', function (event) {
                console.log('grid deselected');
            });
            //This event will fire after the grid has finished loading.
            this.grid.on('dgrid-refresh-complete', function (event) {
                console.log('refreshed Grid Complete');
            });

            //Places the Grid in the DOM
            //If you do not call this then the widget will not show up
            this.grid.startup();
            //Populate Grid
            this.populateGrid();
            
            //Save button for our form
            //Documentation for this widgets properties and events can be found here
            //https://dojotoolkit.org/api/?qs=1.10/dijit/form/Button
            this.btnCreateNewRestaurant = Button({
                id: "createNewRestaurantProfileButton",
                name: "createRestaurantProfile",
                label: "Add New Profile"
            }).placeAt(this.restaurantProfileGridFooter);
            //Attach a click event to the button
            on(this.btnCreateNewRestaurant, "click", lang.hitch(this, this.showCreateRestaurantProfile));
        },
        
        showCreateRestaurantProfile:function(){
            //Show the dialog so the user can create a new Restaurant
            lunchAppGlobal.addLunchRestaurantProfileDialog.show();
        },
        
        populateGrid: function () {
            //Create the rest store to be used for the grid
            //http://dojotoolkit.org/reference-guide/1.10/dojo/store/JsonRest.html
            var restStore = JsonRest({
                target: "/lunchApp/services/vreadrestaurantprofile",
                idProperty: 'restaurantProfileId',
                sortParam: "sortBy"
            });
            //Whenever you set the store for a grid it will refresh the grids data
            this.grid.set("store",restStore);
        },
        
        startup: function () {
            this.inherited(arguments);
        }
    });
});
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


