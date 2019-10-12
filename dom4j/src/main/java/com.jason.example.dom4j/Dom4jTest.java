package com.jason.example.dom4j;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;

public class Dom4jTest {

    public void findLinks(Document document) throws DocumentException {
        List<Node> list = document.selectNodes("//property/@name");
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            Attribute attribute = (Attribute) iter.next();
            String url = attribute.getValue();
            System.out.println(url);
        }
    }

    public static Document read(String fileName) throws MalformedURLException, DocumentException {
        SAXReader reader = new SAXReader();
        return reader.read(new File(fileName));
    }

    //<property name="xx" value="xx"/> 获取value
    public static String findVal(Document document, String name) throws DocumentException {
        List<Node> list = document.selectNodes("//property[@name='" + name + "']");
        DefaultElement node = (DefaultElement) list.get(0);
        return node.attribute("value").getValue();
    }

    public void findNode(Document document) throws DocumentException {
        List<Node> list = document.selectNodes("//property[@name='tai.log.dir']");
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            DefaultElement node = (DefaultElement) iter.next();
            System.out.println(node);
            System.out.println(node.attribute("value").getValue());
        }
    }

    public static void main(String[] args) throws MalformedURLException, DocumentException {
        /*Dom4jTest dom = new Dom4jTest();
        dom.findNode(dom.read(""));
        dom.findLinks(dom.read(""));*/
        System.out.println("==");
        String s = findVal(read("logback.xml"),"tai.log.dir");
        System.out.println(s);
    }
}
