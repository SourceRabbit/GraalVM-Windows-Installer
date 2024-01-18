package graalvminstallerforwindows.Core;

import graalvminstallerforwindows.Core.Utilities.DosPromt;
import graalvminstallerforwindows.Core.Utilities.FileUtils;
import graalvminstallerforwindows.Core.Utilities.Unzipper;
import graalvminstallerforwindows.UI.UITools;
import graalvminstallerforwindows.UI.frmDownloadFile;
import graalvminstallerforwindows.UI.frmMain;
import java.io.File;
import java.io.FilenameFilter;

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

    public void IntallGrallVM() throws Exception
    {
        boolean stepFinished = false;
        STEP1_PrepareInstallationFolder();

        stepFinished = STEP2_DownloadGraalVM();
        if (!stepFinished)
        {
            return;
        }

        stepFinished = STEP3_UnzipDownloadedFile();
        if (!stepFinished)
        {
            return;
        }

        stepFinished = STEP4_MoveUnzippedGraalVMToInstallationPath();
        if (!stepFinished)
        {
            return;
        }

        STEP5_SetJavaHomePathAndRunJarFix();

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
        String javaHomeCommand = "setx JAVA_HOME " + fInstallationPath;
        DosPromt.ExecuteDOSPromt(javaHomeCommand);

        String pathCommand = "setx path \"%PATH%; " + fInstallationPath + "\\bin\" /m";
        DosPromt.ExecuteDOSPromt(javaHomeCommand);

        // Call Jar Fix
        DosPromt.ExecuteDOSPromt("Prerequisites/jarfix.exe/s");
        return true;
    }

}
