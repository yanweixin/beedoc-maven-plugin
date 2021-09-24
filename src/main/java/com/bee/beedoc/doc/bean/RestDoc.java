package com.bee.beedoc.doc.bean;

import com.bee.beedoc.doc.gs.UrlGS;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

/**
 * @author weixin
 */
public class RestDoc implements UrlGS, Serializable {
    private static final long serialVersionUID = -134781505450683702L;
    private String url;
    private List<UrlDoc> urlDocs;
    private List<RestDoc> childDocs;
    @JsonIgnore
    private RestDoc parent;

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    public List<UrlDoc> getUrlDocs() {
        return urlDocs;
    }

    public void setUrlDocs(List<UrlDoc> urlDocs) {
        this.urlDocs = urlDocs;
    }

    public List<RestDoc> getChildDocs() {
        return childDocs;
    }

    public void setChildDocs(List<RestDoc> childDocs) {
        this.childDocs = childDocs;
    }

    public RestDoc getParent() {
        return parent;
    }

    public void setParent(RestDoc parent) {
        this.parent = parent;
    }
}
