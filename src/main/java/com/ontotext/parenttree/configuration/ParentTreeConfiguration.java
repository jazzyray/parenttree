package com.ontotext.parenttree.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

/** **/
public class ParentTreeConfiguration extends Configuration {

    @JsonProperty
    public String sesameRepoURL;

    @JsonProperty
    public String repoID;

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

}
