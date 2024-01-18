package graalvminstallerforwindows.Core;

import graalvminstallerforwindows.Core.Utilities.DosPromt;
import graalvminstallerforwindows.Core.Utilities.EnvironmentVariablesManager;
import graalvminstallerforwindows.Core.Utilities.FileUtils;
import graalvminstallerforwindows.Core.Utilities.Unzipper;
import graalvminstallerforwindows.UI.UITools;
import graalvminstallerforwindows.UI.frmDownloadFile;
import graalvminstallerforwindows.UI.frmMain;
import java.io.File;
import java.io.FilenameFilter;

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
public class Installer
{

    private final String fDownloadURL, fInstallationPath;
    private final String fDownloadedZipFilePath;
    private final FileUtils fFileUtils;

    public Installer(String downloadURL, String installationPath)
    {
        fDownloadURL = downloadURL;
        fInstallationPath = installationPath;
        fDownloadedZipFilePath = fInstallationPath + "\\GraalVMDownload.zip";
        fFileUtils = new FileUtils();
    }

    /**
     * Returns true if installation finishes sucessfully
     *
     * @return
     * @throws Exception
     */
    public boolean IntallGrallVM() throws Exception
    {
        boolean stepFinished = false;
        STEP1_PrepareInstallationFolder();

        stepFinished = STEP2_DownloadGraalVM();
        if (!stepFinished)
        {
            return false;
        }

        stepFinished = STEP3_UnzipDownloadedFile();
        if (!stepFinished)
        {
            return false;
        }

        stepFinished = STEP4_MoveUnzippedGraalVMToInstallationPath();
        if (!stepFinished)
        {
            return false;
        }

        STEP5_SetJavaHomePathAndRunJarFix();

        return true;
    }

    private void STEP1_PrepareInstallationFolder() throws Exception
    {
        // Check if installation directory exists and it is empty!
        File installationDir = new File(fInstallationPath);

        if (!fFileUtils.CheckIfDirectoryExists(fInstallationPath))
        {
            // Create installationDir if not exists
            installationDir.mkdir();
        }

        if (!fFileUtils.CheckIfDirectoryExistsAndItIsEmpty(fInstallationPath))
        {
            throw new Exception("Directory '" + fInstallationPath + "' is not empty!\nDelete any files and directories inside the installation path and try again.");
        }
    }

    private boolean STEP2_DownloadGraalVM() throws Exception
    {
        boolean downloadFinishedSuccessfully = true;

        final String savePath = fDownloadedZipFilePath;

        frmDownloadFile frm = new frmDownloadFile(frmMain.fInstance, true, fDownloadURL, savePath);
        downloadFinishedSuccessfully = frm.OpenAndDownloadFile();

        return downloadFinishedSuccessfully;
    }

    private boolean STEP3_UnzipDownloadedFile() throws Exception
    {
        UITools.ShowPleaseWaitDialog("Please wait", "Extracting GraalVMDownload.zip<br>This can take a while...", frmMain.fInstance, () ->
        {
            try
            {
                Unzipper.Unzip(fDownloadedZipFilePath, fInstallationPath + "\\Unzipped");

                // Delete GraalVMDownload.zip
                File fileToDelete = new File(fDownloadedZipFilePath);
                fileToDelete.delete();
            }
            catch (Exception ex)
            {
            }
        });

        return true;
    }

    private boolean STEP4_MoveUnzippedGraalVMToInstallationPath() throws Exception
    {
        File installationDir = new File(fInstallationPath + "\\Unzipped");

        String[] directories = installationDir.list(new FilenameFilter()
        {
            @Override
            public boolean accept(File current, String name)
            {
                return new File(current, name).isDirectory();
            }
        });

        String sourceDir = fInstallationPath + "\\Unzipped\\" + directories[0];
        fFileUtils.MoveDirContentsToOtherDir(sourceDir, fInstallationPath);

        return true;
    }

    private boolean STEP5_SetJavaHomePathAndRunJarFix() throws Exception
    {
        try
        {
            EnvironmentVariablesManager.SetEnvironmentVariable("JAVA_HOME", fInstallationPath);
            EnvironmentVariablesManager.AddEnvironmentVariable("PATH", fInstallationPath + "\\bin");
        }
        catch (Exception ex)
        {
            throw new Exception("Cannot set environment variables!");
        }

        // Call Jar Fix
        DosPromt.ExecuteDOSPromt("Prerequisites/jarfix.exe /s");
        return true;
    }

}
