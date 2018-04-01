package com.vpomo.etherscantocsv.os.makeJAR;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.dom.Node;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MainParser {
    private static final String BASE_URL = "https://etherscan.io/token/0xcdcfc0f66c522fd086a1b725ea3c0eeb9f9e8814#balances";
    private static final String PATH_FILE_CSV = "distibution.csv";
    private static int MAX_PAGES = 200;

    private static org.slf4j.Logger log = LoggerFactory.getLogger(MainParser.class);

    public static void main(String[] args) throws Exception {
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

        log.info("Parser starting ...");

        final WebClient webClient = new WebClient(BrowserVersion.CHROME);

        try {
            HtmlPage page = webClient.getPage(BASE_URL);
            log.info("Title: " + page.getTitleText());
            final List<FrameWindow> window = page.getFrames();
            HtmlPage pageTwo = (HtmlPage) window.get(1).getEnclosedPage();
            writeListAddress(pageTwo);
            int numberPage = 0;
            //int maxPages = 200;
            HtmlPage pagePrev = pageTwo;
            while(numberPage < MAX_PAGES - 1){
                Thread.sleep(10000);
                DomElement divButton = pagePrev.getElementById("PagingPanel2");
                HtmlAnchor anchor = pagePrev.getAnchorByText("Next");
                HtmlPage pageNext = anchor.click();
                writeListAddress(pageNext);
                pagePrev = pageNext;
                numberPage++;
            }
        } catch (Exception ex) {
            log.error("Exception: {}", ex.getMessage());
        }

        log.info("Parser stop");
    }

    private static void writeListAddress(HtmlPage page) {
        DomElement divTable = page.getElementById("maintable");
        DomNodeList<HtmlElement> table1 = divTable.getElementsByTagName("table");
        final HtmlTable table = (HtmlTable) table1.get(0);
        int numberCell = 0;
        for (final HtmlTableRow row : table.getRows()) {
            numberCell = 0;
            for (final HtmlTableCell cell : row.getCells()) {
                log.info("   Found cell: " + cell.asText());
                if(numberCell == 1 && !cell.asText().equalsIgnoreCase("Address")){
                    writeStringToFile(PATH_FILE_CSV,cell.asText() + ",2000");
                }
                numberCell++;
            }
        }
    }

    private static void writeStringToFile(String pathToFile, String writingString) {
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


}
