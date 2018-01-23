/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task.subtask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tatsuya
 */
public class FileCopier implements Runnable {

    private File sourceFile;
    private File destFile;

    public FileCopier(File sourceFile, File destFile) {
        this.sourceFile = sourceFile;
        this.destFile = destFile;
    }

    @Override
    public void run() {
        try {
            FileInputStream fin = new FileInputStream(sourceFile);
            FileOutputStream fout = new FileOutputStream(destFile);

            byte[] b = new byte[1024];
            int noOfBytes = 0;
            while ((noOfBytes = fin.read(b)) != -1) {
                fout.write(b, 0, noOfBytes);
            }
            System.out.println("File copied!");
            fin.close();
            fout.close();

        } catch (FileNotFoundException fnf) {
            System.out.println("Specified file not found :" + fnf);
        } catch (IOException ex) {
            Logger.getLogger(FileCopier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

