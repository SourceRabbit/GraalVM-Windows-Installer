package graalvminstallerforwindows.Core.Utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/*
    Copyright (C) 2024 Nikolaos Siatras
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    This software is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program.
 */
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
