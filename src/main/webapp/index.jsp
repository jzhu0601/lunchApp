<!DOCTYPE html>
<%@page import="javax.servlet.http.HttpSession"%>
<%
    HttpSession currentSession = request.getSession();
    String authenticated = (String) currentSession.getAttribute("authenticated");
    if (authenticated == null) {
        response.sendRedirect("/lunchApp/loginPage.jsp");
    }
%>
<html>
    <head>
        <meta charset="utf-8">
        <title>Lunch App Boilerplate</title>
        <!-- Application-specific CSS should be stored in your application?s package to ensure portability and to allow
             the build system to combine & minify it. -->
        <link rel="stylesheet" href="css/style.css">
        <link rel="shortcut icon" type="image/ico" href="media/favicon.ico" />
    </head>
    <!-- The claro class configures the theme for the application --> 
    <!--http://dojotoolkit.org/reference-guide/1.10/dijit/themes.html-->
    <body class="claro">
        <!--Container for the Application-->
        <div class="lunchAppContainer">
            <div id="mainContainer"></div>
        </div>

        <!-- dojo.js is an AMD-compliant loader script. It accepts configuration either from a data-dojo-config attribute
            on the dojo.js script tag, from a global dojoConfig object (shown here), or from a global require object.
            Configuration options for the loader can be viewed at http://dojotoolkit.org/reference-guide/loader/amd.html
            along with a bunch of additional information about its features.
        -->

        <script>


            var dojoConfig = {
                // Enable the AMD loader
                async: true,
                // Define the base URL for all of our modules and packages
                baseUrl: '.',
                // Enable debugging
                isDebug: true,
                // Register the packages we are going to be using. 
                packages: [
                    {name: "dojo", location: "js/dojo"},
                    {name: "dijit", location: "js/dijit"},
                    {name: "dojox", location: "js/dojox"},
                    {name: "dgrid", location: "js/dgrid"},
                    {name: "xstyle", location: "js/xstyle"},
                    {name: "put-selector", location: "js/put-selector"},
                    {name: "lunchApp", location: "js/lunchApp"}
                ],
                // Use the smaller, faster "lite" CSS selector engine, which works in all browsers IE8+
                selectorEngine: 'lite',
                // Fix the loader to use normal AMD resolution of unregistered module paths (relative to baseUrl)
                // instead of the legacy Dojo resolution method (relative to the parent directory of baseUrl)
                tlmSiblingOfDojo: false
            };

        </script>
        <script src="https://maps.googleapis.com/maps/api/js"></script>
        <script>
                function initialize() {
                    var mapCanvas = document.getElementById('map-canvas');
                    var mapOptions = {
                        center: new google.maps.LatLng(39.973816, -83.049663),
                        zoom: 10,
                        mapTypeId: google.maps.MapTypeId.ROADMAP
                    }
                    var map = new google.maps.Map(mapCanvas, mapOptions)
                }
                google.maps.event.addDomListener(window, 'load', initialize);
        </script>
        <script src="js/dojo/dojo.js"></script>
        <script>
            var lunchAppGlobal = {};
            require(['js/lunchApp/main']);
        </script>
    </body>
</html>