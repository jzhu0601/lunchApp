/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comresource.lunchapp.resources;
import com.comresource.lunchapp.models.Users;
import com.comresource.lunchapp.PersistenceManager;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Jason
 */
@Path("/users")
public class ReadUser {
    
                    final static Logger log = LoggerFactory.getLogger(ReadUser.class);

        
      @GET
      @Produces(MediaType.APPLICATION_JSON)
      public Response getAll() throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();
        ResponseBuilder builder;
        //Get Session to act as a factory for the Criteria Instance
        Session sess = entityManager.unwrap(Session.class);
        //Hibernate Criteria Docs
        //https://docs.jboss.org/hibernate/orm/3.3/reference/en-US/html/querycriteria.html
        //Specify the Model Class to be used in the query
        Criteria crit = sess.createCriteria(Users.class);
        //Query for Results
        Collection<?> results = crit.list();
        //Send Results to user
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
