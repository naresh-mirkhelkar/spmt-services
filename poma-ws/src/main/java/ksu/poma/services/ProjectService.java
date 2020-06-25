package ksu.poma.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ksu.poma.dbutils.CouchClient;
import ksu.poma.dbutils.DocumentClient;
import ksu.poma.dbutils.model.CustomHttpResponse;
import ksu.poma.dbutils.model.DocumentHttpResponse;
import ksu.poma.dbutils.model.DocumentsAllHttpResponse;
import ksu.poma.model.ProjectInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("prj-mgmt")
public class ProjectService {

    private final CouchClient couchClient = new CouchClient("http://localhost:5984",5,"admin","password");
    private final DocumentClient documentClient =  new DocumentClient(couchClient,"prj-mgmt", new ObjectMapper());
    Logger logger = LoggerFactory.getLogger(ProjectService.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/{projectName}")
    public String getProjectInformation(@PathParam("projectName") String projectName){
        CustomHttpResponse customHttpResponse = documentClient.get(projectName);
        return customHttpResponse.getContent();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAll")
    public DocumentsAllHttpResponse getAllProjectInformation(){
        return documentClient.getAll();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/id/{id}/rev/{rev}")
    public DocumentHttpResponse deleteProjectByName(@PathParam("id") String id, @PathParam("rev") String rev){
        return documentClient.delete(id,rev);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public DocumentHttpResponse createProjectByName(String projectInfJson){
        ObjectMapper objectMapper = new ObjectMapper();
        DocumentHttpResponse documentHttpResponse = new DocumentHttpResponse();
        try {
            ProjectInfo projectInfo = objectMapper.readerFor(ProjectInfo.class).readValue(projectInfJson);
            documentHttpResponse = documentClient.create(projectInfo);
        } catch (JsonProcessingException jpe) {
            logger.error(jpe.getMessage());
            return new DocumentHttpResponse();
        }catch (Exception ex){
            logger.error(ex.getMessage());
        }
        return  documentHttpResponse;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update")
    public DocumentHttpResponse updateProjectByName(String projectInfJson){
        ObjectMapper objectMapper = new ObjectMapper();
        DocumentHttpResponse documentHttpResponse = new DocumentHttpResponse();
        try {
            ProjectInfo projectInfo = objectMapper.readerFor(ProjectInfo.class).readValue(projectInfJson);
            documentHttpResponse = documentClient.update(projectInfo);
        } catch (JsonProcessingException jpe) {
            logger.error(jpe.getMessage());
        }catch (Exception ex){
            logger.error(ex.getMessage());
        }
        return  documentHttpResponse;
    }

}
