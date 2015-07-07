package com.comresource.lunchapp.resources;

import com.comresource.lunchapp.PersistenceManager;
import com.comresource.lunchapp.models.Users;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/checkAuthentication")
public class CheckAuthentication {
    
    final static Logger log = LoggerFactory.getLogger(CheckAuthentication.class);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String go(String credentials, @Context HttpServletRequest httpRequest, @Context HttpServletResponse httpResponse) throws Exception {

        //Check if already logged in
        //Create a Session
        HttpSession session = httpRequest.getSession(true);
        String authenticated = (String) session.getAttribute("authenticated");

        String authResult = "passed";
        if (authenticated == null || authenticated.equals("false")) { //Not Authenticated
            //Get Username and password 
            //parse JSON object

            JSONObject openJSON = (JSONObject) JSONSerializer.toJSON(credentials);
            String loginUserName = (String) openJSON.get("userName");
            String clientPassword = openJSON.getString("password");

            if (loginUserName != null) {
                authResult = Authentication(loginUserName, clientPassword, session, httpResponse);
                log.info("storing authentication result in authResult");
            } else {
                //redirect to index page
                RequestDispatcher page = httpRequest.getRequestDispatcher("loginPage.jsp");
                page.forward(httpRequest, httpResponse);
            }
        }
        return authResult;
        
    }

    public String Authentication(String loginUserName, String clientPassword, HttpSession session, HttpServletResponse httpResponse) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();
        //Get Session to act as a factory for the Criteria Instance
        Session sess = entityManager.unwrap(Session.class);
        Criteria crit = sess.createCriteria(Users.class).add(Restrictions.eq("userName", loginUserName));
        //Query for Results
        List results = crit.list();
        Users currentUser = (Users) results.get(0);
        
        String currentUserId = currentUser.getUserID();

        if (results != null && !results.isEmpty()) {
            // disconnect the entity manager
            entityManager.detach(results);
            log.info("checking for authentication");

            // get the row from the DB and turn it into the User object
            Users userObj = (Users) results.get(0);
            String serverPassword = userObj.getPassword();
            boolean checkHashResult = checkHashPassword(serverPassword, loginUserName, clientPassword);
            if (checkHashResult) {
                session.setAttribute("authenticated", "true");
                session.setAttribute("userName", loginUserName);
                session.setAttribute("userID", currentUserId);
                return "passed";
                
            }
        }
        return "failed";
    }

    public static String checkAuthenticatedSession(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
        //get the session variable
        HttpSession session = httpRequest.getSession(true);
        String authenticated = (String) session.getAttribute("authenticated");
        String authed = "false";
        if ("false".equals(authenticated) || authenticated == null) {
            //redirect to login page
            httpResponse.sendRedirect("loginPage.jsp");
        } else {
            authed = "true";
        }
        return authed;
    }

    public boolean checkHashPassword(String serverPasswordHash, String clientLoginUserName, String clientLoginPassword) {
            log.info("in CheckHashPassword function");
        //hash user entered password and username
        String clientSalt = DigestUtils.sha256Hex(clientLoginUserName);
        String clientLoginPasswordHash = DigestUtils.sha256Hex(clientLoginPassword + clientSalt);

        //return true if server's password hash is equal to user entered password hash
        return serverPasswordHash.equals(clientLoginPasswordHash);
    }
    
}
