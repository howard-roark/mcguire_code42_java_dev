package main;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.*;

/**
 * Scan all files and directories below a specified path on a local filesystem.
 */
public class FileScanner {

    public static ScanResult scan(String path) {
        try {
            Path p = Paths.get(path);
            ScanResult sr = new ScanResult();
            Files.walkFileTree(p, sr);
            return new ScanResult();
        } catch (InvalidPathException ipe) {
            System.err.println("Invalid path passed in.");
            ipe.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Make use of FileVisitor interface to return the following information as
     * the file system is traversed depth first from the path passed in by the user.
     * (Order of sub-directories is not guaranteed)
     *
     *      - Num of directories visited during traversal
     *      - Num of files visited during traversal
     *          - Links are not followed because they may lead to files outside the desired path
     *          - Only regular files are counted
     *      - Total number of bytes of the files counted during traversal
     *      - Avg size of file counted in bytes
     */
    public static class ScanResult implements FileVisitor {
        private static int numDirs = 0;
        private static int numFiles = 0;
        private static long totalBytes = 0;

        // return the number of files scanned
        public int getNumFiles() {
            return this.numFiles;
        }

        // return the number of directories scanned
        public int getNumDirectories() {
            return this.numDirs;
        }

        // return the total number of bytes contained within all scanned _files_
        public long getTotalBytes() {
            return this.totalBytes;
        }

        // return the average size of the scanned _files_
        public long getAvgBytes() {
            try {
                return this.totalBytes / this.numFiles;
            } catch (ArithmeticException ae) {
                System.err.println("Attempting to divide size of files by 0 files");
                ae.printStackTrace();
                return Long.parseLong(null);
            }
        }

        /**
         * Invoked for a directory before entries in the directory are visited.
         * <p>
         * <p> If this method returns {@link FileVisitResult#CONTINUE CONTINUE},
         * then entries in the directory are visited. If this method returns {@link
         * FileVisitResult#SKIP_SUBTREE SKIP_SUBTREE} or {@link
         * FileVisitResult#SKIP_SIBLINGS SKIP_SIBLINGS} then entries in the
         * directory (and any descendants) will not be visited.
         *
         * @param dir   a reference to the directory
         * @param attrs the directory's basic attributes
         * @return the visit result
         * @throws IOException if an I/O error occurs
         */
        @Override
        public FileVisitResult preVisitDirectory(Object dir, BasicFileAttributes attrs) {
            numDirs++;
            return CONTINUE;
        }

        /**
         * Invoked for a file in a directory.
         *
         * @param file  a reference to the file
         * @param attrs the file's basic attributes
         * @return the visit result
         * @throws IOException if an I/O error occurs
         */
        @Override
        public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
            if (attrs.isRegularFile()) {
                this.numFiles++;
                totalBytes = this.getTotalBytes() + attrs.size();
            }
            return CONTINUE;
        }

        /**
         * Invoked for a file that could not be visited. This method is invoked
         * if the file's attributes could not be read, the file is a directory
         * that could not be opened, and other reasons.
         *
         * @param file a reference to the file
         * @param exc  the I/O exception that prevented the file from being visited
         * @return the visit result
         * @throws IOException if an I/O error occurs
         */
        @Override
        public FileVisitResult visitFileFailed(Object file, IOException exc) throws IOException {
            System.err.println(exc);
            return CONTINUE;
        }

        /**
         * Invoked for a directory after entries in the directory, and all of their
         * descendants, have been visited. This method is also invoked when iteration
         * of the directory completes prematurely (by a {@link #visitFile visitFile}
         * method returning {@link FileVisitResult#SKIP_SIBLINGS SKIP_SIBLINGS},
         * or an I/O error when iterating over the directory).
         *
         * @param dir a reference to the directory
         * @param exc {@code null} if the iteration of the directory completes without
         *            an error; otherwise the I/O exception that caused the iteration
         *            of the directory to complete prematurely
         * @return the visit result
         * @throws IOException if an I/O error occurs
         */
        @Override
        public FileVisitResult postVisitDirectory(Object dir, IOException exc) throws IOException {
            return CONTINUE;
        }
    }
}