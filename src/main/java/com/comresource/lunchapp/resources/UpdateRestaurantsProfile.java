/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comresource.lunchapp.resources;

import com.comresource.lunchapp.PersistenceManager;
import com.comresource.lunchapp.models.RestaurantProfile;
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
 * @author LabUser1
 */
@Path("/updateRestaurantprofile")
public class UpdateRestaurantsProfile {
    
                        final static Logger log = LoggerFactory.getLogger(UpdateRestaurantsProfile.class);

    
      //Insert into Database
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String restaurantProfileData) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();
        Response.ResponseBuilder builder = null;
        //Change the string to a json Object that we can use to extract the values
        JSONObject restaurantProfileJSON = (JSONObject) JSONSerializer.toJSON(restaurantProfileData);

        final String restaurantProfileId = (String) restaurantProfileJSON.get("restaurantProfileId");
        String restaurantId = restaurantProfileJSON.getString("restaurantId");
        String userId = restaurantProfileJSON.getString("userId");
        String timeFactor = restaurantProfileJSON.getString("timeFactor");
        String costFactor = restaurantProfileJSON.getString("costFactor");
        String postLunchFullnessFactor = restaurantProfileJSON.getString("postLunchFullnessFactor");
        String deliciousnessFactor = restaurantProfileJSON.getString("deliciousnessFactor");
        String postLunchDiscomfortFactor = restaurantProfileJSON.getString("postLunchDiscomfortFactor");
        String optIn = restaurantProfileJSON.getString("optIn");

        try {
            entityManager.getTransaction().begin();
            // Find the File in the database
            RestaurantProfile found = entityManager.find(RestaurantProfile.class, restaurantProfileId);
            if (found == null) {
                throw new PersistenceException("Restaurant with key: " + restaurantProfileId + " not found.");
            }
            // Copy the new value to the existing set
            found.update( timeFactor, costFactor, postLunchFullnessFactor, deliciousnessFactor,postLunchDiscomfortFactor,optIn);

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
