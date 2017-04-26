package com.ontotext.parenttree.resource;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.ontotext.parenttree.service.ParentTreeService;
import com.ontotext.parenttree.sesamerepo.SesameRepo;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.ontotext.parenttree.resource.ParentTreeResource.ID_FOUR;
import static com.ontotext.parenttree.resource.ParentTreeResource.ID_THREE;
import static com.ontotext.parenttree.service.ParentTreeServiceTest.ID_ONE;
import static com.ontotext.parenttree.service.ParentTreeServiceTest.ID_TWO;
import static com.ontotext.parenttree.util.ResourceUtil.getResourceFileAsString;
import static org.assertj.core.api.Assertions.assertThat;

/** **/
public class ParentTreeResourceTest {


    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080); // No-args constructor defaults to port 8080

    SesameRepo sesameRepo = new SesameRepo("http://localhost:8080", "parent-tree");
    ParentTreeService parentTreeService = new ParentTreeService(sesameRepo);

    @Rule
    public final ResourceTestRule RULE = ResourceTestRule.builder()
            .addResource(new ParentTreeResource(this.parentTreeService))
            .build();

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() throws Exception {
    }

    // GET /parenttree?{conceptId}
    @Test
    public void getParentTree() throws Exception {

        stubFor(get(urlMatching("/protocol"))
                .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "text/plain;charset=UTF-8")
                                .withBody("8")));

        stubFor(post(urlMatching("/repositories/parent-tree"))
                .withHeader("Accept", equalTo("application/n-triples;q=0.7, text/plain;q=0.7, application/x-binary-rdf"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/n-triples;charset=UTF-8")
                        .withBody(getResourceFileAsString("instance-data/mock-model.ttl"))));

        URI uri = UriBuilder.fromPath("/parenttree").queryParam("conceptId", ID_THREE).queryParam("conceptId", ID_FOUR).build();

        final Response response = RULE.client().target("/parenttree")
                .queryParam("conceptId", ID_ONE).queryParam("conceptId", ID_TWO)
                .request()
                .get();

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        String responseStr = response.readEntity(String.class);
        System.out.println("Status: " + response.getStatusInfo());
        System.out.println("Response: " + responseStr);
    }
}