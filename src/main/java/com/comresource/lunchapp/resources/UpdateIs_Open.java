/*
 * Author: Jason
 */
package com.comresource.lunchapp.resources;

import com.comresource.lunchapp.PersistenceManager;
import com.comresource.lunchapp.models.Is_Open;
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
@Path("/updateIs_Open")
public class UpdateIs_Open {
    
                final static Logger log = LoggerFactory.getLogger(UpdateIs_Open.class);

    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
     public Response create(String openData) throws Exception{
         EntityManager entityManager = PersistenceManager.getEntityManager();
         Response.ResponseBuilder builder = null;
         JSONObject openJSON = (JSONObject) JSONSerializer.toJSON(openData);
         final String openId = (String) openJSON.get("openId");
         String restaurantId = openJSON.getString("restaurantId");
         Integer monday = openJSON.getInt("monday");
         Integer tuesday = openJSON.getInt("tuesday");
         Integer wednesday = openJSON.getInt("wednesday");
         Integer thursday = openJSON.getInt("thursday");
         Integer friday = openJSON.getInt("friday");
         Integer saturday = openJSON.getInt("saturday");
         Integer sunday = openJSON.getInt("sunday");
         
         try {
            entityManager.getTransaction().begin();
            // Find the File in the database
            Is_Open found = entityManager.find(Is_Open.class, openId);
            if (found == null) {
                throw new PersistenceException("openId: " + openId + " not found.");
            }
            // Copy the new value to the existing set
           found.update(monday,tuesday,wednesday,thursday,friday,saturday,sunday);

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
         
         return null;
     }
}
