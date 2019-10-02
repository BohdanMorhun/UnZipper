
import org.junit.Test;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;


public class UnZipFileTest {

    @Test
    public void testFileExists() {

        File file = new File("C:\\Users\\BohdanMorhun\\Documents\\TaskZip.zip");

        assertTrue(file.exists());
    }

    @Test
    public void testGetPath() {
        String path = "C:\\Users\\BohdanMorhun\\Documents\\TaskZip.zip";

        File file = new File(path);
        String absolutePath = file.getAbsolutePath();

//        log.info(absolutePath);

        assertTrue(absolutePath.endsWith("TaskZip.zip"));
    }

    @Test(expected = FileNotFoundException.class)
    public void testReadFile() throws IOException {

        try (FileReader reader = new FileReader("Java.zip")) {
            reader.read();
        }
    }

    @Test(timeout = 100)
    public void testNotNull(){

        File file = new File("C:\\Users\\BohdanMorhun\\Documents\\TaskZip.zip");

        assertNotNull(file);
    }
}

