package com.bee.beedoc.doc.bean;

import com.bee.beedoc.doc.gs.CommentGS;
import com.bee.beedoc.doc.gs.NameGS;

import java.io.Serializable;
import java.util.List;

/**
 * @author weixin
 */
public class FieldDoc implements NameGS, CommentGS, Serializable {
    private static final long serialVersionUID = 211308351247816470L;
    private String name;
    private String comment;
    private List<String> seeRefers;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

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
        return seeRefers;
    }

    @Override
    public void setSeeRefers(List<String> seeRefers) {
        this.seeRefers = seeRefers;
    }
}
