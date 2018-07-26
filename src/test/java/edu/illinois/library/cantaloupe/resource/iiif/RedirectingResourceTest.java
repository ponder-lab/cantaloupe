package edu.illinois.library.cantaloupe.resource.iiif;

import edu.illinois.library.cantaloupe.http.Response;
import edu.illinois.library.cantaloupe.resource.ResourceTest;
import edu.illinois.library.cantaloupe.resource.Route;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Functional test of RedirectingResource.
 */
public class RedirectingResourceTest extends ResourceTest {

    @Override
    protected String getEndpointPath() {
        return Route.IIIF_PATH;
    }

    @Test
    public void testDoGet() throws Exception {
        client = newClient("");
        Response response = client.send();

        assertEquals(303, response.getStatus());
        assertTrue(response.getHeaders().getFirstValue("Location").
                endsWith(Route.IIIF_2_PATH));
        assertTrue(response.getBodyAsString().isEmpty());
    }

}
