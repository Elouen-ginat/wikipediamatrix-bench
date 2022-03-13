package fr.univrennes1.istic.wikipediamatrix.Serializer;

import java.io.Writer;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import fr.univrennes1.istic.wikipediamatrix.App;

public abstract class Serializer {

    private Path save_path;

    private final String invalid_char = "[\\\\/:*?\"<>|]";

    public Serializer(String save_path) {
        // Convert the string to the relative path
        this.save_path = Paths.get(save_path);
    }

    public Path getPath(String file_name) {
        // Remove invalid characters
        String file_name_norm = file_name.replaceAll(invalid_char, "");
        // Check if the name has been changed
        if (!file_name_norm.equals(file_name)) {
            App.LOGGER.warn("File name contains invalid characters. Name modified");
        }
        // Get the path to the project directory
        Path project_path = Paths.get("").toAbsolutePath();
        // Remove wikimatrix from path depending on location of execution
        if (project_path.endsWith("wikimatrix")) {
            project_path = project_path.getParent();
        }
        // Join path to get the path to the csv file given the file_name
        Path csv_path = Paths.get(project_path.toString(), this.save_path.toString(), file_name_norm);

        return csv_path;
    }

    public void saveToCSV(String[][] string_table, String file_name) throws IOException{
        Path csv_path = this.getPath(file_name);
        Writer writer = null;
        // Open the csv file to write in
        try {
            FileOutputStream file_stream = new FileOutputStream(csv_path.toString());
            // Specify the char encoding (UTF-8)
            OutputStreamWriter stream_writer = new OutputStreamWriter(file_stream, StandardCharsets.UTF_8);
            writer = new BufferedWriter(stream_writer);
        } catch (IOException e) {
            String message = "Error opening file: " + csv_path.toString();
            throw new IOException(message);
        }
        // Init the string builder being the content of the csv
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < string_table.length; i++) {
            for (String st : string_table[i]) {
                // Escape doubles quotes by adding a double quote see (https://datatracker.ietf.org/doc/html/rfc4180)
                String st_quotes = st.replace("\"", "\"\"");
                // Encapsulate data in quotes "" to prevent comma in data
                content.append('"'+st_quotes+'"');
                content.append(",");
            }
            int length = content.length();
            // Remove last character if it is a comma
            if (length > 0) {
                char last_char = content.charAt(length-1);
                if (last_char == ',') {
                    content.setLength(length-1);
                }
            }
            // Change row
            content.append("\n");
        }
        // Add the string to the file and close it
        try {
            writer.append(content.toString());
            writer.close();
        } catch (IOException e) {
            String message = "Error writing to file: " + csv_path.toString();
            throw new IOException(message);
        }
    }

    public static String mkCSVFileName(String url, int n) {
		return url.trim() + "-" + n + ".csv";
	}
    
}
