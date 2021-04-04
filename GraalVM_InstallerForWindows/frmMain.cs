using GraalVM_InstallerForWindows.Core;
using ICSharpCode.SharpZipLib.Core;
using ICSharpCode.SharpZipLib.Zip;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Net;
using System.Threading;
using System.Windows.Forms;

namespace GraalVM_InstallerForWindows
{
    public partial class frmMain : Form
    {

        private bool fFinishedForUI = false;
        private ManualResetEvent fManualResetEvent = new ManualResetEvent(false);
        private Installer fInstaller;
        private string fInstallationFolder;
        private string fDownloadFilePath;
      

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
            fInstaller = new Installer();

            Dictionary<string, string> versions = GraalVMVersionManager.GraalVMVersions;

            foreach (string key in versions.Keys)
            {
                comboBoxVersions.Items.Add(key);
            }

            comboBoxVersions.SelectedIndex = 0;
        }

        private void buttonInstall_Click(object sender, EventArgs e)
        {
            comboBoxVersions.Enabled = false;
            textBoxInstallationPath.Enabled = false;


            buttonInstall.Enabled = false;
            buttonExit.Enabled = false;


            fInstaller.InstallGraalVM(comboBoxVersions.SelectedItem.ToString(), textBoxInstallationPath.Text);
        }

        



        /// <summary>
        /// Let user to browse the folder he wants to install GrallVM
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void buttonBrowse_Click(object sender, EventArgs e)
        {
            using (var fbd = new FolderBrowserDialog())
            {
                DialogResult result = fbd.ShowDialog();

                if (result == DialogResult.OK && !string.IsNullOrWhiteSpace(fbd.SelectedPath))
                {
                    textBoxInstallationPath.Text = fbd.SelectedPath;   
                    if (textBoxInstallationPath.Text.EndsWith("\\"))
                    {
                        textBoxInstallationPath.Text = textBoxInstallationPath.Text.Substring(0, textBoxInstallationPath.Text.Length - 1);
                    }
                }
            }
        }



        /// <summary>
        /// This timer updates the UI during installation
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void timer1_Tick(object sender, EventArgs e)
        {
            
            if (fInstaller != null)
            {
                labelStatus.Text = fInstaller.Status;
                progressBarDownload.Value = fInstaller.Progress;

                if(fInstaller.IsFinished && !fFinishedForUI)
                {
                    fFinishedForUI = true;
                    MessageBox.Show(this, "Installation finished!\nPlease restart to take effect.", "Finished");
                    this.Close();
                }

            }
        }
    }
}
