package com.comresource.lunchapp.resources;

import com.comresource.lunchapp.PersistenceManager;
import com.comresource.lunchapp.models.Restaurants;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/deleteRestaurants")
public class DeleteRestaurants {
    
         final static Logger log = LoggerFactory.getLogger(DeleteRestaurants.class);


    //Delete From Database
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void create(String restaurant) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();
        ResponseBuilder builder;
        //parse JSON object
        JSONObject restaurantJSON = (JSONObject) JSONSerializer.toJSON(restaurant);
        //GET File ID
        final String restaurantId = (String) restaurantJSON.get("restaurantId");
        try {
            entityManager.getTransaction().begin();
            // Find the File in the database
            Restaurants found = entityManager.find(Restaurants.class, restaurantId);
            if (found == null) {
                throw new PersistenceException("Restaurant with key: " + restaurantId + " not found.");
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
