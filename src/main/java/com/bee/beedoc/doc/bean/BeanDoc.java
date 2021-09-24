package com.bee.beedoc.doc.bean;

import com.bee.beedoc.doc.gs.NameGS;

import java.io.Serializable;
import java.util.List;

/**
 * @author weixin
 */
public class BeanDoc implements NameGS, Serializable {
    private static final long serialVersionUID = -1175796499407161234L;
    private String name;
    private List<FieldDoc> fieldDocs;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public List<FieldDoc> getFieldDocs() {
        return fieldDocs;
    }

    public void setFieldDocs(List<FieldDoc> fieldDocs) {
        this.fieldDocs = fieldDocs;
    }
}
