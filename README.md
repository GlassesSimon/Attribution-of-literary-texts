# Attribution of literary texts
These programs were developed as part of the final qualification work "Using the methods of theoretical and information classification to solve the problems of literary criticism" by Shchegoleva Angelina Ivanovna FIT NSU 2021.
## Text Editor
- This program is designed for the initial processing of literary texts, which includes removing punctuation, extra spaces, extra line breaks and numbers from texts, as well as dividing texts into a certain number of non-overlapping blocks of a given size.
- As an input, the program accepts a directory with files for processing, the required block size in bits and the number of required blocks.
- As a result of the work, the program creates the result directory, into which all the resulting files with blocks are written, while maintaining the file hierarchy. It also displays information on how many blocks the original files were divided into.
## Training Sample
- This program for creating training and test samples of blocks from different files of the same author in one file is a training sample.
- The program takes as input the name for the future training sample, the number of files for the training sample, the number of blocks that will be taken from each file, as well as the names of these files.
- As a result of the work, the program creates a file with a training sample.
## Compression Test
- To use this program, the 7-Zip archiver must be installed on the computer. 
- This program is designed for sequential compression of files with training samples with blocks of the research composition, as well as further calculation of the resulting sizes of compressed files and recording these sizes in a separate file.
- The program accepts the names of training samples and the test sample under study as input.
- As a result of the work, the program creates a file with differences in lengths for each training sample of a separately compressed training sample and a compressed training sample with a block from the test sample appended to the end.
## Compression Analysis
- This program is designed to compare the sizes of compressed texts obtained from multiple files.
- The program accepts the expected name of the file with the analysis results as input, as well as files for analysis (the results of the Compression Test program).
- As a result of the work, the program creates a file with the results of the analysis, in which the number of test sample blocks will be written for each training sample, which are better compressed with this training sample in the format "name_of_training_sample_name_test_sample.txt number_blocks".