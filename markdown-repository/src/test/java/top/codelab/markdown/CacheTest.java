package top.codelab.markdown;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CacheTest {

    private Cache<String, String> cache;

    @Before
    public void setup() {
        this.cache = new Cache<>();
    }

    @Test
    public void testAddAndGet() {
        String v = this.cache.add("key1", "value1");
        Assert.assertNull(v);
        Assert.assertEquals("value1", this.cache.get("key1"));
        v = this.cache.add("key1", "value2");
        Assert.assertEquals(v, "value1");
    }

    @Test
    public void testRemove() {
        this.cache.add("key1", "value1");
        String v = this.cache.remove("key1");
        Assert.assertEquals("value1", v);
        Assert.assertNull(this.cache.get("key1"));
    }

    @Test
    public void testClear() {
        this.cache.add("key1", "value1");
        Cache<String, String> cache = this.cache.clear();
        Assert.assertEquals(this.cache, cache);
        Assert.assertNull(cache.get("key1"));
    }
}
