import compressor.Compressor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the names of the training samples:");
        String trainingFileName = input.nextLine();
        System.out.println("Enter the names of the test samples:");
        String testFileName = input.nextLine();

        File file = new File(trainingFileName);
        List<File> trainingFiles = new ArrayList<>();
        addFiles(file, trainingFiles);

        file = new File(testFileName);
        List<File> testFiles = new ArrayList<>();
        addFiles(file, testFiles);

        for (File trainingFile : trainingFiles)
            for (File testFile : testFiles) {
                try {
                    new Compressor(trainingFile.getPath(), testFile.getPath()).readAndCompress();
                } catch (IOException | InterruptedException e) {
                    System.out.println(e.getMessage());
                }
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
