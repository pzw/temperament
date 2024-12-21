package temperament.temperament;

import java.nio.file.Path;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import temperament.musical.ITemperament;
import temperament.musical.json.TemperamentJson;
import temperament.musical.json.TemperamentJsonVisitor;

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
	
	public void testJSonVisitor() {
		TemperamentJsonVisitor visitor = new TemperamentJsonVisitor();
		List<ITemperament> list = visitor.buildTemperaments(Path.of("./assets"));
		assertEquals(1,  list.size());
	}

}
