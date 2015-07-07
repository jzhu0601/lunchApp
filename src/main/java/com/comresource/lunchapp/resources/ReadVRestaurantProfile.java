/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comresource.lunchapp.resources;
import com.comresource.lunchapp.PersistenceManager;
import com.comresource.lunchapp.models.Users;
import com.comresource.lunchapp.models.VRestaurantProfile;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author LabUser1
 */
@Path("/vreadrestaurantprofile")
public class ReadVRestaurantProfile {
    
        final static Logger log = LoggerFactory.getLogger(ReadVRestaurantProfile.class);

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@Context HttpServletRequest httpRequest) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();
        HttpSession session = httpRequest.getSession(true);
        String userName = session.getAttribute("userName").toString();
        Response.ResponseBuilder builder;
        Session sess = entityManager.unwrap(Session.class);
       // Criteria crit = sess.createCriteria(Users.class).add(Restrictions.eq("USER_ID", userId));
        Criteria crit = sess.createCriteria(VRestaurantProfile.class).add(Restrictions.eq("userName", userName));
        Collection<?> results = crit.list();
        if (results != null) {
            entityManager.detach(results);
        }
        builder = Response.ok(results);
        if (builder == null) {
             
            throw new Exception("builder == null");
           
        }
        CacheControl cacheControl = new CacheControl();
        cacheControl.setNoCache(true);
        return builder.cacheControl(cacheControl).build();
        
    }
}
