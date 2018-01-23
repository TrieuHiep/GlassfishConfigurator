/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task;

import constant.ProductConstant;
import java.io.IOException;

/**
 *
 * @author Tatsuya
 */
public class JNDINameCreator extends ConnCreator {

    private String jndiName;

    public JNDINameCreator(ProductConstant productConstant, String glassfishHome, String connectionPoolName, String jndiName) {
        super(productConstant, glassfishHome, connectionPoolName);
        this.jndiName = jndiName;
    }

    public Process createJNDIName() throws IOException {
        super.baseCommand += super.folderScript + "CreateJNDI" + super.fileExtension + " ";
        StringBuilder originalCommand = new StringBuilder(baseCommand);
        originalCommand.append(jndiName + " ");
        originalCommand.append(super.connectionPoolName + " ");
        originalCommand.append(super.glassfishHome);
        System.out.println("base command = " + originalCommand.toString());
        Process proc = super.runtime.exec(originalCommand.toString());
        return proc;
    }

}
