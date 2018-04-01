package com.vpomo.etherscantocsv;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:V.Pomogalov@ftc.ru" Pomogalov Vladimir/>
 * @created on 30/03/18
 */

public class MainParserTest {
    @Test
    public void homePage() throws Exception {

        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
            Assert.assertEquals("HtmlUnit – Welcome to HtmlUnit", page.getTitleText());

            final String pageAsXml = page.asXml();
//            Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));

            final String pageAsText = page.asText();
            Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));
        }

    }
}
