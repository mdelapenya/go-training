package com.github.simonadonea.quiz;

import com.github.simonadonea.quiz.model.Problem;
import com.github.simonadonea.quiz.model.Quiz;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Quiz!
 *
 */
public class App {

    public static void main( String[] args ) {
        System.out.println( "Hello Quiz!" );

		// create a scanner so we can read the command-line input
		Scanner scanner = new Scanner(System.in);

		try {
			Quiz quiz = new Quiz(scanner, loadCSV("/quiz.csv"));
			quiz.start();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

    private static Problem[] loadCSV(String filename) throws IOException, URISyntaxException {
    	URI uri = App.class.getResource(filename).toURI();
		Reader reader = Files.newBufferedReader(Paths.get(uri));
		List<String[]> records = readCSVFile(reader);

		Problem[] problems = new Problem[records.size()];
		for (int i = 0; i < records.size(); i++) {
			String[] record = records.get(i);

			problems[i] = new Problem(record[0], record[1], i);
		}

		return problems;
	}

	public static List<String[]> readCSVFile(Reader reader) throws IOException {
		List<String[]> list = new ArrayList<>();

		CSVParser parser = new CSVParserBuilder()
				.withSeparator(',')
				.withIgnoreQuotations(true)
				.build();

		CSVReader csvReader = new CSVReaderBuilder(reader)
				.withSkipLines(0)
				.withCSVParser(parser)
				.build();

		String[] line;
		while ((line = csvReader.readNext()) != null) {
			list.add(line);
		}
		reader.close();
		csvReader.close();

		return list;
	}
}
