package editor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class TextEditor {
    /**
     * @param inputFile the file to be edited
     * @param size      block size
     * @param count     number of blocks
     * @return information about the result of work
     * @throws FileNotFoundException if the file with the specified name isn't found
     */

    public static String edit(File inputFile, int size, int count, File newFile) throws IOException {
        String fileString;

        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(inputFile))) {
            fileString = new String(input.readAllBytes());
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(String.format("File %s not found.", inputFile.getName()));
        } catch (IOException e) {
            throw new IOException(String.format("File %s error.", inputFile.getName()));
        }

        Random random = new Random();
        fileString = toFormatWithoutPunctuation(fileString);
        List<Boundary> regionBoundaries = new ArrayList<>();
        int curCount = 0;

        try (FileWriter output = new FileWriter(newFile)) {
            while (curCount < count) {
                int begin;
                boolean isPresence = false;

                do { 
                    begin = random.nextInt(fileString.length());

                    int mayBeEnd = begin + size * 3 / 4;
                    int needEnd = begin + size / 2;
                    if (needEnd >= fileString.length()) {
                        continue;
                    }
                    for (Boundary boundary : regionBoundaries) {
                        Boundary boundaryCheck = new Boundary(begin, mayBeEnd);
                        isPresence = boundary.between(begin) || boundary.between(mayBeEnd);
                        isPresence |= boundaryCheck.between(boundary.getStart());
                        if (isPresence) {
                            break;
                        }
                    }
                } while (isPresence);

                int end = begin + size / 2;
                StringBuilder outString = new StringBuilder();

                try {
                    outString.append(fileString, begin, end);
                    int difference = size - outString.toString().getBytes().length;

                    while (difference > 0) {
                        String add = fileString.substring(end, end + difference / 2 + 1);
                        end += add.length();
                        outString.append(add);
                        difference -= add.getBytes().length;
                    }
                } catch (Exception e) {
                    continue;
                }
                outString.append("\r\n\r\n");

                addBoundaries(new Boundary(begin, end - 1), regionBoundaries);
                output.write(outString.toString());

                curCount++;

                if (!hasRegion(regionBoundaries, size * 3 / 4, fileString.length()) && curCount != count - 1) {
                    break;
                }
            }
        } catch (IOException e) {
            throw new IOException(String.format("File %s error.", inputFile.getName()));
        }
        return "To the file " + newFile.getName() + " number of blocks written: " + curCount;
    }

    private static void addBoundaries(Boundary boundary, List<Boundary> boundaries) {
        int i = 0;
        for (; i < boundaries.size(); i++) {
            if (boundary.getStart() < boundaries.get(i).getStart()) {
                break;
            }
        }
        boundaries.add(i, boundary);
    }

    private static boolean hasRegion(List<Boundary> boundaries, int sizeBlock, int sizeFileString) {
        int prevEnd = 0;
        for (Boundary curBoundary : boundaries) {
            if (curBoundary.getStart() - sizeBlock > prevEnd) {
                return true;
            }
            prevEnd = curBoundary.getEnd();
        }
        return prevEnd + sizeBlock < sizeFileString;
    }

    static String toFormatWithoutPunctuation(String string) {
        //Remove extra spaces, line breaks, lots of capital letters, punctuation and non-Cyrillic
        //To study works in other languages, you should change the strings extraTitleCase and punctuation
        //For example, for English: String extraTitleCase = "[A-Z]{2,}";
        String extraTitleCase = "[А-Я]{2,}";
        String whitespaceInBeginLine = "\n\\s";
        //For example, for English: String punctuation = "[[^a-zA-Z]&&\\S]";
        String punctuation = "[[^а-яА-Я]&&\\S]";
        String extraNewLines = "(\r\n)+";
        String extraWhitespaces = "( )+";
        String oldString = string;
        String newString = oldString.replaceAll(extraTitleCase, "");

        do {
            oldString = newString;
            newString = oldString.replaceAll(whitespaceInBeginLine, "\n");
            newString = newString.replaceAll(punctuation, "");
        } while (!oldString.equals(newString));

        newString = oldString.replaceAll(extraWhitespaces, " ").replaceAll(extraNewLines, "\r\n");
        return newString;
    }
}
