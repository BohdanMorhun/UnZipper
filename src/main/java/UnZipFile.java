
import lombok.extern.log4j.Log4j;
import org.apache.log4j.BasicConfigurator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


@Log4j
public class UnZipFile {

    private static final int BUFFER_SIZE = 2048;
    private static final String ZIP_FILE = "C:\\Users\\BohdanMorhun\\Documents\\TaskZip.zip";
    private static final String DESTINATION_DIRECTORY = "C:\\Users\\BohdanMorhun\\Documents\\output";;
    private static final String ZIP_EXTENSION = ".zip";

    public static void main(String[] args) throws IOException {

        args[0] = ZIP_FILE;
        args[1] = DESTINATION_DIRECTORY;

        BasicConfigurator.configure();

        UnZipFile unzip = new UnZipFile();

        if (unzip.unzipToFile(ZIP_FILE, DESTINATION_DIRECTORY)) {
            log.info("Succefully unzipped to the directory "
                    + DESTINATION_DIRECTORY);
        } else {
            log.info("There was some error during extracting archive to the directory "
                    + DESTINATION_DIRECTORY);
        }
    }

    private boolean unzipToFile(final String srcZipFileName, final String destDirectoryName) throws IOException{
        // create the destination directory structure (if needed)
        File destDirectory = new File(destDirectoryName);

        // open archive for reading
        File file = new File(srcZipFileName);
        ZipFile zipFile = new ZipFile(file, ZipFile.OPEN_READ);

        //for every zip archive entry do
        Enumeration<? extends ZipEntry> zipFileEntries = zipFile.entries();
        InputStream bufIS = null;
        while (zipFileEntries.hasMoreElements()) {
            ZipEntry entry = zipFileEntries.nextElement();

            UnZipFile.log.info("\tExtracting entry: " + entry);

            //create destination file
            File destFile = new File(destDirectory, entry.getName());

            if (!entry.isDirectory()) {
                bufIS = new BufferedInputStream(
                        zipFile.getInputStream(entry));
                int currentByte;

                // buffer for writing file
                byte[] data = new byte[BUFFER_SIZE];

                // write the current file to disk
                OutputStream fOS = new FileOutputStream(destFile);
                try (OutputStream bufOS = new BufferedOutputStream(fOS, BUFFER_SIZE)) {

                    while ((currentByte = bufIS.read(data, 0, BUFFER_SIZE)) != -1) {
                        bufOS.write(data, 0, currentByte);
                    }
                }
                // recursively unzip files
                if (entry.getName().toLowerCase().endsWith(ZIP_EXTENSION)) {
                    String zipFilePath = destDirectory.getPath() + File.separatorChar + entry.getName();

                    unzipToFile(zipFilePath, zipFilePath.substring(0,
                            zipFilePath.length() - ZIP_EXTENSION.length()));
                }
            }
            if (bufIS != null) {
                bufIS.close();
            }
        }
        return true;
        }
    }


