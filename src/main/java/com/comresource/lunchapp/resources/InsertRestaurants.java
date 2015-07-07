/*
 * Jason
 */
package com.comresource.lunchapp.resources;

import com.comresource.lunchapp.PersistenceManager;
import com.comresource.lunchapp.models.Is_Open;
import com.comresource.lunchapp.models.Restaurants;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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

@Path("/insertRestaurants")
public class InsertRestaurants {
    
    final static Logger log = LoggerFactory.getLogger(InsertRestaurants.class);
    
    
    //POST METHOD
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String restaurantData) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();
        ResponseBuilder builder = null;
      
        JSONObject restaurantJSON = (JSONObject) JSONSerializer.toJSON(restaurantData);
        
        Restaurants res = new Restaurants();
        final String restaurantId = UUID.randomUUID().toString();
        String name = restaurantJSON.getString("name");
        String city = restaurantJSON.getString("city");
        String state = restaurantJSON.getString("state");
        String address = restaurantJSON.getString("address");
        String zip = restaurantJSON.getString("zip");
        String website = restaurantJSON.getString("website");
        res.setRestaurantId(restaurantId);
        res.setName(name);
        res.setCity(city);
        res.setState(state);
        res.setAddress(address);
        res.setZip(zip);
        res.setWebsite(website);
        
        
        
        //Catch any insert errors and roll back the transaction
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            //Insert the Restaurant Instance 
            //http://docs.oracle.com/javaee/6/api/javax/persistence/EntityManager.html#persist(java.lang.Object)
            entityManager.persist(res);
            //http://docs.oracle.com/javaee/6/api/javax/persistence/EntityTransaction.html#commit()
            transaction.commit();
            //Remove the given entity from the persistence context
            //http://docs.oracle.com/javaee/6/api/javax/persistence/EntityManager.html#detach(java.lang.Object)
            entityManager.detach(res);
            builder = Response.ok(res);
            if (builder == null) {
                throw new Exception("builder == null");
            }
            
            EntityTransaction transactionIsOpen = entityManager.getTransaction();
            transactionIsOpen.begin();
            //Create new instance of IS_OPEN 
            Is_Open isOpen = new Is_Open();
            
            //Generate GUID
            final String openId = UUID.randomUUID().toString();
            String restaurantId_FK = restaurantId;
            String monday = restaurantJSON.getString("monday");
            String tuesday = restaurantJSON.getString("tuesday");
            String wednesday = restaurantJSON.getString("wednesday");
            String thursday = restaurantJSON.getString("thursday");
            String friday = restaurantJSON.getString("friday");
            String saturday = restaurantJSON.getString("saturday");
            String sunday = restaurantJSON.getString("sunday");
            
            //Add Values to Is_Open
            isOpen.setOpenId(openId);
            isOpen.setRestaurantId(restaurantId_FK);
            isOpen.setTuesday(Integer.parseInt(tuesday));
            isOpen.setWednesday(Integer.parseInt(wednesday));
            isOpen.setThursday(Integer.parseInt(thursday));
            isOpen.setFriday(Integer.parseInt(friday));
            isOpen.setSaturday(Integer.parseInt(saturday));
            isOpen.setSunday(Integer.parseInt(sunday));
            isOpen.setMonday(Integer.parseInt(monday));
            
           
            entityManager.persist(isOpen);
            
            transactionIsOpen.commit();
            
            entityManager.detach(isOpen);
       
            
            
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                log.error(e.getMessage());
            }
        }
        CacheControl cacheControl = new CacheControl();
        cacheControl.setNoCache(true);

        if (builder == null) {
            throw new Exception("builder == null");
        }
        return builder.cacheControl(cacheControl).build();
    }
}
