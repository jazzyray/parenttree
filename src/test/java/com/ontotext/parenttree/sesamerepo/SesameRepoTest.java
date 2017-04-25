package com.ontotext.parenttree.sesamerepo;

import com.ontotext.parenttree.exception.GraphDBRepositoryException;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.impl.StatementImpl;
import org.eclipse.rdf4j.query.GraphQuery;
import org.eclipse.rdf4j.query.GraphQueryResult;
import org.eclipse.rdf4j.query.MalformedQueryException;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.impl.IteratingGraphQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openrdf.query.impl.GraphQueryResultImpl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/** **/
public class SesameRepoTest {

    private final String REPO = "parent-tree";
    private final String SERVICE_URL = "http://localhost:7200/repositories/";

    SesameRepo sesameRepo;
    private final Repository repository = mock(Repository.class);
    private final RepositoryConnection repositoryConnection = mock(RepositoryConnection.class);
    private final GraphQuery graphQuery = mock(GraphQuery.class);
    final ValueFactory valueFactory = SimpleValueFactory.getInstance();
    private final String SPARQL_QUERY = "";

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        this.sesameRepo = new SesameRepo(SERVICE_URL, REPO);
        this.sesameRepo.setRepo(this.repository);
    }


    @Test
    public void shouldThrowOpenRDFExceptionWhenGraphQueryIsMalformed() throws Exception {
        expectedException.expect(GraphDBRepositoryException.class);
        when(this.repository.getConnection()).thenReturn(this.repositoryConnection);
        when(this.repositoryConnection.prepareGraphQuery(any(), anyString())).thenReturn(this.graphQuery);
        when(this.repositoryConnection.prepareGraphQuery(any(), anyString())).thenThrow(new MalformedQueryException());
        sesameRepo.executeGraphQuery("This aint SPARQL");
    }

    @Test
    public void shouldReturnSuccessfully() throws Exception {
        Set<Statement> statements = new HashSet<>();
        Statement statement = valueFactory.createStatement(valueFactory.createIRI("urn:1"), valueFactory.createIRI("urn:2"), valueFactory.createIRI("urn:3"));
        statements.add(statement);
        GraphQueryResult graphQueryResult = new IteratingGraphQueryResult(Collections.singletonMap("String", "String"), statements.iterator());

        when(this.repository.getConnection()).thenReturn(this.repositoryConnection);
        when(this.repositoryConnection.prepareGraphQuery(any(), anyString())).thenReturn(this.graphQuery);

        when(graphQuery.evaluate()).thenReturn(graphQueryResult);

        Model result = sesameRepo.executeGraphQuery(SPARQL_QUERY);
        assertThat(result.isEmpty(), is(false));
        verify(this.repositoryConnection, times(1)).prepareGraphQuery(QueryLanguage.SPARQL, SPARQL_QUERY);
        verify(graphQuery, times(1)).evaluate();

    }

}
