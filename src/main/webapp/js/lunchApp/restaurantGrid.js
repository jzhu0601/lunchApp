define([
    "dojo/_base/declare",
    "dojo/text!./templates/restaurantGrid.html",
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

    return declare("lunchApp.restaurantGrid", [_WidgetBase, _TemplatedMixin, _ContentPaneResizeMixin], {
        //Template used for this widget
        //https://dojotoolkit.org/documentation/tutorials/1.10/templated/
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
            lunchAppGlobal.restaurantGrid = this;

            //Rest store to initialize the grid with
            //Make sure your idProperty in unqiue
            var restStore = JsonRest({
                target: "/lunchApp/services/restaurants",
                idProperty: 'restaurantId',
                sortParam: "sortBy"
            });

            //dGrid Docs
            //https://github.com/SitePen/dgrid/blob/v0.4.0/doc/components/core-components/OnDemandList-and-OnDemandGrid.md
            this.grid = new (declare([ OnDemandGrid,Selection]))({
                id: 'restaurantGrid',
                collection: restStore,
                allowTextSelection:true,
                selectionMode: 'single',
                minRowsPerPage: 2000,
                maxRowsPerPage: 2000,
                //http://dgrid.io/tutorials/0.4/defining_grid_structures/
                columns: {
                    //Make sure these correspond to a Column in your Dataset 
                    //Only exception is this is not necessary if using the renderCell Property
                    edit: {
                        label: 'Edit',
                        renderCell: function (item, rowIndex, cell) {
                            //Create the dom node to be used to contain the data
                            var btn = new Button({
                                label: "Edit"
                            });
                            on(btn, "click", function (evt) {
                                console.log("click test");
                                // btnCreateNewRestaurant.lunchLocation.set('label', "Update");
                                lunchAppGlobal.lunchLocation.btnCreateLocation.set('label', "Update");
                                lunchAppGlobal.lunchLocation.populateDialog(lunchAppGlobal.restaurantGrid.currentSelected);
                                lunchAppGlobal.addLunchRestaurantDialog.show();
                                
                            });
                            return btn.domNode;     
                        }
                    },
                    name: {
                        label: "Name"
                    },
                    address: {
                        label: "Address"
                    },
                    zip: {
                        //Table Header Value
                        label: "City,State Zip",
                        //This is a function that formats a table cell
                        //This is mainly used for adding widgets to cells or formatting data like so
                        //https://github.com/SitePen/dgrid/blob/v0.3.15/doc/components/core-components/Grid.md
                        renderCell: function (item, rowIndex, cell) {
                            //Create the dom node to be used to contain the data
                            var cityStatZipContainer = document.createElement('div');
                            //Concat the 3 fields into a variable
                            var cityStateZip = item.city + ", " + item.state +" "+ item.zip;
                            //Set the innerHTML to the variable
                            cityStatZipContainer.innerHTML = cityStateZip;
                            //Return the domNode
                            return cityStatZipContainer; 
                        }
                    },
                    website: {
                        label: 'Website',
                        renderCell: function (item, rowIndex, cell) {
                            //Create the dom node to be used to contain the data
                            var websiteContainer = document.createElement('a');
                            //Set the innerHTML to the variable
                            websiteContainer.innerHTML = item.website;
                            websiteContainer.href = item.website;
                            websiteContainer.target="_blank";
                            //Return the domNode
                            return websiteContainer; 
                        } 
                    }
                }
            },this.restaurantGridContainer);
            
            //Attach an event listener to the grid select
            this.grid.on('dgrid-select', function (event) {
                console.log('grid selected');
                lunchAppGlobal.restaurantGrid.currentSelected=event.rows[0].data;
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
                id: "createNewRestaurantButton",
                name: "createRestaurant",
                label: "Add New"
            }).placeAt(this.restaurantGridFooter);
            //Attach a click event to the button
            on(this.btnCreateNewRestaurant, "click", lang.hitch(this, this.showCreateRestaurant));
        
        this.btnLogout = Button({
                id: "LunchAppLogout",
                name: "LunchAppLogout",
                label: "Logout"
            }).placeAt(this.restaurantGridFooter);
            //Attach a click event to the button
            on(this.btnLogout, "click", lang.hitch(this, this.afterLogout));
        },
        
        afterLogout : function(){
            
              //Post to create the restaurant
            var xhrArgs = {
                url: "/lunchApp/services/logoutuser",
                
                handleAs: "json",
                headers: {
                    "Content-Type": "application/json"
                },
                load: function (data) {
                    //DO Stuff after the POST is finished
                     location.reload();
                },
                error: function (error) {
                    //POST ERROR
                }
            };
            // Call the asynchronous xhrPost
            var deferred = dojo.xhrPost(xhrArgs);
        
            
           
        },
        
        
        showCreateRestaurant:function(){
            //Show the dialog so the user can create a new Restaurant
            lunchAppGlobal.addLunchRestaurantDialog.show();
        },
        
        populateGrid: function () {
            //Create the rest store to be used for the grid
            //http://dojotoolkit.org/reference-guide/1.10/dojo/store/JsonRest.html
            var restStore = JsonRest({
                target: "/lunchApp/services/restaurantopen",
                idProperty: 'restaurantId',
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
