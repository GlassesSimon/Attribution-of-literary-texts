import training_sample.TrainingSample;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the file name for the training sample:");
        String trainingFileName = input.next();
        System.out.println("Enter the number of files for the training sample:");
        int filesCount = input.nextInt();
        System.out.println("Enter the number of blocks for the training sample:");
        int blocksCount = input.nextInt();
        String[] files = new String[filesCount];

        for (int i = 0; i < filesCount; i++) {
            System.out.println("Enter the file name:");
            files[i] = input.next();
        }

        try(FileWriter fileWriter = new FileWriter(trainingFileName)){
            new TrainingSample(fileWriter).createSampleFile(files, blocksCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
