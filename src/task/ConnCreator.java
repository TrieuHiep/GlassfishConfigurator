/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task;

import constant.ProductConstant;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Tatsuya
 */
public class ConnCreator extends AbstractTask {

    protected String connectionPoolName;

    public ConnCreator(ProductConstant productConstant, String glassfishHome, String connectionPoolName) {
        super(productConstant, glassfishHome);
        this.connectionPoolName = connectionPoolName;
    }

    public Process createConnectoionPool() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File("dbinfo.prop")));
        String serverName = properties.getProperty("serverName") + " ";
        String portNumber = properties.getProperty("portNumber") + " ";
        String databaseName = properties.getProperty("databaseName") + " ";
        String user = properties.getProperty("user") + " ";
        String password = properties.getProperty("password") + " ";

        super.baseCommand += super.folderScript + "CreatePoolConnection" + super.fileExtension + " ";
        StringBuilder originalCommand = new StringBuilder(super.baseCommand);
        originalCommand.append(connectionPoolName + " ");
        originalCommand.append(serverName);
        originalCommand.append(portNumber);
        originalCommand.append(databaseName);
        originalCommand.append(user);
        originalCommand.append(password);
        originalCommand.append(super.glassfishHome);
        System.out.println("base command = " + originalCommand.toString());
        Process proc = super.runtime.exec(originalCommand.toString());
        return proc;
    }

}
