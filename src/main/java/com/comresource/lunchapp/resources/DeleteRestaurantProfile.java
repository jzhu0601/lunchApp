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
 * @author Rob
 */

@Path("/deleteRestaurantsProfile")
public class DeleteRestaurantProfile {
    
     final static Logger log = LoggerFactory.getLogger(DeleteRestaurantProfile.class);

    //Delete From Database
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void create(String restaurantProfile) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();
        Response.ResponseBuilder builder;
        //parse JSON object
        JSONObject restaurantProfileJSON = (JSONObject) JSONSerializer.toJSON(restaurantProfile);
        //GET File ID
        final String restaurantProfileId = (String) restaurantProfileJSON.get("restaurantsProfileId");
        try {
            entityManager.getTransaction().begin();
            // Find the File in the database
            RestaurantProfile found = entityManager.find(RestaurantProfile.class, restaurantProfileId);
            if (found == null) {
                throw new PersistenceException("Restaurant with key: " + restaurantProfileId + " not found.");
            }
            // Delete Row
            entityManager.remove(found);
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

        CacheControl cacheControl = new CacheControl();
        cacheControl.setNoCache(true);
    }
}