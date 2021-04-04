using GraalVM_InstallerForWindows.Core;
using ICSharpCode.SharpZipLib.Core;
using ICSharpCode.SharpZipLib.Zip;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Net;
using System.Windows.Forms;

namespace GraalVM_InstallerForWindows
{
    public partial class frmMain : Form
    {

        private string fInstallationFolder;
        private string fDownloadFilePath;
        private GraalVMVersionManager fVersionManager;

        public frmMain()
        {
            InitializeComponent();

            Initialize();
        }

        private void frmMain_Load(object sender, EventArgs e)
        {
            labelStatus.Text = "";
        }


        private void Initialize()
        {
            fVersionManager = new GraalVMVersionManager();
            Dictionary<string, string> versions = fVersionManager.GraalVMVersions;

            foreach (string key in versions.Keys)
            {
                comboBoxVersions.Items.Add(key);
            }

            comboBoxVersions.SelectedIndex = 0;
        }

        private void buttonInstall_Click(object sender, EventArgs e)
        {

            comboBoxVersions.Enabled = false;
            textBox1.Enabled = false;


            fInstallationFolder = textBox1.Text;
            fDownloadFilePath = fInstallationFolder + "\\" + comboBoxVersions.Text;

            //Step1_DownloadGraalVM();
            //Step2_UnzipGraalVM();

            Step3_InstallGraalVM();
        }


        private void Step1_DownloadGraalVM()
        {
            buttonInstall.Enabled = false;
            buttonExit.Enabled = false;

            string version = comboBoxVersions.SelectedItem.ToString();
            string downloadURL = fVersionManager.GraalVMVersions[version];

            labelStatus.Text = "Downloading " + version + ". Please wait...";

            WebClient webClient = new WebClient();
            webClient.DownloadProgressChanged += new DownloadProgressChangedEventHandler(Event_DownloadProgressChanged);
            webClient.DownloadFileCompleted += new AsyncCompletedEventHandler(Event_DownloadFileCompleted);
            webClient.DownloadFileAsync(new Uri(downloadURL), fDownloadFilePath);
        }


        private void Step2_UnzipGraalVM()
        {
            labelStatus.Text = "Extracting file. Please wait...";
            progressBarDownload.Value = 10;

            // Unzip the file
            Stream st = new FileStream(fDownloadFilePath,FileMode.Open);
            UnzipFile(st, fInstallationFolder+"\\");

            Step3_InstallGraalVM();
        }


        private void Step3_InstallGraalVM()
        {
            labelStatus.Text = "Installing GraalVM...";

            // Create the JAVA_HOME environmental variable
            string environmentalVariableName = "JAVA_HOME";
            string value = Environment.GetEnvironmentVariable(environmentalVariableName);

            // If variable doesn't exit then create it
            Environment.SetEnvironmentVariable(environmentalVariableName, fInstallationFolder, EnvironmentVariableTarget.Machine);
            //Environment.SetEnvironmentVariable("MY_VARIABLE", "value", EnvironmentVariableTarget.Machine);

        }


        // Event to track the Download Progress
        private void Event_DownloadProgressChanged(object sender, DownloadProgressChangedEventArgs e)
        {
            progressBarDownload.Value = e.ProgressPercentage;
        }

        private void Event_DownloadFileCompleted(object sender, AsyncCompletedEventArgs e)
        {
            labelStatus.Text = "Download finished !";
            Step2_UnzipGraalVM();
        }



        public void UnzipFile(Stream zipStream, string outFolder)
        {

            if (outFolder.EndsWith("\\"))
            {
                outFolder = outFolder.Substring(0, outFolder.Length - 1);
            }

            using (var zipInputStream = new ZipInputStream(zipStream))
            {
                while (zipInputStream.GetNextEntry() is ZipEntry zipEntry)
                {
                    string entryFileName = zipEntry.Name;
                    entryFileName = entryFileName.Substring(entryFileName.IndexOf("/"));
                    entryFileName =  entryFileName.Replace("/", "\\");
                    
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

                    // Unzip file in buffered chunks. This is just as fast as unpacking
                    // to a buffer the full size of the file, but does not waste memory.
                    // The "using" will close the stream even if an exception occurs.
                    using (FileStream streamWriter = File.Create(fullZipToPath))
                    {
                        StreamUtils.Copy(zipInputStream, streamWriter, buffer);
                    }
                }
            }

            progressBarDownload.Value = 100;
        }


    }
}
