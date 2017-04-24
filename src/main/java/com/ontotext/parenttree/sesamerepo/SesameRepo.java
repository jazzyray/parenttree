package com.ontotext.parenttree.sesamerepo;

import com.ontotext.parenttree.exception.GraphDBRepositoryException;
import org.openrdf.model.Model;
import org.openrdf.query.*;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** **/
public class SesameRepo {

    private static final Logger LOGGER = LoggerFactory.getLogger(SesameRepo.class);

    Repository repo;

    public SesameRepo(String repoURL) {
        try {
            this.repo = new HTTPRepository(repoURL);
            repo.initialize();
        } catch (RepositoryException re) {
            throw new GraphDBRepositoryException(re);
        }
    }

    public Model executeGraphQuery(String queryString)  {
        try {
            LOGGER.debug("Executing graphQuery:\n{}", queryString);
            RepositoryConnection connection = this.repo.getConnection();
            GraphQuery graphQuery = connection.prepareGraphQuery(QueryLanguage.SPARQL, queryString);
            GraphQueryResult graphQueryResult = graphQuery.evaluate();
            Model result = QueryResults.asModel(graphQueryResult);
            graphQueryResult.close();
            return result;

        } catch (RepositoryException e) {
            LOGGER.error("Unable to get repository connection. ", e);
            throw new GraphDBRepositoryException("Unable to get repository connection. ", e);
        } catch (MalformedQueryException e) {
            LOGGER.error("Unable to prepare the graph query as it is malformed", e);
            throw new GraphDBRepositoryException("Unable to prepare the graph query as it is malformed", e);
        } catch (QueryEvaluationException e) {
            LOGGER.error("Unable to execute the graph query", e);
            throw new GraphDBRepositoryException("Unable to execute the graph query", e);
        } catch (Exception e) {
            LOGGER.error("Unable to execute the graph query", e);
            throw new GraphDBRepositoryException("Unable to execute the graph query", e);
        }
    }

    public Repository getRepo() {
        return this.repo;
    }

    public void setRepo(Repository repo) {
        this.repo = repo;
    }
}
