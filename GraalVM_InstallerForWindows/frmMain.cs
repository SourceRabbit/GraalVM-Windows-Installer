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
     

        public frmMain()
        {
            InitializeComponent();

            Initialize();
        }

        private void frmMain_Load(object sender, EventArgs e)
        {
            labelStatus.Text = "";
            this.Text = this.Text + " " + Config.fVersion;
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
