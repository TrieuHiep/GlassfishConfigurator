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
public abstract class ProductAbstract {

    protected ProductConstant productConstant;

    public ProductAbstract(ProductConstant productConstant) {
        this.productConstant = productConstant;
    }

    public abstract void executeWindowsPlatform();

    public abstract void executeLinuxPlatform();

    public void execute() {
        switch (this.productConstant) {
            case WINDOWS: {
                executeWindowsPlatform();
                break;
            }
            case LINUX: {
                executeLinuxPlatform();
                break;
            }
        }
    }
}
