package com.vpomo.etherscantocsv.os.makeJAR;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.dom.Node;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MainParser {
    private static final String BASE_URL = "https://etherscan.io/token/0xcdcfc0f66c522fd086a1b725ea3c0eeb9f9e8814#balances";
    //private static final String BASE_URL = "http://ya.ru";
    private static org.slf4j.Logger log = LoggerFactory.getLogger(MainParser.class);

    public static void main(String[] args) throws Exception {
        //java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

        log.info("Parser starting ...");
/*
        HtmlUnitDriver driver = new HtmlUnitDriver();
        driver.setJavascriptEnabled(true);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
*/


        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //webClient.setS.setCssEnabled(true);
        //ProxyConfig proxyConfig = new ProxyConfig("127.0.0.1", 8888);
        //webClient.getOptions().setProxyConfig(proxyConfig);
        //webClient.setJavaScriptEnabled(false);
        try {
            HtmlPage page = webClient.getPage(BASE_URL);
            log.info("Title: " + page.getTitleText());
            final List<FrameWindow> window = page.getFrames();
            final HtmlPage pageTwo = (HtmlPage) window.get(1).getEnclosedPage();

            DomElement divTable = pageTwo.getElementById("maintable");
            DomNodeList<HtmlElement> table1 = divTable.getElementsByTagName("table");
            final HtmlTable table = (HtmlTable) table1.get(0);
            for (final HtmlTableRow row : table.getRows()) {
                log.info("Found row");
                for (final HtmlTableCell cell : row.getCells()) {
                    log.info("   Found cell: " + cell.asText());
                }
            }

            final List<DomElement> divs = page.getElementsByTagName("div");
            for (DomElement element : divs) {
                //log.info(element.toString());
            }
/*
            HtmlElement element = page.getHtmlElementById("maintable");
            log.info(element.toString());
*/
            //tab-content
            //HtmlSubmitInput submit = form.getInputByValue("Next");
            //final HtmlTable table = page.getHtmlElementById("maintable");
            printComments(page);
        } catch (Exception ex) {
            log.error("Exception: {}", ex.getMessage());
        }

        log.info("Parser stop");
    }

    public static void printComments(DomNode node) {
        if (node.hasChildNodes()) {
            for (DomNode child: node.getChildren()) {
                printComments(child);
            }
        } else {
            if (node.getNodeType() == Node.COMMENT_NODE) {
                log.info(node.getNodeValue());
            }
        }
    }
}
