/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comresource.lunchapp.resources;

import com.comresource.lunchapp.models.AccessProperties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 *
 * @author LabUser1
 */

@Path("/properties")
public class PropertiesResource {
    
      @GET
      @Produces(MediaType.APPLICATION_JSON)
      public Response getAccessProperties(@Context HttpServletRequest httpRequest) throws Exception{
          
          HttpSession session = httpRequest.getSession(true);
          
          String userName = (String) session.getAttribute("userName");
          
          AccessProperties ap = new AccessProperties();
          
          ap.setUserName(userName);
          
         ResponseBuilder builder = Response.ok(ap);
        if (builder == null) {
            throw new Exception("builder == null");
        }
        CacheControl cacheControl = new CacheControl();
        cacheControl.setNoCache(true);
        return builder.cacheControl(cacheControl).build();
          
          
          
      
      }
}
    
    
    
    
    

