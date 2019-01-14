package by.shalak;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for Start.
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

		long inputDataFileSize = Start.getInputDataFilePath().toFile().length();
		long outputDataFileSize = Start.getOutputDataFilePath().toFile().length();
		System.out.println("inputDataFileSize: " + inputDataFileSize);
		System.out.println("outputDataFileSize: " + outputDataFileSize);

		assertEquals("inputDataFileSize not equals outputDataFileSize! ", inputDataFileSize, outputDataFileSize);

		Start.getInputDataFilePath().toFile().delete();
		Start.getOutputDataFilePath().toFile().delete();
	}

	/**
	 * If inputDataFile contains duplicate
	 */
	@Test
	public void duplicateTest() {
		System.out.println("Creation of input_data_file.txt...");

		try (PrintWriter printWriter = new PrintWriter(new FileWriter(Start.getInputDataFilePath().toFile()))) {

			while (Start.getInputDataFilePath().toFile().length() < Start.getInputDataFileSize()) {
				printWriter.println(1);
				printWriter.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		sizeEqualityTest();
	}
}
