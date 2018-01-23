/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task;

import constant.ProductConstant;

/**
 *
 * @author Tatsuya
 */
public abstract class AbstractTask extends ProductAbstract {

    protected Runtime runtime;
    protected String baseCommand;
    protected String folderScript;
    protected String fileExtension;
    protected String glassfishHome;

    public AbstractTask(ProductConstant productConstant, String glassfishHome) {
        super(productConstant);
        this.glassfishHome = "\"" + glassfishHome + "\"";
        super.execute();
        this.runtime = Runtime.getRuntime();
    }

    @Override
    public void executeWindowsPlatform() {
        this.baseCommand = "cmd /C ";
        this.folderScript = "windows\\";
        this.fileExtension = ".bat";
    }

    @Override
    public void executeLinuxPlatform() {
        this.baseCommand = "bash ";
        this.folderScript = "linux/";
        this.fileExtension = ".sh";
    }
}
