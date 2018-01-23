/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Tatsuya
 */
public class Domain {

    private String domainName;
    private String status;

    public Domain() {
    }

    public Domain(String domainName, String status) {
        this.domainName = domainName;
        this.status = status;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return domainName + " - " + status;
    }
}
