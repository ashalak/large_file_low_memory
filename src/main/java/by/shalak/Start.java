package by.shalak;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author Alexey Shalak
 */
public class Start {
	private static Path inputDataFile = Paths.get("input_data_file.txt");
	private static Path outputDataFile = Paths.get("output_data_file.txt");

	private static int inputDataFileSize = 1024 * 1024 * 1; // size of the inputDataFile
	private static int maxMemorySize = (int) Runtime.getRuntime().maxMemory(); // size of the max memory
	//private static int tempFilesCount = (inputDataFileSize / maxMemorySize + 1) * 10;
	private static int tempFilesCount = 2;

	public static void main(String[] args) throws IOException {
		System.out.println("inputDataFileSize - " + inputDataFileSize / 1024 / 1024 + " MB");
		System.out.println("maxMemorySize - " + maxMemorySize / 1024 / 1024 + " MB");
		System.out.println("tempFilesCount - " + tempFilesCount);

		createInputDataFile();
		List<File> tempFiles = createTempFiles();
		List<File> sortedTempFiles = sortTempFiles(tempFiles);
		createOutPutDataFile(sortedTempFiles);
	}

	private static void createInputDataFile() throws IOException {
		if (Files.notExists(inputDataFile)) {
			Files.createFile(inputDataFile);
			System.out.println("Creation of input_data_file.txt...");

			try (PrintWriter printWriter = new PrintWriter(new FileWriter(inputDataFile.getFileName().toString()))) {

				while (inputDataFile.toFile().length() < inputDataFileSize) {
					printWriter.println((int) ((long) Integer.MIN_VALUE + Math.random() * ((long) Integer.MAX_VALUE - Integer.MIN_VALUE + 1)));
				}
			}
		}
	}

	/**
	 * Split the large file into valid for memory temp files
	 */
	private static List<File> createTempFiles() throws IOException {
		List<File> files = new ArrayList<>();
		long tempFileSize = inputDataFile.toFile().length() / tempFilesCount;

		System.out.println("Creation of temp files...");

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputDataFile.toFile()))) {
			int fileCounter = 1;
			for (int i = 1; i <= tempFilesCount; i++) {

				File tempFile = new File("temp_file_" + fileCounter + ".txt");
				try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempFile))) {

					String line;
					while ((line = bufferedReader.readLine()) != null && tempFile.length() <= tempFileSize) {
						bufferedWriter.write(line);
						bufferedWriter.write(System.lineSeparator());
						bufferedWriter.flush();
					}

					files.add(tempFile);
					fileCounter++;
				}
			}
		}

		return files;
	}

	/**
	 * Sort temp file's data
	 */
	private static List<File> sortTempFiles(List<File> tempFiles) throws IOException {
		System.out.println("Sorting of temp files...");

		for (File tempFile : tempFiles) {
			List<String> lines = Files.lines(tempFile.toPath()).sorted(Comparator.comparing(Integer::valueOf))
							.collect(Collectors.toList());

			try (BufferedWriter newBufferedWriter = Files.newBufferedWriter(tempFile.toPath())) {
				for (String line : lines) {
					newBufferedWriter.write(line);
					newBufferedWriter.write(System.lineSeparator());
					newBufferedWriter.flush();
				}
			}
		}

		return tempFiles;
	}

	/**
	 * Merge sorted temp files into a sorted outPutDataFile
	 */
	private static void createOutPutDataFile(List<File> sortedTempFiles) throws IOException {
		System.out.println("Creation of output_data_file.txt...");

		List<BufferedReader> bufferedReaders = new ArrayList<>();
		Map<Integer, BufferedReader> map = new TreeMap<>();

		try (PrintWriter printWriter = new PrintWriter(new FileWriter(outputDataFile.getFileName().toString()))) {
			for (File file : sortedTempFiles) {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
				bufferedReaders.add(bufferedReader);
				String line = bufferedReader.readLine();
				map.put(Integer.valueOf(line), bufferedReader);
			}

			while (!map.isEmpty()) {
				List<Integer> sortedKeys =  map.entrySet().stream().map(Entry::getKey).sorted().collect(Collectors.toList());
				int minValue = sortedKeys.get(0);
				BufferedReader minValueBufferedReader = map.get(minValue);

				printWriter.println(minValue);
				map.remove(minValue);

				String line;
				if ((line = minValueBufferedReader.readLine()) != null) {
					map.put(Integer.valueOf(line), minValueBufferedReader);
				}
			}

		} finally {
			if (bufferedReaders.size() > 0) {
				for (BufferedReader br : bufferedReaders) {
					br.close();
				}
			}

			for (File file : sortedTempFiles) {
				//file.delete();
			}
		}
	}
}

