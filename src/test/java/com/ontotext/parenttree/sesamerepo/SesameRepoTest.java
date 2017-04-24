package com.ontotext.parenttree.sesamerepo;

import com.ontotext.parenttree.exception.GraphDBRepositoryException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openrdf.model.Model;
import org.openrdf.model.Statement;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.query.GraphQuery;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.impl.GraphQueryResultImpl;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;

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
    private final String SERVICE_URL = "http://localhost:7200/repository/";

    SesameRepo sesameRepo;
    private final Repository repository = mock(Repository.class);
    private final RepositoryConnection repositoryConnection = mock(RepositoryConnection.class);
    private final GraphQuery graphQuery = mock(GraphQuery.class);
    final ValueFactory valueFactory = new ValueFactoryImpl();
    private final String SPARQL_QUERY = "";


    //private final IPreparedGraphQuery graphQuery = mock(IPreparedGraphQuery.class);

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        this.sesameRepo = new SesameRepo(SERVICE_URL + REPO);
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
        StatementImpl statement = new StatementImpl(valueFactory.createURI("urn:1"), valueFactory.createURI("urn:2"), valueFactory.createURI("urn:3"));
        statements.add(statement);
        GraphQueryResult graphQueryResult = new GraphQueryResultImpl(Collections.singletonMap("String", "String"), statements);

        when(this.repository.getConnection()).thenReturn(this.repositoryConnection);
        when(this.repositoryConnection.prepareGraphQuery(any(), anyString())).thenReturn(this.graphQuery);

        when(graphQuery.evaluate()).thenReturn(graphQueryResult);

        Model result = sesameRepo.executeGraphQuery(SPARQL_QUERY);
        assertThat(result.isEmpty(), is(false));
        verify(this.repositoryConnection, times(1)).prepareGraphQuery(QueryLanguage.SPARQL, SPARQL_QUERY);
        verify(graphQuery, times(1)).evaluate();

    }

}
