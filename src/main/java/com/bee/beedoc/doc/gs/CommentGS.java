package com.bee.beedoc.doc.gs;

import java.util.List;

public interface CommentGS {
    String getComment();

    void setComment(String comment);

    List<String> getSeeRefers();

    void setSeeRefers(List<String> seeRefers);
}
