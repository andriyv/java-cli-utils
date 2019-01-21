package org.open.software.utils.cli;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class OptionsTest.
 */
public class OptionsTest {

	/**
	 * Test options get.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testOptionsGet() throws Exception {

		Options options = new Options(new String[] { "-a", "10", "-b", "-c", "Hello world" });

		Assert.assertTrue(options.has("a"));
		Assert.assertTrue(options.has("b"));
		Assert.assertTrue(options.has("c"));
		Assert.assertFalse(options.has("d"));

		Assert.assertEquals(new Integer(10), options.get("a", Integer.class));
		Assert.assertEquals(new Long(10), options.get("a", Long.class));
		Assert.assertEquals(new Float(10), options.get("a", Float.class));
	}
	
	@Test
	public void testOptionsGetQuoted() throws Exception {

		Options options = new Options(new String[] { "/a", "\"Hello World\""});

		Assert.assertTrue(options.has("a"));
		Assert.assertFalse(options.has("b"));;

		Assert.assertEquals("Hello World", options.get("a", String.class));
	}
	
	@Test
	public void testOptionsGetEqualsQuoted() throws Exception {

		Options options = new Options(new String[] { "\"/a=\"Hello World\"\""});

		Assert.assertTrue(options.has("a"));
		Assert.assertFalse(options.has("b"));;

		Assert.assertEquals("Hello World", options.get("a", String.class));
	}
	
	@Test
	public void testOptionsListEqualsWithQuoted() throws Exception {

		Options options = new Options(new String[] { "\"/a=\"Hello World\",super,\"another, one\"\""});

		Assert.assertTrue(options.has("a"));
		Assert.assertFalse(options.has("b"));;

		List<String> list = options.list("a", String.class);
		
		Assert.assertNotNull(list);
		Assert.assertEquals(3, list.size());
	}

	/**
	 * Test options list.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testOptionsList() throws Exception {

		Options options = new Options(new String[] { "-a", "10", "-a", "20,39,40,B12", "-a", "Hello world" });

		Assert.assertTrue(options.has("a"));

		List<Integer> aInts = options.list("a", Integer.class);

		Assert.assertNotNull(aInts);
		Assert.assertEquals(4, aInts.size());

		List<String> aStrings = options.list("a", String.class);

		Assert.assertNotNull(aStrings);
		Assert.assertEquals(6, aStrings.size());
	}

}
