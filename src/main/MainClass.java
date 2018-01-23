/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import constant.ProductConstant;
import controller.MainController;
import view.GlassfishConfigurator;

/**
 *
 * @author Tatsuya
 */
public class MainClass {

    public static void main(String[] args) {
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            MainController controller = new MainController(
                    new GlassfishConfigurator(), ProductConstant.WINDOWS);
        } else {
            MainController controller = new MainController(
                    new GlassfishConfigurator(), ProductConstant.LINUX);
        }
        
    }
}
