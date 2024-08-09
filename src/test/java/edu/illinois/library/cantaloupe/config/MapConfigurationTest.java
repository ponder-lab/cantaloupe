package edu.illinois.library.cantaloupe.config;

import org.junit.jupiter.api.BeforeEach;

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
public class MapConfigurationTest extends AbstractConfigurationTest {

    private MapConfiguration instance;
    
    @Param({"10", "50", "100", "500", "1000", "5000"})
    private static int NUM_CONCURRENT_THREADS;

    @BeforeEach
    @Setup
    public void setUp() throws Exception {
        super.setUp();
        instance = new MapConfiguration();
    }

    protected Configuration getInstance() {
        return instance;
    }
    
    @Benchmark
    public void testClearConcurrently() throws Exception {
    	super.testClearConcurrently(NUM_CONCURRENT_THREADS);
    }
    
    @Benchmark
    public void testClearPropertyWithStringConcurrently() throws Exception {
    	super.testClearPropertyWithStringConcurrently(NUM_CONCURRENT_THREADS);
    }
    
    @Benchmark
    public void testGetBooleanWithStringConcurrently() throws Exception {
    	super.testGetBooleanWithStringConcurrently(NUM_CONCURRENT_THREADS);
    }
    
    @Benchmark
    public void testGetDoubleWithStringConcurrently() throws Exception {
    	super.testGetDoubleWithStringConcurrently(NUM_CONCURRENT_THREADS);
    }
    
    @Benchmark
    public void testGetFloatWithStringConcurrently() throws Exception {
    	super.testGetFloatWithStringConcurrently(NUM_CONCURRENT_THREADS);
    }
    
    @Benchmark
    public void testGetIntWithStringConcurrently() throws Exception {
    	super.testGetIntWithStringConcurrently(NUM_CONCURRENT_THREADS);
    }
    
    @Benchmark
    public void testGetKeysConcurrently() throws Exception {
    	super.testGetKeysConcurrently(NUM_CONCURRENT_THREADS);
    }
    
    @Benchmark
    public void testGetLongWithStringConcurrently() throws Exception {
    	super.testGetLongWithStringConcurrently(NUM_CONCURRENT_THREADS);
    }
    
    @Benchmark
    public void testGetLongBytesWithStringConcurrently() throws Exception {
    	super.testGetLongBytesWithStringConcurrently(NUM_CONCURRENT_THREADS);
    }
    
    @Benchmark
    public void testGetPropertyWithStringConcurrently() throws Exception {
    	super.testGetPropertyWithStringConcurrently(NUM_CONCURRENT_THREADS);
    }
    
    @Benchmark
    public void testGetStringWithStringConcurrently() throws Exception {
    	super.testGetStringWithStringConcurrently(NUM_CONCURRENT_THREADS);
    }

}
