package com.bee.beedoc.doc.bean;

import com.bee.beedoc.doc.gs.CommentGS;
import com.bee.beedoc.doc.gs.UrlGS;

import java.io.Serializable;
import java.util.List;

/**
 * @author weixin
 */
public class UrlDoc implements UrlGS, CommentGS, Serializable {
    private static final long serialVersionUID = 5865391329667973967L;
    /**
     * url function description
     */
    private String comment;

    private String url;

    private String postMethod;

    private String contentType;

    private List<ParamDoc> paramDocs;

    private ParamDoc returnDoc;

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public List<String> getSeeRefers() {
        return null;
    }

    @Override
    public void setSeeRefers(List<String> seeRefers) {

    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    public String getPostMethod() {
        return postMethod;
    }

    public void setPostMethod(String postMethod) {
        this.postMethod = postMethod;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public List<ParamDoc> getParamDocs() {
        return paramDocs;
    }

    public void setParamDocs(List<ParamDoc> paramDocs) {
        this.paramDocs = paramDocs;
    }

    public ParamDoc getReturnDoc() {
        return returnDoc;
    }

    public void setReturnDoc(ParamDoc returnDoc) {
        this.returnDoc = returnDoc;
    }
}
