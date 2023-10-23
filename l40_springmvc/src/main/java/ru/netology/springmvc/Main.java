package ru.netology.springmvc;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {

    public static final int DEFAULT_PORT = 9999;

    public static void main(String[] args) throws IOException, LifecycleException {
        File baseDir = Files.createTempDirectory("tc-40-webmvc").toFile();
        baseDir.deleteOnExit();

        Connector connector = new Connector();
        connector.setPort(DEFAULT_PORT);

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(baseDir.getAbsolutePath());
        tomcat.setConnector(connector);
        tomcat.getHost().setAppBase(".");
        tomcat.addWebapp("", ".");

        tomcat.getServer().start();
        tomcat.getServer().await();
    }
}
