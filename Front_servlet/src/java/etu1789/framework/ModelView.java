/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etu1789.framework;

import java.util.HashMap;

/**
 *
 * @author Hasinjo
 */
public class ModelView {
    private String page;
    private HashMap<String, Object> data;
    
    
    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    
    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
    
}
