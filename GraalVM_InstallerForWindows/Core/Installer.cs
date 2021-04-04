/*
Copyright (C) 2021  Nikos Siatras
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
SourceRabbit GCode Sender is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program.
*/

using GraalVM_InstallerForWindows.Core;
using ICSharpCode.SharpZipLib.Core;
using ICSharpCode.SharpZipLib.Zip;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace GraalVM_InstallerForWindows
{
    public class Installer
    {
        private string fStatus = "";
        private int fProgressValue = 0;
        private string fVersionToInstall, fInstallationFolder;

        private Thread fInstallationThread;
        private ManualResetEvent fWaitStepToFinish = new ManualResetEvent(false);
        private bool fFinished = false;


        public Installer()
        {

        }


        public void InstallGraalVM(string versionName, string installationPath)
        {
            fVersionToInstall = versionName;
            fInstallationFolder = installationPath;

            fInstallationThread = new Thread(InstallationThreadStart);
            fInstallationThread.Start();
        }


        private void InstallationThreadStart()
        {
            fWaitStepToFinish.Reset();
            Step1_DownloadGraalVM();
            fWaitStepToFinish.WaitOne();

            fWaitStepToFinish.Reset();
            Step2_UnzipGraalVM();
            fWaitStepToFinish.WaitOne();


            fWaitStepToFinish.Reset();
            Step3_SetEnvironmentalVariables();
            fWaitStepToFinish.WaitOne();

            fWaitStepToFinish.Reset();
            Step4_RunJarFix();
            fWaitStepToFinish.WaitOne();


            // Set fFinished to true at the end!!!
            fFinished = true;
        }


        private void Step1_DownloadGraalVM()
        {
            fStatus = "Downloading " + fVersionToInstall + "...";

            string webPathToGraalVMZip = GraalVMVersionManager.GraalVMVersions[fVersionToInstall];
            string downloadPathToDisk = fInstallationFolder + "\\" + fVersionToInstall;

            // Create installation folder if doesn't exit 
            if(!Directory.Exists(fInstallationFolder))
            {
                Directory.CreateDirectory(fInstallationFolder);
            }

            if (!File.Exists(downloadPathToDisk))
            {
                WebClient webClient = new WebClient();
                webClient.DownloadProgressChanged += new DownloadProgressChangedEventHandler(Event_DownloadProgressChanged);
                webClient.DownloadFileCompleted += new AsyncCompletedEventHandler(Event_DownloadFileFinished);
                webClient.DownloadFileAsync(new Uri(webPathToGraalVMZip), downloadPathToDisk);
            }
            else
            {
                fProgressValue = 100;
                fWaitStepToFinish.Set();
            }
        }

        private void Event_DownloadProgressChanged(object sender, DownloadProgressChangedEventArgs e)
        {
            fProgressValue = e.ProgressPercentage;
        }

        private void Event_DownloadFileFinished(object sender, AsyncCompletedEventArgs e)
        {
            fWaitStepToFinish.Set();
        }


        private void Step2_UnzipGraalVM()
        {
            fProgressValue = 0;
            fStatus = "Extracting GraalVM " + fVersionToInstall + ". Please wait...";
            string zipPath = fInstallationFolder + "\\" + fVersionToInstall;

            Stream zipStream = new FileStream(zipPath, FileMode.Open);

            string outFolder = fInstallationFolder;
            outFolder = outFolder.EndsWith("\\") ? outFolder.Substring(0, outFolder.Length - 1) : outFolder;


            // Get how many files needs to be extracted
            int totalFilesToExtract = 0;
            using (var zipInputStream = new ZipInputStream(zipStream))
            {
                while (zipInputStream.GetNextEntry() is ZipEntry zipEntry)
                {
                    totalFilesToExtract += 1;
                }
            }

            // Extract files
            zipStream = new FileStream(zipPath, FileMode.Open);
            int filesExtracted = 0;
            using (var zipInputStream = new ZipInputStream(zipStream))
            {

                while (zipInputStream.GetNextEntry() is ZipEntry zipEntry)
                {
                    string entryFileName = zipEntry.Name;
                    entryFileName = entryFileName.Substring(entryFileName.IndexOf("/"));
                    entryFileName = entryFileName.Replace("/", "\\");

                    // 4K is optimum
                    var buffer = new byte[4096];

                    // Manipulate the output filename here as desired.
                    var fullZipToPath = outFolder + entryFileName;
                    var directoryName = Path.GetDirectoryName(fullZipToPath);
                    if (directoryName.Length > 0)
                    {
                        Directory.CreateDirectory(directoryName);
                    }

                    // Skip directory entry
                    if (Path.GetFileName(fullZipToPath).Length == 0)
                    {
                        continue;
                    }

                    using (FileStream streamWriter = File.Create(fullZipToPath))
                    {
                        StreamUtils.Copy(zipInputStream, streamWriter, buffer);
                    }

                    filesExtracted += 1;

                    fProgressValue = (int)(filesExtracted * 100) / totalFilesToExtract;
                }
            }

            fProgressValue = 100;
            fWaitStepToFinish.Set();
        }


        private void Step3_SetEnvironmentalVariables()
        {
            fStatus = "Setting environmental variables...";

            // Create the JAVA_HOME environmental variable
            string environmentalVariableName = "JAVA_HOME";
            Environment.SetEnvironmentVariable(environmentalVariableName, fInstallationFolder, EnvironmentVariableTarget.Machine);

            fProgressValue = 50;

            // Create Path
            // Example C:\GraalVM\bin
            string pathVar = fInstallationFolder + "\\bin\\";
            var name = "PATH";
            var scope = EnvironmentVariableTarget.Machine;
            var oldValue = Environment.GetEnvironmentVariable(name, scope);
            if (!oldValue.Contains(pathVar))
            {
                if (!oldValue.EndsWith(";"))
                {
                    oldValue = oldValue + ";";
                }
                var newValue = oldValue + pathVar + ";";
                Environment.SetEnvironmentVariable(name, newValue, scope);
            }

            fProgressValue = 100;
            fWaitStepToFinish.Set();
        }

        private void Step4_RunJarFix()
        {
            string appPath = AppDomain.CurrentDomain.BaseDirectory;
            string jarFixPath = appPath + "JarFix\\jarfix.exe";
            System.Diagnostics.Process.Start(jarFixPath);
            fWaitStepToFinish.Set();
        }


        public string Status
        {
            get { return fStatus; }
        }

        public int Progress
        {
            get { return fProgressValue; }
        }

        public bool IsFinished
        {
            get { return fFinished; }
        }
    }
}
