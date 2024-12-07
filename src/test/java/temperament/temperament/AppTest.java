package temperament.temperament;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import temperament.musical.json.TemperamentJson;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	public void testJSonReader() {
		TemperamentJson t = new TemperamentJson("assets/tierce_quinte.json");
		assertTrue(t.isWellDefined());
	}

}
