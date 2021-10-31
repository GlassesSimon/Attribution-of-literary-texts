import analyzer.Analyzer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the file name for the analysis result:");
        String resultFileName = input.next();
        System.out.println("Enter filenames for analysis:");
        String fileName = input.next();

        File file = new File(fileName);
        List<File> files = new ArrayList<>();
        addFiles(file, files);

        File[] filesArray = new File[files.size()];
        filesArray = files.toArray(filesArray);

        try(FileWriter fileWriter = new FileWriter(resultFileName)){
            fileWriter.write(new Analyzer().analysis(filesArray).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void addFiles(File source, List<File> fileList) {
        if (source.isFile()) {
            fileList.add(source);
            return;
        }
        for (File file : Objects.requireNonNull(source.listFiles())) {
            addFiles(file, fileList);
        }
    }
}
