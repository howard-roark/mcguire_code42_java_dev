package test;

import main.FileScanner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static main.FileScanner.ScanResult;

public class FileScannerTest {
    private static ScanResult sr;

    @BeforeClass
    public void setUp() throws Exception {
        String parDir = new File("").getAbsolutePath();
        String pathToSampleDir = parDir + "/src/test/test_dir";

        FileScanner fs = new FileScanner();
        sr = fs.scan(pathToSampleDir);
    }

    @Test
    public void testGetNumFiles() {
        assert sr.getNumFiles() == 3;
    }

    @Test
    public void testGetNumDirectories() {
        assert sr.getNumDirectories() == 4;
    }

    @Test
    public void testGetTotalBytes() {
        assert sr.getTotalBytes() == 32562;
    }

    @Test
    public void testGetAvgBytes() {
        assert sr.getAvgBytes() == 10854;
    }
}
