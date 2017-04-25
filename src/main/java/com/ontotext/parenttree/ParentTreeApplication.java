package com.ontotext.parenttree;

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

public class ParentTreeApplication  extends Application<ParentTreeConfiguration> {

    ParentTreeService parentTreeService;

    @Override
    public void run(ParentTreeConfiguration parentTreeConfiguration, Environment environment) throws Exception {
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
        new ParentTreeApplication().run(args);
    }
}
