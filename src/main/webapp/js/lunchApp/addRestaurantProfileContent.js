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
    "dojo/text!./templates/addRestaurantProfileContent.html",
    "dijit/form/FilteringSelect",
    "dojo/store/JsonRest",
    "dojo/data/ObjectStore",
    "dojo/parser",
    "dijit/form/HorizontalSlider",
     "dijit/form/TextBox",
     "dijit/form/Select"
], function (declare,_WidgetBase,_TemplatedMixin,lang,lunchApp,ValidationTextBox,DropDownSelect,Button,CheckBox,dom,on,template,FilteringSelect,JsonRest,ObjectStore,parser,HorizontalSlider,TextBox,Select) {
    return declare("lunchApp.addRestaurantProfileContent", [_WidgetBase, _TemplatedMixin], {
        templateString: template,
        constructor: function (args) {
            if (args.parent) {
                this._parent = args.parent;
            }
        },
        buildRendering: function () {
            var self = this;
            this.inherited(arguments);
            lunchAppGlobal.lunchRestaurantProfile = this;
            
            var dropdownRestStore = JsonRest({
                target: "/lunchApp/services/restaurants",
                idProperty: 'restaurantId'
            });

            this.restaurantDropdown = FilteringSelect({
                id: "restaurantDropdown",
                store: new ObjectStore({objectStore: dropdownRestStore}),
//                autoComplete: true,
                searchAttr: "name"
            }).placeAt(this.restaurantDropdownContainer);
             
                this.lunchTimeFactorSlider = new HorizontalSlider({
                value: 5,
                minimum: 0,
                maximum: 10,
                intermediateChanges: true,
                style: "width:300px;",
                onChange: function (value) {
                    self.lunchTimeFactor.innerHTML = value;
                }
                }).placeAt(this.timeFactorDropdownContainer);
                
                
                this.lunchCostFactorSlider = new HorizontalSlider({
                value: 5,
                minimum: 0,
                maximum: 10,
                intermediateChanges: true,
                style: "width:300px;",
                onChange: function (value) {
                    self.lunchCostFactor.innerHTML = value;
                }
                }).placeAt(this.costFactorDropdownContainer);
                
                
            this.lunchFullnessFactorSlider = new HorizontalSlider({
                value: 5,
                minimum: 0,
                maximum: 10,
                intermediateChanges: true,
                style: "width:300px;",
                onChange: function (value) {
                    self.lunchFullnessFactor.innerHTML = value;
                }
            }).placeAt(this.fullnessDropdownContainer);
            
            this.lunchDeliciousnessFactorSlider = new HorizontalSlider({
                value: 5,
                minimum: 0,
                maximum: 10,
                intermediateChanges: true,
                style: "width:300px;",
                onChange: function (value) {
                    self.lunchDeliciousnessFactor.innerHTML = value;
                }
            }).placeAt(this.deliciousnessDropdownContainer);
            
            this.lunchDiscomfortFactorSlider = new HorizontalSlider({
                value: 5,
                minimum: 0,
                maximum: 10,
                intermediateChanges: true,
                style: "width:300px;",
                onChange: function (value) {
                    self.lunchDiscomfortFactor.innerHTML = value;
                }
            }).placeAt(this.discomfortDropdownContainer);
            
            this.optInCheckBox = new CheckBox({
                id:"optInContainer",
                name: "optInCheckboxName",
                value: 1,
                checked: false,
                onChange: function(b){
                    console.log('onChange called with parameter = ' + b + ', and widget value = ' + this.get('value') ); 
                }
            }).placeAt(this.optInCheckbox);
            
            
            this.btnCreateLocation = Button({
                id: "createProfileButton",
                name: "createProfile",
                label: "Create Restaurant Profile"
            }).placeAt(this.createProfile);
            //Attach a click event to the button
            on(this.btnCreateLocation, "click", lang.hitch(this, this.insertProfile));
        },

        settingCheckbox: function (widget, checkValue) {
            if (checkValue == 1) {
                widget.set('checked', true);
            } else {
                widget.set('checked', false);
            }
        },


            insertProfile: function () {
            var self = this;

            //Get the input values into variables
            var restaurantId = this.restaurantDropdown.get('value');
            var timeFactor = this.lunchTimeFactorSlider.get('value');
            var costFactor = this.lunchCostFactorSlider.get('value');
            var deliciousnessFactor = this.lunchDeliciousnessFactorSlider.get('value');
            var discomfortFactor = this.lunchDiscomfortFactorSlider.get('value');
            var lunchFullnessFactor = this.lunchFullnessFactorSlider.get('value');
            
            var optIn = this.getCheckboxValue(this.optInCheckBox);
 
            //Post to create the restaurant
            var xhrArgs = {
                url: "/lunchApp/services/insertRestaurantProfiles/",
                postData: dojo.toJson({
                    restaurantId: restaurantId,
                    timeFactor:timeFactor,
                    costFactor:costFactor,
                    postLunchFullnessFactor:lunchFullnessFactor,
                    deliciousnessFactor:deliciousnessFactor,
                    postLunchDiscomfortFactor:discomfortFactor,
                    optIn:optIn
                }),
                handleAs: "json",
                headers: {
                    "Content-Type": "application/json"
                },
                load: function (data) {
                    //DO Stuff after the POST is finished
                    lunchAppGlobal.addLunchRestaurantProfileDialog.hide();
                   
                },
                error: function (error) {
                    //POST ERROR
                }
            };
            // Call the asynchronous xhrPost
            var deferred = dojo.xhrPost(xhrArgs);
        },
        
//          populateDialog: function(data){
//            
//            this.restaurantDropdown.set('value',data.name);
//          
//        },
//            
            settingCheckbox: function(widget,checkValue){
                if(checkValue == 1){
                widget.set('checked',true);
                }else{
                widget.set('checked',false);
              }
            },
        
        
        getCheckboxValue: function (checkboxId) {
            var checkboxValue;
            var isChecked = dijit.byId(checkboxId).checked;
            if (isChecked) {
                checkboxValue = 1;
            } else {
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



           
            
            
            
