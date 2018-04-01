package com.vpomo.etherscantocsv.os.service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.vpomo.etherscantocsv.os.model.Settings.PATH_LOGS;

public class ListFromFile {

    public void writeLog(int result, String proxy, String url) {
        String writedString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z");

        switch (result) {
            case 1:
                writedString = dateFormat.format(new Date()) + " - " + proxy + " - " + url + " - " + "OK!";
                break;
            case 2:
                writedString = dateFormat.format(new Date()) + " - " + proxy + " - " + url + " - " + "Error" + "(" + result + ") - The page wasn't loaded";
                break;
            case 3:
                writedString = dateFormat.format(new Date()) + " - " + proxy + " - " + url + " - " + "Error" + "(" + result + ") - Base Page Exception";
                break;
            case 40:
                writedString = dateFormat.format(new Date()) + " - " + proxy + " - " + url + " - " + "Error" + "(" + result + ") - Page 1 (No such element Exception)";
                break;
            case 41:
                writedString = dateFormat.format(new Date()) + " - " + proxy + " - " + url + " - " + "Error" + "(" + result + ") - Page 1 (WebDriver Exception)";
                break;
            case 50:
                writedString = dateFormat.format(new Date()) + " - " + proxy + " - " + url + " - " + "Error" + "(" + result + ") - Page 2 (No such element Exception)";
                break;
            case 51:
                writedString = dateFormat.format(new Date()) + " - " + proxy + " - " + url + " - " + "Error" + "(" + result + ") - Page 2 (WebDriver Exception)";
                break;
            case 60:
                writedString = dateFormat.format(new Date()) + " - " + proxy + " - " + url + " - " + "Error" + "(" + result + ") - Page 3 (No such element Exception)";
                break;
            case 61:
                writedString = dateFormat.format(new Date()) + " - " + proxy + " - " + url + " - " + "Error" + "(" + result + ") - Page 3 (WebDriver Exception)";
                break;
            case 70:
                writedString = dateFormat.format(new Date()) + " - " + proxy + " - " + url + " - " + "Error" + "(" + result + ") - Page 4 (No such element Exception)";
                break;
            case 71:
                writedString = dateFormat.format(new Date()) + " - " + proxy + " - " + url + " - " + "Error" + "(" + result + ") - Page 4 (WebDriver Exception)";
                break;
            default:
                break;
        }
        writeStringToFile(PATH_LOGS, writedString);
    }

    public void writeLog(int result, String message) {
        String writedString = "";
        switch (result) {
            case 100:
                writedString = message;
                break;
            default:
                break;
        }
        writeStringToFile(PATH_LOGS, writedString);
    }

    public void writeStringToFile(String pathToFile, String writingString) {
        File file = new File(pathToFile);
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        String dataWithNewLine = writingString + System.getProperty("line.separator");
        try {
            fileWriter = new FileWriter(file, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(dataWithNewLine);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int deleteFile(String pathToFile) {
        File file = new File(pathToFile);
        if (file.exists()) {
            file.delete();
            return 1;
        }
        return 0;
    }

}
