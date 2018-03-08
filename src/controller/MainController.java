/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import constant.ConsoleAreaConstant;
import constant.ProductConstant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Domain;
import task.ConnCreator;
import task.DomainGetter;
import task.JNDINameCreator;
import task.subtask.FileCopier;
import utils.DomainParser;
import view.GlassfishConfigurator;

/**
 * @author Tatsuya
 */
public class MainController {

    private GlassfishConfigurator view;
    private String glassfishHomeDir;
    private ProductConstant productConstant;

    private Map<String, String> mapLogs;

    public MainController(GlassfishConfigurator view, ProductConstant productConstant) {
        this.view = view;
        this.productConstant = productConstant;
        this.mapLogs = new HashMap<>();
        initListener();
        initGlassfishHome();
        this.view.showMessage(System.getProperty("os.name") + " OS detected!");
    }

    private void initGlassfishHome() {
        Properties configReader = new Properties();
        try {
            configReader.load(new FileInputStream("logs/log.txt"));
        } catch (IOException e) {
            System.out.println("creating logs file...");
            try {
                String path = "logs" + File.separator + "log.txt";
                File f = new File(path);
                f.getParentFile().mkdirs();
                f.createNewFile();
                System.out.println("done!");
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String t = configReader.getProperty("GlassfishHome");
        String mysql = configReader.getProperty("MySQLDriver");
        if (t != null) {
            this.mapLogs.put("GlassfishHome", t);
            glassfishHomeDir = t;
            this.view.setGlassfishHome(t);
        }
        if (mysql != null) {
            this.mapLogs.put("MySQLDriver", mysql);
            this.view.setMySQLLib(mysql);
        }
    }

    private void initListener() {
        this.view.setGlassfishHomeListener(new GlassfishLoader());
        this.view.setMySQLConnector(new MySQLPathGetter());
        this.view.setStartDomainListener(new DomainStarter());
        this.view.setStopDomainListener(new DomainStopper());
        this.view.setConnectionPoolListener(new ConnectionPoolCreator());
        this.view.setJNDINameListener(new JNDICreator());
        this.view.setGetDomainStatusListener(new DomainStatusGetter());
    }

    class DomainStatusGetter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            glassfishHomeDir = MainController.this.view.getGlassfishHome();
            if (glassfishHomeDir == null) {
                view.showMessage("you must choose the home folder of Glassfish Server!");
                return;
            }
            try {
                Process proc = new DomainGetter(glassfishHomeDir, productConstant).getDomainStatus();
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
                System.out.println("Here is the standard output of the command:\n");
                String s = null;
                List<Domain> domainList = new ArrayList<>();
                while ((s = stdInput.readLine()) != null) {
                    System.out.println(s);
                    view.appendRuntimeLog(s, ConsoleAreaConstant.DOMAIN_AREA);
                    if (s.contains("running")) {
                        domainList.add(DomainParser.parseDomain(s));
                    }
                }
                view.addDomains(domainList);
                System.out.println("Here is the standard error of the command (if any):\n");
                while ((s = stdError.readLine()) != null) {
                    System.out.println(s);
                    view.appendRuntimeLog(s, ConsoleAreaConstant.DOMAIN_AREA);
                }
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    class GlassfishLoader implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            glassfishHomeDir = MainController.this.view.getGlassfishDirectory();
            if (glassfishHomeDir == null) {
                view.showMessage("you must choose the home folder of Glassfish Server!");
                return;
            }
            MainController.this.mapLogs.put("GlassfishHome", glassfishHomeDir);
            MainController.this.saveToLogs();
            MainController.this.view.setGlassfishHome(glassfishHomeDir);
            System.out.println(glassfishHomeDir);

        }
    }

    class MySQLPathGetter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (glassfishHomeDir == null || view.getSelectedDomain() == null) {
                view.showMessage("you must choose glassfish home folder and domain first!");
                return;
            }
            File srcFile = view.getMySQLConnectorJarFile();
            if (srcFile == null) {
                view.showMessage("please choose mysql driver library to copy!");
                return;
            }
            view.showFilePath(srcFile.getAbsolutePath());
            String libName = srcFile.getName();

            String libNameAbs = srcFile.getAbsolutePath();
            MainController.this.mapLogs.put("MySQLDriver", libNameAbs);
            MainController.this.saveToLogs();
            String desFileURL = glassfishHomeDir + "/glassfish/domains/"
                    + view.getSelectedDomain().getDomainName() + "/lib/" + libName;
            File destFile = new File(desFileURL);
            new Thread(new FileCopier(srcFile, destFile)).start();
        }
    }

    class DomainStarter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Domain domain = view.getSelectedDomain();
            if (domain == null) {
                view.showMessage("you must choose a domain to start");
                return;
            }
            try {
                showResultToGUI(
                        new task.DomainMonitor(productConstant, glassfishHomeDir).startDomain(domain),
                        ConsoleAreaConstant.DOMAIN_AREA);
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    class DomainStopper implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Domain domain = view.getSelectedDomain();
            if (domain == null) {
                view.showMessage("you must choose a domain to start");
                return;
            }
            try {
                showResultToGUI(
                        new task.DomainMonitor(productConstant, glassfishHomeDir).stopDomain(domain),
                        ConsoleAreaConstant.DOMAIN_AREA);
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    class ConnectionPoolCreator implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String poolName = view.getConnecionPoolName();
            if (poolName == null || glassfishHomeDir == null) {
                view.showMessage("you must choose glassfish home and enter pool name");
                return;
            }
            try {
                showResultToGUI(
                        new ConnCreator(productConstant, glassfishHomeDir, poolName)
                                .createConnectoionPool(), ConsoleAreaConstant.JDBC_CONNECTION_POOL_AREA
                );
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    class JNDICreator implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String jndiName = view.getJNDIName();
            if (glassfishHomeDir == null || view.getConnecionPoolName() == null || jndiName == null) {
                view.showMessage("you must enter all fields in previous tabs!");
                return;
            }
            try {
                showResultToGUI(
                        new JNDINameCreator(productConstant, glassfishHomeDir,
                                view.getConnecionPoolName(), jndiName)
                                .createJNDIName(), ConsoleAreaConstant.JNDI_AREA
                );
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void showResultToGUI(Process proc, ConsoleAreaConstant consoleAreaConstant) {
        try {
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

            System.out.println("Here is the standard output of the command:\n");
            String s = null;

            while ((s = stdInput.readLine()) != null) {
                System.out.print(s);
                view.appendRuntimeLog(s, consoleAreaConstant);
            }

            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.print(s);
                view.appendRuntimeLog(s, consoleAreaConstant);
            }
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveToLogs() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new File("logs/log.txt"));
            for (String key : this.mapLogs.keySet()) {
                String value = this.mapLogs.get(key);
                if (value.contains("\\")) {
                    value = value.replace('\\', '/');
                }
                writer.println(key + "=" + value);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
        }
    }
}
