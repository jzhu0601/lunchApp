package com.comresource.lunchapp.resources;

import com.comresource.lunchapp.PersistenceManager;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import org.hibernate.Query;
import org.hibernate.Session;

@Path("/restaurantgraphinfo")
public class RestaurantGraphInfo {

    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getAll(@Context HttpServletRequest httpRequest) throws Exception {

        String user = httpRequest.getSession(true).getAttribute("userName").toString();
        EntityManager entityManager = PersistenceManager.getEntityManager();
        Session sess = entityManager.unwrap(Session.class);

        Query query = sess.getNamedQuery("suggestionForUser");
        
        // Setting the user name in the query twice, It's not a mistake
        query.setParameter(0, user);
        query.setParameter(1, user);
        List results = query.list();

        Response.ResponseBuilder builder = Response.ok(results);
        if (builder == null) {
            throw new Exception("builder == null");
        }
        CacheControl cacheControl = new CacheControl();
        cacheControl.setNoCache(true);
        return builder.cacheControl(cacheControl).build();
    }
}
