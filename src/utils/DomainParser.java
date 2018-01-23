/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.StringTokenizer;
import model.Domain;

/**
 *
 * @author Tatsuya
 */
public class DomainParser {
    public static Domain parseDomain(String input) {
        StringTokenizer stk = new StringTokenizer(input, " ");
        Domain domain = new Domain();
        int count = stk.countTokens();
        domain.setDomainName(stk.nextToken());
        if (count == 2) {
            domain.setStatus("running");
        } else {
            domain.setStatus("not running");
        }
        return domain;
    }
}
