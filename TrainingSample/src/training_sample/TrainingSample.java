package training_sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TrainingSample {
    private final FileWriter sampleFile;

    public TrainingSample(FileWriter sampleFile) {
        this.sampleFile = sampleFile;
    }

    public void createSampleFile(String[] files, int blocksCount) throws IOException {
        int count = blocksCount;
        Scanner[] scanners = new Scanner[files.length];
        for (int i = 0; i < scanners.length; i++) {
            try {
                scanners[i] = new Scanner(new FileInputStream(files[i]));
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException(String.format("The file %s not found", files[i]));
            }
        }
        while (count > 0) {
            for (Scanner scanner : scanners) {
                try {
                    readWriteBlock(scanner);
                    if (--count == 0) {
                        break;
                    }
                } catch (IOException e) {
                    throw new IOException("File error");
                }
            }
        }
        for (Scanner scanner : scanners) {
            scanner.close();
        }
    }

    private void readWriteBlock(Scanner file) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String add = file.nextLine();
        if ("".equals(add)) {
            stringBuilder.append("\r\n");
            add = file.nextLine();
        }
        stringBuilder.append(add);
        add = file.nextLine();
        while (!"".equals(add)) {
            stringBuilder.append("\r\n").append(add);
            add = file.nextLine();
        }
        try {
            sampleFile.write(stringBuilder.toString());
        } catch (IOException e) {
            throw new IOException("File error");
        }
    }
}
