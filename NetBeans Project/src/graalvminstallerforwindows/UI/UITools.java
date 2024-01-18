package graalvminstallerforwindows.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

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
public class UITools
{

    static
    {

    }

    public static void ShowPleaseWaitDialog(String dialogTitle, String description, javax.swing.JFrame parentForm, Runnable workToDo)
    {
        final JDialog loading = new JDialog(parentForm);
        JPanel p1 = new JPanel(new GridLayout(1, 1));

        p1.add(new JLabel("<html><p style='padding:15px'>" + description + "</p></html>"), BorderLayout.CENTER);

        loading.setTitle(dialogTitle);
        loading.setUndecorated(false);
        loading.getContentPane().add(p1);
        loading.pack();
        loading.setLocationRelativeTo(parentForm);
        loading.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        loading.setModal(true);

        // Create a new Swing Worker in order to run
        // the "workToDo" runnable and then displose the "loading dialog"
        SwingWorker<String, Void> worker = new SwingWorker<String, Void>()
        {
            @Override
            protected String doInBackground() throws Exception
            {
                workToDo.run();
                return null;
            }

            @Override
            protected void done()
            {
                loading.dispose();
            }
        };
        worker.execute();

        // Show the loading dialog
        loading.setVisible(true);
        loading.requestFocus();

        loading.addWindowListener(new WindowListener()
        {
            @Override
            public void windowOpened(WindowEvent e)
            {
                try
                {
                    // Wait for worker to finish and then dispose the loading form
                    worker.get(); //here the parent thread waits for completion
                    loading.dispose();
                }
                catch (Exception ex)
                {

                }
            }

            @Override
            public void windowClosing(WindowEvent e)
            {

            }

            @Override
            public void windowClosed(WindowEvent e)
            {

            }

            @Override
            public void windowIconified(WindowEvent e)
            {

            }

            @Override
            public void windowDeiconified(WindowEvent e)
            {
            }

            @Override
            public void windowActivated(WindowEvent e)
            {

            }

            @Override
            public void windowDeactivated(WindowEvent e)
            {

            }
        });

    }

    public static Point getPositionForFormToOpenInMiddleOfScreen(int formWidth, int formHeight)
    {
        // Get the size of the screen
        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Determine the new location of the window
        final int w = formWidth;
        final int h = formHeight;
        final int x = (dim.width - w) / 2;
        final int y = (dim.height - h) / 2;

        // Move the window
        return new Point(x, y);
    }

    public static Point getPositionForDialogToOpenInMiddleOfParentForm(JFrame parent, JDialog form)
    {
        // Set form in middle of frmControl
        final int frmControlWidth = parent.getSize().width;
        final int frmControlHeight = parent.getSize().height;
        final int w = form.getSize().width;
        final int h = form.getSize().height;
        final int x = ((frmControlWidth - w) / 2) + parent.getX();
        final int y = (frmControlHeight - h) / 2 + parent.getY();

        return new Point(x, y);
    }

}
