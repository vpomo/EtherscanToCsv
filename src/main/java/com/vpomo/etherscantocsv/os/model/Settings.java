package com.vpomo.etherscantocsv.os.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Zver on 11.10.2016.
 */
public class Settings {
    private static final Logger logger = LoggerFactory.getLogger(Settings.class);
    public static String PATH_TO_CONFIG = "./setupJAR/Init/config.txt";

    public static String TIME_FIRST_START = "2017/06/27--07:01:00";
    public static int NUMBER_DAYS_RUNNING = 365;

    public static String PATH_CHROME_DRIVER = "./setupJAR/chromedriver.exe";
    public static String PATH_FIREFOX_DRIVER = "./setupJAR/geckodriver.exe";
    public static String PATH_FIREFOX_BIN = "";

    public static String PATH_LIST_PROXY = "./setupJAR/Proxy/listProxy.txt";
    public static String PATH_LIST_DIRTY_PROXY = "./setupJAR/Proxy/listDirtyProxy.txt";
    public static String PATH_BAD_PROXY = "./setupJAR/Proxy/badProxy.txt";
    public static String PATH_LOGS = "./setupJAR/Logs/log.txt";
    public static String PATH_TRACK_VISIT = "./setupJAR/Init/track.txt";
    public static String DRIVER_BROWSER = "FIREFOX";
    public static String TESTED = "TESTED";

    public static String readConfig() {

        Properties prop = new Properties();
        InputStream propStream = null;
        String result = null;
        try {
            propStream = new FileInputStream("./setupJAR/Init/config.txt");
            if (propStream == null) {
                System.out.println("Not reading config");
            }
            logger.info("\n read properties:");
            if (propStream != null) {
                prop.load(propStream);
                // read configuration
                String numberDays = prop.getProperty("NUMBER_DAYS_RUNNING");
                PATH_CHROME_DRIVER = prop.getProperty("PATH_CHROME_DRIVER");
                PATH_FIREFOX_DRIVER = prop.getProperty("PATH_FIREFOX_DRIVER");
                PATH_FIREFOX_BIN = prop.getProperty("PATH_FIREFOX_BIN");

                DRIVER_BROWSER = prop.getProperty("DRIVER_BROWSER");
                PATH_LIST_PROXY = prop.getProperty("PATH_LIST_PROXY");
                PATH_LIST_DIRTY_PROXY = prop.getProperty("PATH_LIST_DIRTY_PROXY");
                PATH_LOGS = prop.getProperty("PATH_LOGS");
                PATH_TRACK_VISIT = prop.getProperty("PATH_TRACK_VISIT");
                TESTED = prop.getProperty("TESTED");

                String timeFirstStart = prop.getProperty("TIME_FIRST_START");
                if (timeFirstStart != null) {
                    TIME_FIRST_START = timeFirstStart;
                } else { result = "Не указана дата и время запуска. Пример: 2016/11/18--07:01:00";}
                System.out.println("timeFirstStart = " + TIME_FIRST_START);
            }
        } catch (Exception ex) {
            logger.info("Exception: \n" + ex.toString());
            ex.printStackTrace();

            System.out.println("Couldn't read config.properties");
        } finally {
            if (propStream != null) {
                try {
                    propStream.close();
                } catch (IOException e) {
                    System.out.println("Couldn't close input stream: " + e.getMessage());
                }
            }
        }
        return result;
    }



}
