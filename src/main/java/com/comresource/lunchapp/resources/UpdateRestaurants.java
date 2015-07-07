package com.comresource.lunchapp.resources;

import com.comresource.lunchapp.PersistenceManager;
import com.comresource.lunchapp.models.Restaurants;
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
import javax.ws.rs.core.Response.ResponseBuilder;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/updateRestaurant")
public class UpdateRestaurants {
    
                    final static Logger log = LoggerFactory.getLogger(UpdateRestaurants.class);


    //Insert into Database
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String restaurantData) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();
        ResponseBuilder builder = null;
        //Change the string to a json Object that we can use to extract the values
        JSONObject restaurantJSON = (JSONObject) JSONSerializer.toJSON(restaurantData);
        

        final String restaurantId = (String) restaurantJSON.get("restaurantId");
        String name = restaurantJSON.getString("name");
        String city = restaurantJSON.getString("city");
        String state = restaurantJSON.getString("state");
        String address = restaurantJSON.getString("address");
        String zip = restaurantJSON.getString("zip");
        String website = restaurantJSON.getString("website");
        final String openId = (String) restaurantJSON.get("openId");
         
         Integer monday = restaurantJSON.getInt("monday");
         Integer tuesday = restaurantJSON.getInt("tuesday");
         Integer wednesday = restaurantJSON.getInt("wednesday");
         Integer thursday = restaurantJSON.getInt("thursday");
         Integer friday = restaurantJSON.getInt("friday");
         Integer saturday = restaurantJSON.getInt("saturday");
         Integer sunday = restaurantJSON.getInt("sunday");
         
        
      log.info("in update restaurants class");

        try {
            entityManager.getTransaction().begin();
            // Find the File in the database
            Restaurants found = entityManager.find(Restaurants.class, restaurantId);
            
            if (found == null) {
                throw new PersistenceException("Restaurant with key: " + restaurantId + " not found.");
            }
            // Copy the new value to the existing set
            found.update(name, city, state, address, zip, website);
          

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
                log.info("just updating name,city,state,address,zip and website");
                log.error(e.getMessage());
            }
        }
        
         try {
            entityManager.getTransaction().begin();
            // Find the File in the database
            Is_Open got = entityManager.find(Is_Open.class, openId);
            
            if (got == null) {
                throw new PersistenceException("openId: " + openId + " not got/found.");
            }
            // Copy the new value to the existing set
          got.update(monday,tuesday,wednesday,thursday,friday,saturday,sunday);
          

            // Persist it
            entityManager.persist(got);
            entityManager.getTransaction().commit();
            entityManager.detach(got);

            builder = Response.ok(got);
            if (builder == null) {
                throw new Exception("builder == null");
            }
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                log.error("updating checkboxes");
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
