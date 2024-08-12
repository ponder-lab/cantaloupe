package edu.illinois.library.cantaloupe.operation.overlay;

import edu.illinois.library.cantaloupe.test.BaseTest;
import edu.illinois.library.cantaloupe.test.ConcurrentReaderWriter;
import edu.illinois.library.cantaloupe.test.TestUtil;
import edu.illinois.library.cantaloupe.test.WebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import static edu.illinois.library.cantaloupe.test.PerformanceTestConstants.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = WARMUP_ITERATIONS,
        time = WARMUP_TIME)
@Measurement(iterations = MEASUREMENT_ITERATIONS,
        time = MEASUREMENT_TIME)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = { "-server", "-Xms128M", "-Xmx128M", "-Dcantaloupe.config=memory" })
public class ImageOverlayCacheTest extends BaseTest {

    private static WebServer webServer;
    private ImageOverlayCache instance;
    
    @Param({"10", "50", "100", "500", "1000", "5000"})
    private int numThreads;

    @BeforeAll
    @Setup
    public static void beforeClass() throws Exception {
        webServer = new WebServer();
        webServer.start();
    }

    @AfterAll
    @TearDown
    public static void afterClass() throws Exception {
        webServer.stop();
    }

    @BeforeEach
    @Setup
    public void setUp() throws Exception {
        super.setUp();
        instance = new ImageOverlayCache();
    }

    // putAndGet(URI)

    @Test
    void testPutAndGetWithPresentFileURI() throws IOException {
        URI uri = TestUtil.getImage("jpg").toUri();
        byte[] bytes = instance.putAndGet(uri);
        assertEquals(1584, bytes.length);
    }

    @Test
    void testPutAndGetWithMissingFileURI() throws Exception {
        URI uri = TestUtil.getImage("blablabla").toUri();
        assertThrows(IOException.class, () -> instance.putAndGet(uri));
    }

    @Test
    void testPutAndGetWithPresentRemoteURI() throws Exception {
        URI uri = new URI(webServer.getHTTPURI() + "/jpg");
        byte[] bytes = instance.putAndGet(uri);
        assertEquals(1584, bytes.length);
    }

    @Test
    void testPutAndGetWithMissingRemoteURI() throws Exception {
        URI uri = new URI(webServer.getHTTPURI() + "/blablabla");
        assertThrows(IOException.class, () -> instance.putAndGet(uri));
    }

    @Test
    @Benchmark
    public void testPutAndGetConcurrently() throws Exception {
        Callable<Void> callable = () -> {
            URI uri = new URI(webServer.getHTTPURI() + "/jpg");
            byte[] bytes = instance.putAndGet(uri);
            assertEquals(1584, bytes.length);
            return null;
        };
        new ConcurrentReaderWriter(callable, callable, numThreads).run();
    }

}
