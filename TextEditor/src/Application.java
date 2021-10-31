import editor.TextEditor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Application {
    private static final String outputFilesName = "result";
    private static final String inputFilesName = "files";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the file name:");
        String fileName = scanner.nextLine();
        System.out.println("Enter the block size in bits:");
        int size = scanner.nextInt();
        System.out.println("Enter the number of blocks:");
        int count = scanner.nextInt();
        File file = new File(fileName);
        List<File> files = new ArrayList<>();
        addFiles(file, files);
        for (File input : files) {
            try {
                File output = new File(input.getPath().replaceFirst(inputFilesName, outputFilesName).replace(".txt", (size/1024)+"Kb.txt"));
                if (!Paths.get(output.getParent()).toFile().exists()) {
                    boolean result = output.getParentFile().mkdirs();
                    if(!result){
                        throw new IOException("Error creating directory");
                    }
                }
                System.out.println(TextEditor.edit(input, size, count, output));
            } catch (IOException e) {
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
