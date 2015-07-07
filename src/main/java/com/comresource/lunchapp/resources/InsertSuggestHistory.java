package com.comresource.lunchapp.resources;

import com.comresource.lunchapp.PersistenceManager;
import com.comresource.lunchapp.models.SuggestHistory;
import java.util.Date;
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

@Path("/insertSuggestHistory")
public class InsertSuggestHistory {

    final static Logger log = LoggerFactory.getLogger(InsertSuggestHistory.class);

    //POST METHOD
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String suggestData) throws Exception {
        EntityManager entityManager = PersistenceManager.getEntityManager();
        ResponseBuilder builder = null;

        JSONObject suggestJSON = (JSONObject) JSONSerializer.toJSON(suggestData);

        SuggestHistory s = new SuggestHistory();
        final String id = UUID.randomUUID().toString();
        s.setSuggestId(id);

        String restaurantId = suggestJSON.getString("restaurantId");
        s.setRestaurantId(restaurantId);
        s.setSuggestDate(new Date());

        //Catch any insert errors and roll back the transaction
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(s);
            transaction.commit();
            entityManager.detach(s);
            builder = Response.ok(s);
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
