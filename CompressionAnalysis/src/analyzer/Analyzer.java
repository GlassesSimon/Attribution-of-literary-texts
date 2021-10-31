package analyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Analyzer {
    private final Map<File, Integer> bestCompression = new HashMap<File, Integer>();


    public Analyzer analysis(File[] files) throws FileNotFoundException {
        Scanner[] scanners = new Scanner[files.length];
        for (int i = 0; i < scanners.length; i++) {
            try {
                scanners[i] = new Scanner(new FileInputStream(files[i]));
                bestCompression.put(files[i], 0);
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException(String.format("The file %s not found", files[i]));
            }
        }
        while (scanners[0].hasNextInt()) {
            List<Integer> differences = new ArrayList<>();
            for (Scanner scanner : scanners) {
                differences.add(scanner.nextInt());
            }
            int min = differences.stream().min(Integer::compareTo).get();
            int firstIndex = differences.indexOf(min);
            int lastIndex = differences.lastIndexOf(min);
            File key;
            if (firstIndex != lastIndex) {
                List<File> filesRepeated = new ArrayList<>(List.of(files[firstIndex], files[lastIndex]));
                for (int i = firstIndex + 1; i < lastIndex; i++) {
                    if (differences.get(i) == min) {
                        filesRepeated.add(files[i]);
                    }
                }
                key = randomFile(filesRepeated);
            } else {
                key = files[firstIndex];
            }
            bestCompression.put(key, bestCompression.get(key) + 1);
        }

        return this;
    }

    private File randomFile(List<File> fileNames) {
        return fileNames.get(new Random().nextInt(fileNames.size()));
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        bestCompression.forEach((key, value) -> stringBuilder.append(key.getName()).append(" ").append(value).append("\n"));
        return stringBuilder.toString();
    }
}


