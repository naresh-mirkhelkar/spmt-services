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

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Objects;

@Path("prj-mgmt")
public class ProjectService {

//    private CouchClient couchClient = new CouchClient("http://localhost:5984",5,"admin","password");
    private CouchClient couchClient = null;
//    private final DocumentClient documentClient =  new DocumentClient(couchClient,"prj-mgmt", new ObjectMapper());
    private  DocumentClient documentClient = null;
    Logger logger = LoggerFactory.getLogger(ProjectService.class);
    private String url = "http://%s:5984";

    public ProjectService(){
        String hostName = System.getenv("HOST");
        if (Objects.nonNull(hostName) && !hostName.trim().isEmpty())
            url = String.format(url,hostName);
        else
            url = String.format(url,"localhost");
        logger.info("Using host name as " + url);
        couchClient = new CouchClient(url,5,"admin","password");
        documentClient =  new DocumentClient(couchClient,"prj-mgmt", new ObjectMapper());
    }

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

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("ishealthy")
    public String isHealthy(){
        return "up and running!";
    }

}
