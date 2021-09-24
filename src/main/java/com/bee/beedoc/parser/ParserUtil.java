package com.bee.beedoc.parser;

import com.bee.beedoc.doc.bean.ParamDoc;
import com.bee.beedoc.doc.bean.RestDoc;
import com.bee.beedoc.doc.bean.UrlDoc;
import com.bee.beedoc.doc.gs.NameGS;
import com.bee.beedoc.doc.gs.UrlGS;
import com.bee.beedoc.util.JacksonUtil;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author weixin
 */
public class ParserUtil {
    private final static Logger LOGGER = Logger.getLogger(ParserUtil.class.getName());
    private final ClassLoader classLoader;
    private final Map<String, Class<?>> classMap = new HashMap<>();

    public ParserUtil(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void walkNodes(CompilationUnit compilationUnit) {
        List<Node> childNodes = compilationUnit.getChildNodes();
        for (Node node : childNodes) {
            if (node instanceof ClassOrInterfaceDeclaration) {
                processClassOrInterface((ClassOrInterfaceDeclaration) node);
            }
        }
    }

    public void processClassOrInterface(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
        final String className = classOrInterfaceDeclaration.getNameAsString();
        LOGGER.info("process class or interface->" + className);
        NodeList<AnnotationExpr> annotations = classOrInterfaceDeclaration.getAnnotations();
        NodeList<ClassOrInterfaceType> implementedTypes = classOrInterfaceDeclaration.getImplementedTypes();
        if (className.endsWith("Controller") || annotations.stream().anyMatch(it -> {
            String identifier = it.getName().getIdentifier();
            return "RestController".equals(identifier) || "Controller".equals(identifier);
        })) {
            RestDoc restDoc = new RestDoc();
            processController(classOrInterfaceDeclaration, restDoc);
            LOGGER.info(JacksonUtil.serialize(restDoc));
        } else if (className.endsWith("Vo") || className.endsWith("Bean") || className.endsWith("Param")
                || implementedTypes.stream().anyMatch(it -> Serializable.class.getSimpleName().equals(it.getName().getIdentifier()))) {
            LOGGER.warning(String.format("java bean class type:{%s}", className));
        } else {
            try {
                String fullName = classOrInterfaceDeclaration.getFullyQualifiedName().orElse(null);
                if (fullName != null && !classMap.containsKey(fullName)) {
                    Class<?> clazz = classLoader.loadClass(fullName);
                    classMap.put(fullName, clazz);
                }
            } catch (ClassNotFoundException e) {
                LOGGER.warning(String.format("class type not found:[%s]", className));
            }
        }
    }

    private void processController(ClassOrInterfaceDeclaration classOrInterfaceDeclaration, RestDoc restDoc) {
        for (Node classComponentNode : classOrInterfaceDeclaration.getChildNodes()) {
            if (classComponentNode instanceof ClassOrInterfaceDeclaration) {
                RestDoc childRestDoc = new RestDoc();
                if (restDoc.getChildDocs() == null) {
                    restDoc.setChildDocs(new ArrayList<>());
                }
                childRestDoc.setParent(restDoc);
                restDoc.getChildDocs().add(childRestDoc);
                processController((ClassOrInterfaceDeclaration) classComponentNode, childRestDoc);
            } else if (classComponentNode instanceof AnnotationExpr) {
                processAnnotation(((AnnotationExpr) classComponentNode), restDoc, null);
            } else if (classComponentNode instanceof MethodDeclaration) {
                MethodDeclaration methodDeclaration = (MethodDeclaration) classComponentNode;
                // skip private method
                if (methodDeclaration.isPrivate()) {
                    continue;
                }
                UrlDoc urlDoc = new UrlDoc();
                if (restDoc.getUrlDocs() == null) {
                    restDoc.setUrlDocs(new ArrayList<>());
                }
                restDoc.getUrlDocs().add(urlDoc);
                processMethod(methodDeclaration, urlDoc);
            }
        }
    }

    private void processAnnotation(AnnotationExpr annotationExpr, UrlGS urlGS, NameGS nameGS) {
//        LOGGER.info("process annotation->" + annotationExpr.getNameAsString());
        String result = "";
        for (Node memberNode : annotationExpr.getChildNodes()) {
            if (memberNode instanceof MemberValuePair) {
                MemberValuePair memberValuePair = (MemberValuePair) memberNode;
                if ("name".equals(memberValuePair.getName().asString())) {
                    result = memberValuePair.getValue().asStringLiteralExpr().asString();
                    break;
                }
            } else if (memberNode instanceof StringLiteralExpr) {
                StringLiteralExpr stringLiteralExpr = (StringLiteralExpr) memberNode;
                result = stringLiteralExpr.asString();
                break;
            }
        }
        switch (annotationExpr.getNameAsString()) {
            case "RequestMapping":
                if (urlGS != null) {
                    urlGS.setUrl(result);
                }
                break;
            case "RequestParam":
                if (nameGS != null) {
                    nameGS.setName(result);
                }
                break;
            default:
                break;
        }
    }

    private void processMethod(MethodDeclaration methodDeclaration, UrlDoc urlDoc) {
//        LOGGER.info("process method->" + methodDeclaration.getNameAsString());
        urlDoc.setComment(methodDeclaration.getComment().toString());
        urlDoc.setPostMethod("POST");
        urlDoc.setContentType("form-data");
        for (Node node : methodDeclaration.getChildNodes()) {
            if (node instanceof AnnotationExpr) {
                processAnnotation((AnnotationExpr) node, urlDoc, null);
            } else if (node instanceof Parameter) {
                if (urlDoc.getParamDocs() == null) {
                    urlDoc.setParamDocs(new ArrayList<>());
                }
                processParameter((Parameter) node, urlDoc);
            } else if (node instanceof Type) {
                ParamDoc returnParam = new ParamDoc();
                processType((Type) node, returnParam);
            }
        }
    }

    private void processType(Type type, ParamDoc paramDoc) {
        paramDoc.setParamType(type.asString());
        paramDoc.setFieldDocs(new ArrayList<>());
    }

    private void processParameter(Parameter parameter, UrlDoc urlDoc) {
//        LOGGER.info("process parameter->" + parameter.getTypeAsString() + " " + parameter.getNameAsString());
        ParamDoc paramDoc = new ParamDoc();
        urlDoc.getParamDocs().add(paramDoc);
        for (Node node : parameter.getChildNodes()) {
            if (node instanceof AnnotationExpr) {
                processAnnotation((AnnotationExpr) node, null, paramDoc);
            } else if (node instanceof Type) {
                processType((Type) node, paramDoc);
            } else if (node instanceof SimpleName) {
                if (paramDoc.getName() == null) {
                    paramDoc.setName(((SimpleName) node).asString());
                }
            } else {
                LOGGER.warning(String.format("unknown parameter node:{%s}", node.getClass().getName()));
            }
        }
//        LOGGER.info("type of parameter:" + parameter.getType().getClass().getName());
    }
}
