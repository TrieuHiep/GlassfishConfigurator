/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task;

import constant.ProductConstant;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tatsuya
 */
public class DomainGetter extends AbstractTask{

    public DomainGetter(String glassfishHome,
            ProductConstant productConstant) {
        super(productConstant, glassfishHome);
    }

    public Process getDomainStatus() throws IOException {
        super.baseCommand += super.folderScript + "GetDomain" + super.fileExtension + " " + super.glassfishHome;
        System.out.println("base command = " + baseCommand);
        Process proc = super.runtime.exec(baseCommand);
        return proc;
    }

//    @Override
//    public void run() {
//        try {
//            String dir = "\"" + glassfishHome + "\"";
//            String cmd = "cmd /C GetDomain.bat \"C:\\Program Files\\glassfish-4.1.1\"";
//
//            this.baseCommand += super.folderScript + "GetDomain" + super.fileExtension + " " + dir;
//            System.out.println("base command = " + baseCommand);
//            Process proc = super.runtime.exec(baseCommand);
//            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
//            System.out.println("Here is the standard output of the command:\n");
//            String s = null;
//            while ((s = stdInput.readLine()) != null) {
//                System.out.println(s);
//                if (s.contains("running")) {
//                    view.addDomain(DomainParser.parseDomain(s));
//                }
//                view.appendRuntimeLog(s);
//            }
//            // read any errors from the attempted command
//            System.out.println("Here is the standard error of the command (if any):\n");
//            while ((s = stdError.readLine()) != null) {
//                System.out.println(s);
//                view.appendRuntimeLog(s);
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(DomainGetter.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }


}
