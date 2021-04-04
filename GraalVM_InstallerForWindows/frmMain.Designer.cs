
namespace GraalVM_InstallerForWindows
{
    partial class frmMain
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.label1 = new System.Windows.Forms.Label();
            this.comboBoxVersions = new System.Windows.Forms.ComboBox();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.buttonInstall = new System.Windows.Forms.Button();
            this.buttonExit = new System.Windows.Forms.Button();
            this.progressBarDownload = new System.Windows.Forms.ProgressBar();
            this.labelStatus = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(12, 12);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(138, 20);
            this.label1.TabIndex = 0;
            this.label1.Text = "Graal VM Version:";
            // 
            // comboBoxVersions
            // 
            this.comboBoxVersions.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBoxVersions.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.comboBoxVersions.FormattingEnabled = true;
            this.comboBoxVersions.Location = new System.Drawing.Point(156, 9);
            this.comboBoxVersions.Name = "comboBoxVersions";
            this.comboBoxVersions.Size = new System.Drawing.Size(410, 28);
            this.comboBoxVersions.TabIndex = 1;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(13, 43);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(309, 13);
            this.label2.TabIndex = 2;
            this.label2.Text = "Select the GraalVM Version you want to install on your computer";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(12, 79);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(127, 20);
            this.label3.TabIndex = 3;
            this.label3.Text = "Installation Path:";
            // 
            // textBox1
            // 
            this.textBox1.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.textBox1.Location = new System.Drawing.Point(156, 78);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(410, 26);
            this.textBox1.TabIndex = 4;
            this.textBox1.Text = "C:\\GraalVMTest";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(12, 110);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(297, 13);
            this.label4.TabIndex = 5;
            this.label4.Text = "We suggest you install the GraalVM in the \"C:\\GraalVM\" path";
            // 
            // buttonInstall
            // 
            this.buttonInstall.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonInstall.Location = new System.Drawing.Point(15, 144);
            this.buttonInstall.Name = "buttonInstall";
            this.buttonInstall.Size = new System.Drawing.Size(109, 29);
            this.buttonInstall.TabIndex = 6;
            this.buttonInstall.Text = "Install";
            this.buttonInstall.UseVisualStyleBackColor = true;
            this.buttonInstall.Click += new System.EventHandler(this.buttonInstall_Click);
            // 
            // buttonExit
            // 
            this.buttonExit.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonExit.Location = new System.Drawing.Point(130, 144);
            this.buttonExit.Name = "buttonExit";
            this.buttonExit.Size = new System.Drawing.Size(88, 29);
            this.buttonExit.TabIndex = 7;
            this.buttonExit.Text = "Exit";
            this.buttonExit.UseVisualStyleBackColor = true;
            // 
            // progressBarDownload
            // 
            this.progressBarDownload.Location = new System.Drawing.Point(15, 206);
            this.progressBarDownload.Name = "progressBarDownload";
            this.progressBarDownload.Size = new System.Drawing.Size(550, 23);
            this.progressBarDownload.TabIndex = 8;
            // 
            // labelStatus
            // 
            this.labelStatus.AutoSize = true;
            this.labelStatus.Location = new System.Drawing.Point(13, 190);
            this.labelStatus.Name = "labelStatus";
            this.labelStatus.Size = new System.Drawing.Size(52, 13);
            this.labelStatus.TabIndex = 9;
            this.labelStatus.Text = "Status: ...";
            // 
            // frmMain
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(582, 243);
            this.Controls.Add(this.labelStatus);
            this.Controls.Add(this.progressBarDownload);
            this.Controls.Add(this.buttonExit);
            this.Controls.Add(this.buttonInstall);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.comboBoxVersions);
            this.Controls.Add(this.label1);
            this.Name = "frmMain";
            this.ShowIcon = false;
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "GraalVM Windows Installer";
            this.Load += new System.EventHandler(this.frmMain_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.ComboBox comboBoxVersions;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Button buttonInstall;
        private System.Windows.Forms.Button buttonExit;
        private System.Windows.Forms.ProgressBar progressBarDownload;
        private System.Windows.Forms.Label labelStatus;
    }
}

