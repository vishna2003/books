package com.sismics.books.core.util;

import com.sismics.util.EnvironmentUtil;
import org.apache.commons.lang.StringUtils;

import java.io.File;

/**
 * Utilities to gain access to the storage directories used by the application.
 * 
 * @author jtremeaux
 */
public class DirectoryUtil {
    /**
     * Returns the base data directory.
     * 
     * @return Base data directory
     */
    public static File getBaseDataDirectory() {
        File baseDataDir = null;
        if (EnvironmentUtil.getWebappRoot() != null) {
            // We are in a webapp environment
            if (StringUtils.isNotBlank(EnvironmentUtil.getBooksHome())) {
                // If the books.home property is set then use it
                baseDataDir = new File(EnvironmentUtil.getBooksHome());
                if (!baseDataDir.isDirectory()) {
                    baseDataDir.mkdirs();
                }
            } else {
                // Use the base of the Webapp directory
                baseDataDir = new File(EnvironmentUtil.getWebappRoot() + File.separator + "sismicsbooks");
                if (!baseDataDir.isDirectory()) {
                    baseDataDir.mkdirs();
                }
            }
        }
        if (baseDataDir == null) {
            // Or else (for unit testing), use a temporary directory
            baseDataDir = new File(System.getProperty("java.io.tmpdir"));
        }
        
        return baseDataDir;
    }
    
    /**
     * Returns the database directory.
     * 
     * @return Database directory.
     */
    public static File getDbDirectory() {
        return getDataSubDirectory("db");
    }
    
    /**
     * Returns the book covers directory.
     * 
     * @return Book covers directory.
     */
    public static File getBookDirectory() {
        return getDataSubDirectory("book");
    }
    
    /**
     * Returns the log directory.
     * 
     * @return Log directory.
     */
    public static File getLogDirectory() {
        return getDataSubDirectory("log");
    }

    /**
     * Returns the themes directory.
     * 
     * @return Theme directory.
     */
    
     public static File getThemeDirectory() {
        String webappRoot = EnvironmentUtil.getWebappRoot();
        File themeDir;
        if (webappRoot != null) {
            themeDir = new File(webappRoot + File.separator + "style" + File.separator + "theme");
        } else {
            themeDir = new File(DirectoryUtil.class.getResource("/style/theme").getFile());
        }
        if (themeDir.isDirectory()) {
            return themeDir;
        }
        return null;
    }
    

        
    /**
     * Returns a subdirectory of the base data directory
     * 
     * @return Subdirectory
     */
    private static File getDataSubDirectory(String subdirectory) {
        File baseDataDir = getBaseDataDirectory();
        File faviconDirectory = new File(baseDataDir.getPath() + File.separator + subdirectory);
        if (!faviconDirectory.isDirectory()) {
            faviconDirectory.mkdirs();
        }
        return faviconDirectory;
    }
}
