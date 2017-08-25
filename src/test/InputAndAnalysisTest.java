package test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import main.InputAndAnalysis;

import java.io.File;

public class InputAndAnalysisTest {

    private InputAndAnalysis iaa;

    @BeforeClass
    public void setUp() throws Exception {
        String parDir = new File("").getAbsolutePath();
        String pathToSampleData = parDir + "/src/test/sample_data.txt";

        this.iaa = new InputAndAnalysis(pathToSampleData);
        this.iaa.run();
    }

    @Test
    public void testGetTotal() {
        assert this.iaa.getTotal() == 16.2;
    }

    @Test
    public void testGetAverage() {
        assert this.iaa.getAverage() == 5.4;
    }

    @Test
    public void testGetCountOfNumbers() {
        assert this.iaa.getCountOfNumbers() == 3;
    }

    @Test
    public void testContainsFullLine() {
        assert this.iaa.contains("foo") == true;
    }

    @Test
    public void testContainsPartialLine() {
        assert this.iaa.contains("fox") == true;
    }

    @Test
    public void testContainsNot() {
        assert this.iaa.contains("cats") == false;
    }
}
