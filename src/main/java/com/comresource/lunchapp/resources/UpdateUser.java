/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comresource.lunchapp.resources;

import com.comresource.lunchapp.PersistenceManager;
import com.comresource.lunchapp.models.Users;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jason
 */
@Path("/updateUser")
public class UpdateUser {
    
       final static Logger log = LoggerFactory.getLogger(UpdateUser.class);

    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String userData) throws Exception{
         EntityManager entityManager = PersistenceManager.getEntityManager();
         Response.ResponseBuilder builder = null;
         
        JSONObject userJSON = (JSONObject) JSONSerializer.toJSON(userData);
         
        final String userId = (String) userJSON.get("userID");
        String name = userJSON.getString("userName");
        
        try {
            entityManager.getTransaction().begin();
            // Find the File in the database
            Users found = entityManager.find(Users.class, userId);
            if (found == null) {
                throw new PersistenceException("Restaurant with key: " + userId + " not found.");
            }
            // Copy the new value to the existing set
            found.update(userId, name);

            // Persist it
            entityManager.persist(found);
            entityManager.getTransaction().commit();
            entityManager.detach(found);

            builder = Response.ok(found);
            if (builder == null) {
                throw new Exception("builder == null");
            }
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                log.error(e.getMessage());
            }
        }

        if (builder == null) {
            throw new Exception("builder == null");
        }
        CacheControl cacheControl = new CacheControl();
        cacheControl.setNoCache(true);
        return builder.cacheControl(cacheControl).build();
    }
    
}
