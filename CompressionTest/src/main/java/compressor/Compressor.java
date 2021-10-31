package compressor;

import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;

public class Compressor {
    private final String trainingFileString;
    private final String trainingFileName;
    private final String testFileName;
    private Scanner testFile;
    private FileWriter trainingFile;
    private long trainingCompressSize;


    public Compressor(String trainingFileName, String testFileName) throws IOException {
        this.trainingFileName = trainingFileName;
        this.testFileName = testFileName;
        try (InputStream trainingFile = new FileInputStream(trainingFileName)) {
            trainingFileString = new String(trainingFile.readAllBytes());
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(String.format("The file %s not found", trainingFileName));
        } catch (IOException e) {
            throw new IOException("File error " + trainingFileName);
        }
    }

    public void readAndCompress() throws IOException, InterruptedException {
        try (Scanner scanner = new Scanner(new FileInputStream(testFileName))) {
            testFile = scanner;
            int count = 0;
            int trainingPointIndex = trainingFileName.lastIndexOf('.');
            int testBracketIndex = testFileName.lastIndexOf('.');
            String tmpFile = new StringBuilder(trainingFileName)
                    .insert(trainingPointIndex, testFileName.substring(testFileName.lastIndexOf('\\')+1, testBracketIndex))
                    .toString();
            String resultFileName = new StringBuilder(tmpFile).delete(0,tmpFile.indexOf('\\')).insert(0,"result").toString();
            try (FileWriter outputFile = new FileWriter(resultFileName)) {
                String zip = trainingFileName.substring(0, trainingPointIndex) + ".7z";
                Process p = Runtime.getRuntime().exec(String.format("7z a -mx9 %s %s", zip, trainingFileName));
                p.waitFor();
                trainingCompressSize = Paths.get(zip).toFile().length();
                Paths.get(zip).toFile().deleteOnExit();
                while (testFile.hasNext()) {
                    count++;
                    try (FileWriter fileWriter = new FileWriter(tmpFile)) {
                        trainingFile = fileWriter;
                        trainingFile.write(trainingFileString);
                        readBlock();
                    }
                    compress(outputFile, tmpFile.substring(0, tmpFile.lastIndexOf(".")));
                }
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(String.format("The file %s not found", testFileName));
        } catch (IOException e) {
            throw new IOException("File error");
        } catch (InterruptedException e) {
            throw new InterruptedException("Compression error");
        }
    }


    private void readBlock() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String add = testFile.nextLine();
        if ("".equals(add)) {
            stringBuilder.append("\r\n");
            add = testFile.nextLine();
        }
        stringBuilder.append(add);
        add = testFile.nextLine();
        while (!"".equals(add)) {
            stringBuilder.append("\r\n").append(add);
            add = testFile.nextLine();
        }
        try {
            trainingFile.write(stringBuilder.toString());
        } catch (IOException e) {
            throw new IOException("File error");
        }
    }

    private void compress(FileWriter outputFile, String baseName) throws IOException, InterruptedException {
        String txt = baseName + ".txt";
        String zip = baseName + ".7z";
        Process p = Runtime.getRuntime().exec(String.format("7z a -mx9 %s %s", zip, txt));
        p.waitFor();
        Paths.get(txt).toFile().deleteOnExit();
        outputFile.write(((Paths.get(zip).toFile().length()) - trainingCompressSize) + "\n");
        Paths.get(baseName + ".7z").toFile().deleteOnExit();
    }

}
