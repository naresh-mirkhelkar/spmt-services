package ksu.poma.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import ksu.poma.dbutils.CouchClient;
import ksu.poma.dbutils.DocumentClient;
import ksu.poma.dbutils.model.CustomHttpResponse;
import ksu.poma.dbutils.model.DocumentHttpResponse;
import ksu.poma.dbutils.model.DocumentsAllHttpResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("prj-mgmt")
public class ProjectService {

    private final CouchClient couchClient = new CouchClient("http://localhost:5984",5,"admin","password");
    private final DocumentClient documentClient =  new DocumentClient(couchClient,"prj-mgmt", new ObjectMapper());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getProjectInfo/{projectName}")
    public CustomHttpResponse getProjectInformation(@PathParam("projectName") String projectName){
       return documentClient.get(projectName);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllProjects")
    public DocumentsAllHttpResponse getAllProjectInformation(){
        return documentClient.getAll();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deleteProject/id/{id}/rev/{rev}")
    public DocumentHttpResponse deleteProjectByName(@PathParam("id") String id, @PathParam("rev") String rev){
        return documentClient.delete(id,rev);
    }

}
