package com.ontotext.parenttree.health;

import com.codahale.metrics.health.HealthCheck;

/** **/
public class ParentTreeHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {

        // @TODO add some proper health checks
        return HealthCheck.Result.healthy();
    }
}
