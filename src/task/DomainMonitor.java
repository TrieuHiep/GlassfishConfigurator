/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task;

import constant.ProductConstant;
import java.io.IOException;
import model.Domain;

/**
 *
 * @author Tatsuya
 */
public class DomainMonitor extends AbstractTask{

    public DomainMonitor(ProductConstant productConstant, String glassfishHome) {
        super(productConstant, glassfishHome);
    }

    public Process startDomain(Domain domain) throws IOException {
        super.baseCommand += super.folderScript + "StartDomain" + super.fileExtension + " " + super.glassfishHome + " " + domain.getDomainName();
        System.out.println("base command = " + baseCommand);
        Process proc = super.runtime.exec(baseCommand);
        return proc;
    }
        public Process stopDomain(Domain domain) throws IOException {
        super.baseCommand += super.folderScript + "StopDomain" + super.fileExtension + " " + super.glassfishHome + " " + domain.getDomainName();
        System.out.println("base command = " + baseCommand);
        Process proc = super.runtime.exec(baseCommand);
        return proc;
    }
}
