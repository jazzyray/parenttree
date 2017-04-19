package com.ontotext.parenttree.resource;

import com.codahale.metrics.annotation.Timed;
import com.ontotext.parenttree.model.Tree;
import com.ontotext.parenttree.service.ParentTreeService;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.net.HttpURLConnection;
import java.util.List;

/** **/
@Api("Parent Tree API")
@Path("/parenttree")
public class ParentTreeResource {

    ParentTreeService parentTreeService;

    public ParentTreeResource(ParentTreeService parentTreeService) {
        this.parentTreeService = parentTreeService;
    }

    // GET /parenttree
    @GET
    @Timed
    // @Produces({CesDocumentMediaType.GENERIC_DOCUMENT_JSON_VALUE, CesDocumentMediaType.GENERIC_DOCUMENT_XML_VALUE})
    @ApiOperation(value = "Get Parent Tree for a set of concepts", response = Tree.class)
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Invalid Concept Ids supplied"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Concept Ids not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Success",
                    responseHeaders = {@ResponseHeader(name = "X-Cache", description = "Explains whether or not a cache was used", response = Boolean.class),
                            @ResponseHeader(name = HttpHeaders.VARY, description = "Make sure proxies cache by Vary", response = String.class),
                            @ResponseHeader(name = HttpHeaders.ETAG, description = "Annotated Content ETag for cache control", response = String.class),
                            @ResponseHeader(name = HttpHeaders.ALLOW, description = "CORS Allowed Methods", response = String.class)})})
    public Response getAnnotatedContent(@ApiParam(value = "Concept Ids", required = true) @QueryParam("conceptId") List<String> conceptIds,
                                        @ApiParam(value = "Transaction Id", required = false) @HeaderParam("X-Request-ID") String transactionId) {

        return Response.status(HttpURLConnection.HTTP_OK).entity(parentTreeService.getParentTree(conceptIds)).build();
    }

}
