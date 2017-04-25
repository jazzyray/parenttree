package com.ontotext.parenttree;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.ontotext.parenttree.configuration.ParentTreeConfiguration;
import com.ontotext.parenttree.health.ParentTreeHealthCheck;
import com.ontotext.parenttree.resource.ParentTreeResource;
import com.ontotext.parenttree.service.ParentTreeService;
import com.ontotext.parenttree.sesamerepo.SesameRepo;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.ontotext.parenttree.util.ResourceUtil.getResourceFileAsString;

public class WireMockedParentTreeApplication  extends Application<ParentTreeConfiguration> {

    ParentTreeService parentTreeService;

    @Override
    public void run(ParentTreeConfiguration parentTreeConfiguration, Environment environment) throws Exception {
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(8080));
        wireMockServer.start();

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



        this.parentTreeService = new ParentTreeService(new SesameRepo(parentTreeConfiguration.sesameRepoURL, parentTreeConfiguration.repoID));

        final ParentTreeResource resource = new ParentTreeResource(this.parentTreeService);

        final ParentTreeHealthCheck healthCheck = new ParentTreeHealthCheck();
        environment.healthChecks().register("parenttree", healthCheck);

        environment.jersey().register(resource);

    }

    @Override
    public void initialize(Bootstrap<ParentTreeConfiguration> bootstrap) {

        bootstrap.addBundle(new SwaggerBundle<ParentTreeConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(ParentTreeConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    public static void main(String[] args) throws Exception {
        new WireMockedParentTreeApplication().run(args);
    }


}
