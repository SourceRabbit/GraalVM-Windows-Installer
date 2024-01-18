package graalvminstallerforwindows.Core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author Nikos Siatras
 */
public class FileUtils
{

    public FileUtils()
    {

    }

    /**
     * Returns true if the directory exists
     *
     * @param path
     * @return
     */
    public boolean CheckIfDirectoryExists(String path)
    {
        File dir = new File(path);
        return dir.exists();
    }

    /**
     * Returns true if the directory exists and it is empty
     *
     * @param path
     * @return
     */
    public boolean CheckIfDirectoryExistsAndItIsEmpty(String path)
    {
        File dir = new File(path);
        boolean dirIsEmpty = false;
        if (dir.exists())
        {
            dirIsEmpty = dir.listFiles().length == 0;
        }

        return dirIsEmpty;
    }

    /**
     *
     * @param sourceDirPath
     * @param destinationDirPath
     * @return
     */
    public boolean MoveDirContentsToOtherDir(String sourceDirPath, String destinationDirPath)
    {
        File sourceFile = new File(sourceDirPath);
        File destFile = new File(destinationDirPath);

        if (sourceFile.isDirectory())
        {
            String destinationDir = sourceFile.getAbsolutePath().replace(sourceDirPath, destinationDirPath);
            File tempDestDir = new File(destinationDir);
            tempDestDir.mkdir();

            for (File file : sourceFile.listFiles())
            {
                MoveDirContentsToOtherDir(file.getAbsolutePath(), destinationDir + "\\" + file.getName());
            }

            try
            {
                Files.delete(Paths.get(sourceFile.getPath()));
            }
            catch (IOException ex)
            {
                return false;
            }
        }
        else
        {
            try
            {
                Files.move(Paths.get(sourceFile.getPath()), Paths.get(destFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
                return true;
            }
            catch (IOException e)
            {
                return false;
            }
        }
        return false;

    }

}
