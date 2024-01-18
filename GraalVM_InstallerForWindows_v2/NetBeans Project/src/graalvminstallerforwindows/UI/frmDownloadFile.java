package graalvminstallerforwindows.UI;

import graalvminstallerforwindows.Core.Threading.ManualResetEvent;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.SwingUtilities;

/**
 *
 * @author Nikos Siatras
 */
public class frmDownloadFile extends javax.swing.JDialog
{

    private final String fURLOfFileToDownload;
    private final String fPathToSaveFile;

    private Thread fDownloadThread;
    private boolean fKeepDownloadThreadRunning = false;
    private ManualResetEvent fWaitForDownloadToFinish;

    /**
     * Creates new form frmDownloadFile
     *
     * @param parent
     * @param modal
     * @param urlOfFileToDownload
     * @param pathToSaveTheFile
     */
    public frmDownloadFile(java.awt.Frame parent, boolean modal, String urlOfFileToDownload, String pathToSaveTheFile)
    {
        super(parent, modal);
        initComponents();

        fURLOfFileToDownload = urlOfFileToDownload;
        fPathToSaveFile = pathToSaveTheFile;

        jLabelFilePath.setText("<html><p style=\"width:280px\">" + fURLOfFileToDownload + "</p></html>");

        this.setLocationRelativeTo(parent);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jLabelFilePath = new javax.swing.JLabel();
        jButtonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("File Download");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowOpened(java.awt.event.WindowEvent evt)
            {
                formWindowOpened(evt);
            }
        });

        jProgressBar1.setStringPainted(true);

        jLabel1.setText("Downloading File:");

        jLabelFilePath.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelFilePath.setText("FILE_PATH_GOES_HERE");
        jLabelFilePath.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap())
                    .addComponent(jLabelFilePath, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelFilePath)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCancelActionPerformed
    {//GEN-HEADEREND:event_jButtonCancelActionPerformed
        fKeepDownloadThreadRunning = false;
        fWaitForDownloadToFinish.WaitOne();
        this.dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowOpened
    {//GEN-HEADEREND:event_formWindowOpened
        Runnable downloadRunnable = () ->
        {
            try
            {
                URL url = new URI(fURLOfFileToDownload).toURL();

                HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
                long completeFileSize = httpConnection.getContentLength();
                java.io.BufferedInputStream in = new java.io.BufferedInputStream(httpConnection.getInputStream());
                java.io.FileOutputStream fos = new java.io.FileOutputStream(fPathToSaveFile);
                java.io.BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
                byte[] data = new byte[1024];
                long downloadedFileSize = 0;

                int x1 = 0;
                while (fKeepDownloadThreadRunning && (x1 = in.read(data, 0, 1024)) >= 0)
                {
                    downloadedFileSize += x1;

                    // Update Progress
                    final double currentProgress = (((double) downloadedFileSize * 100.00d) / (double) completeFileSize);

                    SwingUtilities.invokeLater(() ->
                    {
                        jProgressBar1.setValue((int) currentProgress);
                        jProgressBar1.setString(String.format("%.2f", currentProgress) + "%");
                    });
                    bout.write(data, 0, x1);
                }
                bout.close();
                in.close();
            }
            catch (IOException | URISyntaxException ex)
            {
                System.err.println(ex.getMessage());
            }

            fWaitForDownloadToFinish.Set();
            this.dispose();
        };

        fDownloadThread = new Thread(downloadRunnable);
        fWaitForDownloadToFinish = new ManualResetEvent(false);
        fKeepDownloadThreadRunning = true;
        fDownloadThread.start();
    }//GEN-LAST:event_formWindowOpened


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelFilePath;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
}
