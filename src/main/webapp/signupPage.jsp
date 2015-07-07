<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ page import="java.util.Properties,java.io.InputStream,java.io.FileNotFoundException" %>
<%@ page import="javax.servlet.http.HttpSession"%>
<%
   
//    HttpSession currentSession = request.getSession();
//    String authenticated = (String) currentSession.getAttribute("authenticated");
    //Flex 1.2
    //check if session exists
//    if (authenticated != null) {
//        //No session exists so redirect to login page
//        response.sendRedirect("/SFG_Tool");
//    }
%>
<%-- <%@ taglib uri='http://www.stercomm.com/uix/security' prefix='sec' %> --%>
<%

    session.setAttribute("vendorEmail", "");
    String message = "";
    String title = request.getParameter("title");

    String loginHeader = "";
    if (title == null) {
        title = "Please Sign up";
    }
%>

<html>
    <head>
        <title>Sign Up</title>
        <link rel="stylesheet" href="js/dijit/themes/claro/claro.css" />
        <link rel="stylesheet" href="css/style.css" />
        <link rel="shortcut icon" type="image/ico" href="media/favicon.ico" />
        <style>
            body{
                height: 100%;
                width: 100%;
                border: 0;
                padding: 0;
                margin: 0;
                background: #a6a6a6;
            }
            .mainWrapper{
                display: inline-block;
                position: fixed;
                top: 0;
                bottom: 0;
                left: 0;
                right: 0;
                margin: auto;		

                /*margin: 0 auto;*/
                padding: 20px 20px 20px;
                width: 310px;
                height:200px;
                background: white;
                border-radius: 3px;
                -webkit-box-shadow: 0 0 200px rgba(255, 255, 255, 0.5), 0 1px 2px rgba(0, 0, 0, 0.3);
                box-shadow: 0 0 200px rgba(255, 255, 255, 0.5), 0 1px 2px rgba(0, 0, 0, 0.3);
            }
            .enviromentHeader{
                font-size: 28px;
                font-weight: bolder;
                text-align: center;
                margin-bottom: 5px;
            }

            .loginHeaderImage{
                margin-left: auto;
                margin-right: auto;
                display: table;
            }

            #autoLogin{
                margin: 0px !important;
            }

            #signupButtonContainer{
                display: table;
                margin-left: auto;
                margin-right: auto;
            }
            #signupButton{
                font-size:16px !important;
            }

            .signupButtonWidth  span.dijitButtonNode {
                width:100px;
                margin:15px;
            }

            .line{
                width:98%;
                margin-top: 5px;
                margin-bottom: 20px;
            }

            .textLabel{
                width:100px;
            }
            .textInput{
                width: 95%;
                font-size: 24px;
                height: 30px;
                border-radius: 3px;
                padding: 5px;
                margin-bottom: 10px;
                margin-top: 10px;
            }	
            
            .errorStyle{
                color: #952B33;
            }

            .dijitButton .dijitButtonNode, .dijitDropDownButton .dijitButtonNode, .dijitComboButton .dijitButtonNode, .dijitToggleButton .dijitButtonNode {
                border: 2px solid gray;
                margin: 5px !important;
                padding: 4px 10px 6px 8px;
                background: #001140;
                color: #ffffff;
                text-shadow: 1px 1px 1px #333333;
                -moz-border-radius: 2px;
                border-radius: 5px;
                -webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.15);
                -moz-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.15);
                box-shadow: 0 1px 1px rgba(0, 0, 0, 0.15);
            }
        </style>

        <script type="text/javascript" src="js/dojo/dojo.js" data-dojo-config="async: true"></script>
    </head>
    <body>

        <div class="mainWrapper" >
            <div class='enviromentHeader'>
                User Sign Up <%= loginHeader%>
            </div>

            <form id='userSignup' method="post" target="_blank">

                <div id='userNameSignup'></div>
                <div class="errorStyle" id="signupNameError"></div>
                <div id='passwordSignup'></div>

                <input name="action" type="hidden" value="SignupUser">
                
                
              
            </form> 
            
                <div class="errorStyle" id="signupError"></div>

            <div id='signupButtonContainer' class="signupButtonWidth"></div>
        </div>

        <script type="text/javascript">
            require({
                baseUrl: "/",
                packages: [
                    {name: "dojo", location: "../js/dojo"},
                    {name: "dijit", location: "../js/dijit"},
                    {name: "dojox", location: "../js/dojox"}
                ]
            });
            require([
                "dojo/dom",
                "dojo/dom-construct",
                "dojo/io-query",
                "dojo/on",
                "dijit/form/ValidationTextBox",
                "dijit/form/Button",
                "dojo/query",
                "dojo/keys",
                "dojo/request/iframe",
                "dojo/_base/fx",
                "dojo/fx/easing",
                "dijit/_WidgetBase",
                "dijit/_TemplatedMixin",                
                "dojo/domReady!"],
                    function (dom, domConstruct, ioQuery, on, ValidationTextBox, Button, query, keys, iframe,baseFx,easing) {
                        var self = this;

                        //Variables
                        //var submitButton = dom.byId('loginSubmit');
                        var submitButton = dom.byId('signupButtonContainer');
                        var usernameTextbox = dom.byId('userNameSignup');
                        var passwordTextbox = dom.byId('passwordSignup');

                        //UserName Textbox
                        this.userNameTextbox = new ValidationTextBox({
                            'class': 'textInput',
                            name: 'username',
                             regExp: ".+",
                            placeHolder: "Enter Username"
                        }, "text").placeAt(usernameTextbox);

                        //Password Textbox
                        this.passwordTextbox = new ValidationTextBox({
                            'class': 'textInput',
                            name: 'password',
                            type: 'password',
                            regExp: ".+",
                            placeHolder: "Enter Password"
                        }, "password").placeAt(passwordTextbox);

                        //Save-Create Button for Entity
                        this.SignupButtonBase = Button({
                            id: "signupButton",
                            name: "SignupUser",
                            label: "Signup"
                        }).placeAt(submitButton);
                        //Save Click Event 
                        on(this.SignupButtonBase, "click", function () {
                            self.SignupUser();
                        });

                        query("input[type='password']").on("keydown", function (event) {
                            //query returns a nodelist, which has an on() function available that will listen
                            //to all keydown events for each node in the list
                            switch (event.keyCode) {
                                case keys.ENTER:
                                    self.SignupUser();
                                    break;
                            }
                        });

                        this.SignupUser = function () {
                            //Get Input Values
                            var username = self.userNameTextbox.get('value');
                            var password = self.passwordTextbox.get('value');

                            if (username !== '' && password !== '') {
                                var xhrArgs = {
                                    url: "/lunchApp/services/insertuser",
                                    postData: dojo.toJson({userName: username, password: password}),
                                    handleAs: "text",
                                    headers: {
                                        "Content-Type": "application/json"
                                    },
                                    load: function (data) {
                                        //Revert border styling                            
                                        loginUser();
                                    },
                                    error: function (error) {
                                        // We'll 404 in the demo, but that's okay.  We don't have a 'postIt' service on the
                                        // docs server.

                                        var errorNode = dom.byId("signupNameError");
                                        errorNode.innerHTML = "Try with a Different NAME...:)";

                                        var errorNode = dom.byId("signupError");
                                        errorNode.innerHTML = "Error Creating User.!!!.User may Already Exist!!!";

                                    }
                                };
                                // Call the asynchronous xhrPost
                                var deferred = dojo.xhrPost(xhrArgs);
                            }
                        };

                        var loginUser = function () {
                            //Get Input Values
                            var username = self.userNameTextbox.get('value');
                            var password = self.passwordTextbox.get('value');

                            if (username !== '' && password !== '') {
                                var xhrArgs = {
                                    url: "/lunchApp/services/checkAuthentication",
                                    postData: dojo.toJson({userName: username, password: password}),
                                    handleAs: "text",
                                    headers: {
                                        "Content-Type": "application/json"
                                    },
                                    load: function (data) {


                                        if (data === 'failed') {
                                            setInterval(function () {
                                                window.location.href = "/lunchApp";
                                            }, 500);
                                        } else {
                                            window.location.href = "/lunchApp";
                                        }
                                    },
                                    error: function (error) {
                                        // We'll 404 in the demo, but that's okay.  We don't have a 'postIt' service on the

                                    }
                                };
                                // Call the asynchronous xhrPost
                                var deferred = dojo.xhrPost(xhrArgs);
                            }
                        };
                    }
            );

        </script>

    </body>
</html>
