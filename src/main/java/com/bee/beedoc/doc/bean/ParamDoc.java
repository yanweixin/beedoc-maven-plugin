package com.bee.beedoc.doc.bean;

import com.bee.beedoc.doc.gs.NameGS;

import java.io.Serializable;
import java.util.List;

/**
 * @author weixin
 */
public class ParamDoc implements NameGS, Serializable {
    private static final long serialVersionUID = -6457867410902113099L;

    private String name;

    private String paramType;

    private List<FieldDoc> fieldDocs;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public List<FieldDoc> getFieldDocs() {
        return fieldDocs;
    }

    public void setFieldDocs(List<FieldDoc> fieldDocs) {
        this.fieldDocs = fieldDocs;
    }
}
