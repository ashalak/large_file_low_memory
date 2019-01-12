package by.shalak;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for simple Start.
 *
 * @author Alexey Shalak
 */
public class StartTest {

	/**
	 * Test memory constraint
	 */
	@Test
	public void memoryConstraintTest() {
		System.out.println("Max memory is available for JVM (MB): " + Runtime.getRuntime().maxMemory() / 1024 / 1024);
		assertFalse("Max memory is available for JVM is bigger than 100 MB", Runtime.getRuntime().maxMemory() / 1024 / 1024 <= 1);
	}
}
