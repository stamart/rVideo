/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.orangeretail.gofiber.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
 
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.message.internal.ReaderWriter;
import org.slf4j.LoggerFactory;
 
public class CustomLoggingFilter extends LoggingFeature implements ContainerRequestFilter, ContainerResponseFilter {

    final transient static org.slf4j.Logger logger = LoggerFactory.getLogger(CustomLoggingFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext)  throws IOException 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("User: ").append(requestContext.getSecurityContext().getUserPrincipal() == null ? "unknown"
                        : requestContext.getSecurityContext().getUserPrincipal());
        sb.append(" - Path: ").append(requestContext.getUriInfo().getPath());
        sb.append(" - Header: ").append(requestContext.getHeaders());
        sb.append(" - Entity: ").append(getEntityBody(requestContext));
        System.out.println("HTTP     REQUEST : " + sb.toString());
    }
 
    public String getEntityBody(ContainerRequestContext requestContext) 
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = requestContext.getEntityStream();
         
        final StringBuilder b = new StringBuilder();
        try
        {
            ReaderWriter.writeTo(in, out);
 
            byte[] requestEntity = out.toByteArray();
            if (requestEntity.length == 0)
            {
                b.append("").append("\n");
            }
            else
            {
                b.append(new String(requestEntity)).append("\n");
            }
            requestContext.setEntityStream( new ByteArrayInputStream(requestEntity) );
 
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
            //Handle logging error
        }
        return b.toString();
    }
 
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Header: ").append(responseContext.getHeaders());
        sb.append(" - Entity: ").append(responseContext.getEntity());
        System.out.println("HTTP RESPONSE : " + sb.toString());
    }
}