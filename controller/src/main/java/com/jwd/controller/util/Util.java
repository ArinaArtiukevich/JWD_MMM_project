package com.jwd.controller.util;

import com.jwd.controller.resources.ConfigurationBundle;

public class Util {
    public static String pathToJspCheckIsIndexPage(final String page_path) {
        return ConfigurationBundle.getProperty("path.page.index").equals(page_path) ?
                pathToJspIndexPage(page_path) :
                pathToJsp(page_path);
    }

    public static String pathToJsp(final String page_path) {
        return ConfigurationBundle.getProperty("path.folder.web.inf") + ConfigurationBundle.getProperty("path.folder.jsp") + page_path + ConfigurationBundle.getProperty("path.jsp");
    }

    public static String pathToJspIndexPage(final String page_path) {
        return ConfigurationBundle.getProperty("path.folder.empty") + page_path + ConfigurationBundle.getProperty("path.jsp");
    }
}
