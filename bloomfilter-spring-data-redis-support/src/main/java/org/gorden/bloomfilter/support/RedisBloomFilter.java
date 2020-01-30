package org.gorden.bloomfilter.support;

import org.gorden.bloomfilter.core.BloomFilter;
import org.gorden.bloomfilter.core.bitset.BitSet;
import org.gorden.bloomfilter.core.hash.HashFunction;
import org.gorden.bloomfilter.core.serializer.BloomFilterSerializer;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @author: GordenTam
 * @create: 2020-01-16
 **/

public class RedisBloomFilter<T> extends BloomFilter<T> {

    private String name;

    private RedisBloomFilter(int numHashFunctions, BitSet bitSet, BloomFilterSerializer bloomFilterSerializer, HashFunction hashFunction) {
        super(numHashFunctions, bitSet, bloomFilterSerializer, hashFunction);
    }

    public static <T> RedisBloomFilter<T> create(String name, long expectedInsertions, double fpp, RedisConnectionFactory redisConnectionFactory, BloomFilterSerializer bloomFilterSerializer, HashFunction hashFunction) {
        if (expectedInsertions <= 0) {
            throw new IllegalArgumentException(String.format("expectedInsertions (%s) must be > 0", expectedInsertions));
        }
        if (fpp >= 1.0) {
            throw new IllegalArgumentException(String.format("numHashFunctions (%s) must be < 1.0", fpp));
        }
        if (fpp <= 0.0) {
            throw new IllegalArgumentException(String.format("numHashFunctions (%s) must be > 0.0", fpp));
        }
        long numBits = optimalNumOfBits(expectedInsertions, fpp);
        int numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, numBits);
        return new RedisBloomFilter<T>(numHashFunctions, new RedisBitSet(numBits, name, redisConnectionFactory), bloomFilterSerializer, hashFunction);
    }

}
