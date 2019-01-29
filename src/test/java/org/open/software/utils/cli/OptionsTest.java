package org.open.software.utils.cli;

import java.io.File;
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
	
	/**
	 * Test options get.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testOptionsAliasesGet() throws Exception {

		Options options = new Options(new String[] { "-a", "10", "-b", "-another", "Hello world" });

		Assert.assertTrue(options.has("a"));
		Assert.assertTrue(options.has("another"));
		Assert.assertTrue(options.has("a?another"));
		
		Assert.assertEquals("10", options.get("a?another", String.class));
		
		List<String> s = options.list("a?another", String.class);
		Assert.assertEquals(2, s.size());
		
	}
	
	/**
	 * Test options get with predicate
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testOptionsWithPredicate() throws Exception {

		Options options = new Options(new String[] { "-a", "10", "-a", "12,25" });

		Assert.assertTrue(options.has("a"));
		

		Assert.assertEquals(new Integer(12), options.get("a", Integer.class, (x) -> x > 10));
		
		List<Integer> list = options.list("a", Integer.class, (x) -> x < 20);
		Assert.assertEquals(2, list.size());
		Assert.assertTrue(list.contains(10));
		Assert.assertTrue(list.contains(12));
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
	
	/**
	 * Test options list.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testOptionsListOfFiles() throws Exception {

		Options options = new Options(new String[] { "-a", "a.txt", "-a", "b.txt", "-a", "Hello world" });

		List<File> list = options.list("a", File.class);
		
		Assert.assertEquals(3, list.size());
		
		List<File> existingList = options.list("a", File.class, (d) -> d.exists());
	
		Assert.assertTrue(existingList.isEmpty());
	}
	

}
