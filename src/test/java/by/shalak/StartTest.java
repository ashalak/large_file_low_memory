package by.shalak;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

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
		assertTrue("Max memory is available for JVM is bigger than 100 MB!", Runtime.getRuntime().maxMemory() / 1024 / 1024 <= 100);
	}

	/**
	 * Test inputDataFileSize and outputDataFileSize equality
	 */
	@Test
	public void sizeEqualityTest() {
		Start.main(null);

		long inputDataFileSize = Paths.get("input_data_file.txt").toFile().length();
		long outputDataFileSize = Paths.get("output_data_file.txt").toFile().length();
		System.out.println("inputDataFileSize: " + inputDataFileSize);
		System.out.println("outputDataFileSize: " + outputDataFileSize);

		assertEquals("inputDataFileSize not equals outputDataFileSize! ", inputDataFileSize, outputDataFileSize);
	}

	/**
	 * If inputDataFile contains duplicate
	 */
	@Test
	public void duplicateTest() {
		System.out.println("Creation of input_data_file.txt...");

		try (PrintWriter printWriter = new PrintWriter(new FileWriter(Start.getInputDataFile().toFile()))) {

			while (Start.getInputDataFile().toFile().length() < Start.getInputDataFileSize()) {
				printWriter.println(1);
				printWriter.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		sizeEqualityTest();
	}
}
