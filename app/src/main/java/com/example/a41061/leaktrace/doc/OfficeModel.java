package com.example.a41061.leaktrace.doc;

import com.example.a41061.leaktrace.test.ChairFactory;
import com.example.a41061.leaktrace.test.DeskFactory;
import com.example.a41061.leaktrace.test.Product;
import com.example.a41061.leaktrace.test.ProductFactory;

import java.io.Serializable;

/**
 * 文档类型model
 *
 * @author FanHongyu.
 * @since 18/3/16 20:43.
 * email fanhongyu@hrsoft.net.
 */

public class OfficeModel implements Serializable{


    private String id;
    private String path;
    private String size;
    private String name;
    private String type;



    public OfficeModel(String id, String path, String size, String name, String type) {
        this.id = id;
        this.path = path;
        this.size = size;
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
