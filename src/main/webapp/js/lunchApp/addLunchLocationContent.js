define([
    "dojo/_base/declare",
    "dijit/_WidgetBase",
    "dijit/_TemplatedMixin",
    "dojo/_base/lang",
    "./main",
    "dijit/form/ValidationTextBox",
    "dojox/form/DropDownSelect",
    "dijit/form/Button",
    "dijit/form/CheckBox",
    "dojo/dom",
    "dojo/on",
    "dojo/text!./templates/addLunchLocationContent.html"

], function (declare,_WidgetBase,_TemplatedMixin,lang,lunchApp,ValidationTextBox,DropDownSelect,Button,CheckBox,dom,on,template) {
    return declare("lunchApp.addLunchLocationContent", [_WidgetBase, _TemplatedMixin], {
        templateString: template,
        constructor: function (args) {
            if (args.parent) {
                this._parent = args.parent;
            }
        },
        buildRendering: function () {
            this.inherited(arguments);
            lunchAppGlobal.lunchLocation = this;

            //Location name Textbox
            //Documentation for this widgets properties and events can be found here
            //https://dojotoolkit.org/api/?qs=1.10/dijit/form/ValidationTextBox
            this.txtLocationName = new ValidationTextBox({
                'class': 'textboxWidth',
                regExp: ".+",
                required: true
            }, "text").placeAt(this.lunchLocationName);

            //Location name Textbox
            this.txtLocationCity = new ValidationTextBox({
                'class': 'textboxWidth',
                value: "Columbus",
                regExp: ".+",
                required: true
            }, "text").placeAt(this.lunchLocationCity);

            //Location State Textbox
            //Disabled this textbox because odds are you are not leaving Ohio for lunch
            this.txtLocationState = new ValidationTextBox({
                'class': 'textboxWidth',
//                disabled:true,
                maxLength: "2",
                regExp: "[a-zA-Z]{2}",
                value: "OH",
                required: true
            }, "text").placeAt(this.lunchLocationState);

            //Location Address Textbox
            this.txtLocationAddress = new ValidationTextBox({
                'class': 'textboxWidth',
                regExp: ".+",
                required: true
            }, "text").placeAt(this.lunchLocationAddresss);

            //Location Zip Textbox
            this.txtLocationZip = new ValidationTextBox({
                'class': 'textboxWidth',
                regExp: "[\\d]{5}",
                required: true
            }, "text").placeAt(this.lunchLocationZip);

            //Location Website Textbox
            this.txtLocationWebsite = new ValidationTextBox({
                'class': 'textboxWidth',
                regExp: "(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?",
                required: false

            }, "text").placeAt(this.lunchLocationWebsite);


            this.mondayCheckBoxWidget = new CheckBox({
                id:"mondayCheckboxContainer",
                name: "dayOfWeekCheckbox",
                value: 1,
                checked: false,
                onChange: function(b){
                    console.log('onChange called with parameter = ' + b + ', and widget value = ' + this.get('value') ); 
                }
            }).placeAt(this.mondayCheckbox);
            this.tuesdayCheckBox = new CheckBox({
                id:"tuesdayCheckboxContainer",
                name: "dayOfWeekCheckbox",
                value: 1,
                checked: false,
                onChange: function(b){
                    console.log('onChange called with parameter = ' + b + ', and widget value = ' + this.get('value') ); 
                }
            }).placeAt(this.tuesdayCheckbox);
            this.wednesdayCheckBox = new CheckBox({
                id:"wednesdayCheckboxContainer",
                name: "dayOfWeekCheckbox",
                value: 1,
                checked: false,
                onChange: function(b){
                    console.log('onChange called with parameter = ' + b + ', and widget value = ' + this.get('value') ); 
                }
            }).placeAt(this.wednesdayCheckbox);
            this.thursdayCheckBox = new CheckBox({
                id:"thursdayCheckboxContainer",
                name: "dayOfWeekCheckbox",
                value: 1,
                checked: false,
                onChange: function(b){
                    console.log('onChange called with parameter = ' + b + ', and widget value = ' + this.get('value') ); 
                }
            }).placeAt(this.thursdayCheckbox);
            this.fridayCheckBox = new CheckBox({
                id:"fridayCheckboxContainer",
                name: "dayOfWeekCheckbox",
                value: 1,
                checked: false,
                onChange: function(b){
                    console.log('onChange called with parameter = ' + b + ', and widget value = ' + this.get('value') ); 
                }
            }).placeAt(this.fridayCheckbox);
            this.saturdayCheckBox = new CheckBox({
                id:"saturdayCheckboxContainer",
                name: "dayOfWeekCheckbox",
                value: 1,
                checked: false,
                onChange: function(b){
                    console.log('onChange called with parameter = ' + b + ', and widget value = ' + this.get('value') ); 
                }
            }).placeAt(this.saturdayCheckbox);
            
            this.sundayCheckBox = new CheckBox({
                id:"sundayCheckboxContainer",
                name: "dayOfWeekCheckbox",
                value: 1,
                checked: false,
                onChange: function(b){
                    console.log('onChange called with parameter = ' + b + ', and widget value = ' + this.get('value') ); 
                }
            }).placeAt(this.sundayCheckbox);

            //Save button for our form
            //Documentation for this widgets properties and events can be found here
            //https://dojotoolkit.org/api/?qs=1.10/dijit/form/Button
            this.btnCreateLocation = Button({
                id: "createLocationButton",
                name: "createLocation",
                label: "Create"
            }).placeAt(this.createLocation);
            //Attach a click event to the button
            on(this.btnCreateLocation, "click", lang.hitch(this, this.insertUpdateRestaurant));
            

            lunchApp.addLocationContent = this;
        },
        
        insertUpdateRestaurant: function (){
            if (this.dialogState == "insert"){
                this.insertRestaurant();
            }
            else{
                console.log("update");
                
                this.updateRestaurant();

          
          
           }
        },
        
        updateRestaurant : function (){
                  var self = this;
            //Get the input values into variables
            var restaurantId = lunchAppGlobal.restaurantGrid.currentSelected.restaurantId;
            var openId = lunchAppGlobal.restaurantGrid.currentSelected.openId;
            var name = this.txtLocationName.get('value');
            var city = this.txtLocationCity.get('value');
            var state = this.txtLocationState.get('value');
            var address = this.txtLocationAddress.get('value');
            var zip = this.txtLocationZip.get('value');
            var website = this.txtLocationWebsite.get('value');
            
            //Get Days of Week Values
            var monday = this.getCheckboxValue(mondayCheckboxContainer);
            var tuesday = this.getCheckboxValue(tuesdayCheckboxContainer);
            var wednesday = this.getCheckboxValue(wednesdayCheckboxContainer);
            var thursday = this.getCheckboxValue(thursdayCheckboxContainer);
            var friday = this.getCheckboxValue(fridayCheckboxContainer);
            var saturday = this.getCheckboxValue(saturdayCheckboxContainer);
            var sunday = this.getCheckboxValue(sundayCheckboxContainer);

            //Post to create the restaurant
            var xhrArgs = {
                url: "/lunchApp/services/updateRestaurant/",
                postData: dojo.toJson({
                    restaurantId : restaurantId,
                    openId: openId,
                    name: name,
                    city: city,
                    state: state,
                    address: address,
                    zip: zip,
                    website: website,
                    monday : monday,
                    tuesday : tuesday,
                    wednesday : wednesday,
                    thursday : thursday,
                    friday : friday,
                    saturday : saturday,
                    sunday : sunday

                    
                }),
                handleAs: "json",
                headers: {
                    "Content-Type": "application/json"
                },
                load: function (data) {
                    //DO Stuff after the POST is finished
                    lunchAppGlobal.addLunchRestaurantDialog.hide();
                     lunchAppGlobal.restaurantGrid.grid.refresh();
                },
                error: function (error) {
                    //POST ERROR
                }
            };
            // Call the asynchronous xhrPost
            var deferred = dojo.xhrPost(xhrArgs);
        },

        
        settingCheckbox: function(widget,checkValue){
            if(checkValue == 1){
                widget.set('checked',true);
            }else{
                widget.set('checked',true);
            }
        },
        
            insertRestaurant: function () {
            var self = this;

            //Get the input values into variables
            var name = this.txtLocationName.get('value');
            var city = this.txtLocationCity.get('value');
            var state = this.txtLocationState.get('value');
            var address = this.txtLocationAddress.get('value');
            var zip = this.txtLocationZip.get('value');
            var website = this.txtLocationWebsite.get('value');
            
            //Get Days of Week Values
            var monday = this.getCheckboxValue(mondayCheckbox);
            var tuesday = this.getCheckboxValue(tuesdayCheckbox);
            var wednesday = this.getCheckboxValue(wednesdayCheckbox);
            var thursday = this.getCheckboxValue(thursdayCheckbox);
            var friday = this.getCheckboxValue(fridayCheckbox);
            var saturday = this.getCheckboxValue(saturdayCheckbox);
            var sunday = this.getCheckboxValue(sundayCheckbox);

            //Post to create the restaurant
            var xhrArgs = {
                url: "/lunchApp/services/insertRestaurants/",
                postData: dojo.toJson({
                    name: name,
                    city: city,
                    state: state,
                    address: address,
                    zip: zip,
                    website: website,
                    monday : monday,
                    tuesday : tuesday,
                    wednesday : wednesday,
                    thursday : thursday,
                    friday : friday,
                    saturday : saturday,
                    sunday : sunday
                }),
                handleAs: "json",
                headers: {
                    "Content-Type": "application/json"
                },
                load: function (data) {
                    //DO Stuff after the POST is finished
                    lunchAppGlobal.addLunchLocationDialog.hide();
                },
                error: function (error) {
                    //POST ERROR
                }
            };
            // Call the asynchronous xhrPost
            var deferred = dojo.xhrPost(xhrArgs);
        },
        
        populateDialog: function(data){
            
            this.txtLocationName.set('value',data.name);
            this.txtLocationCity.set('value',data.city);
            this.txtLocationState.set('value',data.state);
            this.txtLocationAddress.set('value',data.address);
            this.txtLocationZip.set('value',data.zip);
            this.txtLocationWebsite.set('value',data.website);
            
            var monday = dijit.byId("mondayCheckboxContainer");
            this.settingCheckbox(monday,data.monday);
            var tuesday = dijit.byId("tuesdayCheckboxContainer");
            this.settingCheckbox(tuesday,data.tuesday);
            var wednesday = dijit.byId("wednesdayCheckboxContainer");
            this.settingCheckbox(wednesday,data.wednesday);
            var thursday = dijit.byId("thursdayCheckboxContainer");
            this.settingCheckbox(thursday,data.thursday);
            var friday = dijit.byId("fridayCheckboxContainer");
            this.settingCheckbox(friday,data.friday);
            var saturday = dijit.byId("saturdayCheckboxContainer");
            this.settingCheckbox(saturday,data.saturday);
            var sunday = dijit.byId("sundayCheckboxContainer");
            this.settingCheckbox(sunday,data.sunday);
        },
            
            settingCheckbox: function(widget,checkValue){
                if(checkValue == 1){
                widget.set('checked',true);
                }else{
                widget.set('checked',false);
              }
            },
        
        getCheckboxValue:function(checkboxId){
            var checkboxValue;
            var isChecked = dijit.byId(checkboxId).checked;  
            if(isChecked){
                checkboxValue = 1;
            }else{
                checkboxValue = 0;
            }
          return checkboxValue;
        },
        clearDialog: function () {
            //Clear Dialog back to Default 
        },
        startup: function () {
            this.inherited(arguments);
        }
    });
});
