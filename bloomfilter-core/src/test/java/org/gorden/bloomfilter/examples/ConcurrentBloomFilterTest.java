package org.gorden.bloomfilter.examples;

import org.gorden.bloomfilter.examples.concurrent.ConcurrentBloomFilter;
import org.gorden.bloomfilter.examples.hash.Murmur3_128HashFunction;
import org.gorden.bloomfilter.examples.serializer.JdkSerializationBloomFilterSerializer;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ConcurrentBloomFilterTest {

    @Test
    public void mightContainTest() {
        ConcurrentBloomFilter<String> concurrentBloomFilter = ConcurrentBloomFilter.create("test", 100000, 0.03, new JdkSerializationBloomFilterSerializer(), new Murmur3_128HashFunction(0));
        concurrentBloomFilter.put("a");
        concurrentBloomFilter.put("b");
        concurrentBloomFilter.put("c");
        assertTrue(concurrentBloomFilter.mightContain("a"));
        assertTrue(concurrentBloomFilter.mightContain("b"));
        assertTrue(concurrentBloomFilter.mightContain("c"));
        assertFalse(concurrentBloomFilter.mightContain("d"));
    }
}
