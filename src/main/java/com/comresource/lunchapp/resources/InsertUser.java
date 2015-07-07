
package com.comresource.lunchapp.resources;

import com.comresource.lunchapp.PersistenceManager;
import com.comresource.lunchapp.models.Users;
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
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/insertuser")
public class InsertUser {
    
    final static Logger log = LoggerFactory.getLogger(InsertRestaurants.class);
    
    
    //POST METHOD
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String userData) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();
        ResponseBuilder builder = null;
      
        JSONObject userJSON = (JSONObject) JSONSerializer.toJSON(userData);
        
        
        Users us = new Users();
        final String userID = UUID.randomUUID().toString();
        String userName = userJSON.getString("userName");
        String password = userJSON.getString("password");
         String clientSalt = DigestUtils.sha256Hex(userName);
        String clientLoginPasswordHash = DigestUtils.sha256Hex(password + clientSalt);

       
        us.setUserID(userID);
        us.setUserName(userName);
        us.setPassword(clientLoginPasswordHash);
        us.setSALT(clientSalt);
       
         try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            //Insert the Restaurant Instance 
            //http://docs.oracle.com/javaee/6/api/javax/persistence/EntityManager.html#persist(java.lang.Object)
            entityManager.persist(us);
            //http://docs.oracle.com/javaee/6/api/javax/persistence/EntityTransaction.html#commit()
            transaction.commit();
            //Remove the given entity from the persistence context
            //http://docs.oracle.com/javaee/6/api/javax/persistence/EntityManager.html#detach(java.lang.Object)
            entityManager.detach(us);
            builder = Response.ok(us);
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

        if (builder == null) {
            throw new Exception("builder == null");
        }
        return builder.cacheControl(cacheControl).build();
    }
        
        
      
           
}
