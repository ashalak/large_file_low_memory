package by.shalak;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Alexey Shalak
 */
public class Start {
	private static Path inputDataFile = Paths.get("input_data_file.txt");
	private static final int INPUT_DATA_FILE_SIZE_MB = 30;

	public static void main(String[] args) {
		try {
			createInputFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creation of line separated text file of integers ranging anywhere from Integer.MIN to Integer.MAX of size 1024MB
	 */
	private static void createInputFile() throws IOException {
		if (Files.notExists(inputDataFile)) {
			Files.createFile(inputDataFile);

			try (FileWriter writer = new FileWriter(inputDataFile.toFile())) {

				System.out.println("Creation of input_data_file.txt...");

				while (inputDataFile.toFile().length() < Math.pow(1024, 2) * INPUT_DATA_FILE_SIZE_MB) {
					writer.write(Integer.toString(getRandomInt()));
					writer.write(System.lineSeparator());
					writer.flush();
				}
			}
		}
	}

	/**
	 * Getting of random int value from Integer.MIN_VALUE to Integer.MAX_VALUE
	 *
	 * @return int
	 */
	private static int getRandomInt() {
		return (int) ((long) Integer.MIN_VALUE + Math.random() * ((long) Integer.MAX_VALUE - Integer.MIN_VALUE + 1));
	}
}

