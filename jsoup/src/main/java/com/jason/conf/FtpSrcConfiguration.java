package com.jason.conf;

import com.jason.databus.JinShanTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import sun.rmi.runtime.Log;

import javax.xml.soap.Text;
import java.time.Duration;
import java.util.HashSet;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Set;


public class FtpSrcConfiguration implements DatabusConfiguration {
    private final static String ADDR = "文件存放地址";
    private final static String LINK_NUM = "ftp最大连接数";
    private final static String SCHEMA = "自定义列元数据";
    private final static String FILE_TYPE = "ftp存放文件类型";
    private final static String SEP = "ftp文件列分割符号";
    private String ftpAddrIndex;
    private String ip;
    private String userName;
    private String path;
    private String linkNum;
    private String schema;
    private String sep;
    private String signalFileCheck;
    private String signalFileName;
    private Set<String> set;


    public FtpSrcConfiguration() {
        set = new HashSet<>(10);
        set.add(ADDR);
        set.add(LINK_NUM);
        set.add(SCHEMA);
        set.add(FILE_TYPE);
        set.add(SEP);
    }

    public String getFtpAddrIndex() {
        return ftpAddrIndex;
    }

    public void setFtpAddrIndex(String ftpAddrIndex) {
        this.ftpAddrIndex = ftpAddrIndex;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getSep() {
        return sep;
    }

    public void setSep(String sep) {
        this.sep = sep;
    }

    public String getSignalFileCheck() {
        return signalFileCheck;
    }

    public void setSignalFileCheck(String signalFileCheck) {
        this.signalFileCheck = signalFileCheck;
    }

    public String getSignalFileName() {
        return signalFileName;
    }

    public void setSignalFileName(String signalFileName) {
        this.signalFileName = signalFileName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLinkNum() {
        return linkNum;
    }

    public void setLinkNum(String linkNum) {
        this.linkNum = linkNum;
    }

    private void fillSep(WebElement tr, WebDriver driver) {
        tr.findElement(By.cssSelector("input")).sendKeys(getSep());
    }

    private void fillFileType(WebElement tr, WebDriver driver) {

    }

    private void fillSchema(WebElement tr, WebDriver driver) {
        JinShanTest.waitx();
        tr.findElement(By.cssSelector("td")).click();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("b[title='增加列']")));
        JinShanTest.waitx();

        String[] arr = getSchema().split(",", -1);
        System.out.println(arr.length);
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].trim();
            System.out.println(arr[i]);
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("b[title='增加列']")));
        WebElement jiahao = driver.findElements(By.cssSelector("b[title='增加列']")).get(0);
        Actions actionProvider = new Actions(driver);
        for (int i = 0; i < arr.length - 1; i++) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("点击增加列加号");
            }
            actionProvider.moveToElement(jiahao).perform();
            actionProvider.click(jiahao).perform();
            //actionProvider.clickAndHold(jiahao);
        }
        // todo 填写schema
        WebElement div = driver.findElement(By.cssSelector("div#selfDefineDiv"));
        List<WebElement> schematrs = div.findElements(By.tagName("tr"));
        for (int i = 0; i < arr.length; i++) {
            String[] schemas = arr[i].split("\\s+", -1);
            if (schemas.length != 2) {
                throw new IllegalArgumentException("schema 不合法：" + getSchema());
            }
            String name = schemas[0];
            String type = schemas[1];
            WebElement schematr = schematrs.get(i + 1);
            List<WebElement> schemaTds = schematr.findElements(By.tagName("td"));
            schemaTds.get(0).findElement(By.tagName("input")).sendKeys(name);
            Select select = new Select(schemaTds.get(2).findElement(By.tagName("select")));
            select.selectByValue(type.toLowerCase());
        }

        div.findElement(By.cssSelector("i#closeButton")).click();

    }

    private void fillLinkNum(WebElement tr, WebDriver driver) {
        tr.findElement(By.tagName("input")).sendKeys(getLinkNum());
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("添加连接数成功");
        }
    }

    private void fillAddr(WebElement tr, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20).getSeconds());
        tr.click();
        //modal-content
        JinShanTest.waitx(3000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input#ftpConnection")));
        WebElement div = driver.findElement(By.cssSelector("div.modal-content"));
        //点击选择连接
        div.findElement(By.cssSelector("input#ftpConnection")).click();
        JinShanTest.waitx(3000);
        //wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("table.datagrid-btable tr")));

        //wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("td[field='userName']")));
        //wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("div[type='iframe']")));
        driver.switchTo().frame(1);
        //List<WebElement> tbs = driver.findElements(By.cssSelector("table.datagrid-btable"));
        List<WebElement> tbs = driver.findElements(By.tagName("table"));
        //查找ftp连接并点击
        outter:
        for (WebElement tb : tbs) {
            List<WebElement> trs = tb.findElements(By.tagName("tr"));
            for (WebElement trx : trs) {
                String s = trx.getText();
                if (s.contains("使用该连接") && s.contains(getIp()) && s.contains(getUserName())) {
                    trx.findElement(By.cssSelector("a[title='使用该连接']")).click();
                    break outter;
                }
            }
        }
        JinShanTest.waitx();
        driver.switchTo().defaultContent();
        //填写路径
        driver.findElement(By.cssSelector("input#paramPath")).sendKeys(getPath());
        driver.findElement(By.cssSelector("button#submit")).click();
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("ftp 地址填写完毕");
        }
    }

    @Override
    public void config(WebElement element, WebDriver driver) {
        WebElement edit = element;
        List<WebElement> trs = edit.findElements(By.xpath("//form[@id='myForm']/div/table/tbody/tr"));
        // = tbf.findElements(By.cssSelector("tr"));
        String s = "colsSetting";
        String desc = null;
        for (WebElement tr : trs) {
            String cla = tr.getAttribute("class");
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("class = " + cla);
            }
            if (!cla.contains(s)) {
                continue;
            }
            String text = tr.getText();
            System.out.println(">>>>>" + text);
            if (set.contains(text)) {
                desc = text;
                continue;
            }
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("=========" + desc);
            }
            switch (desc) {
                case ADDR:
                    fillAddr(tr, driver);
                    break;
                case LINK_NUM:
                    fillLinkNum(tr, driver);
                    break;
                case SCHEMA:
                    fillSchema(tr, driver);
                    break;
                case FILE_TYPE:
                    fillFileType(tr, driver);
                    break;
                case SEP:
                    fillSep(tr, driver);
                    break;
                default:
                    System.out.println(desc);
                    break;
            }
        }
        List<WebElement> buttons = element.findElements(By.tagName("button"));
        JinShanTest.waitx(3000);
        //点击 保存
        for (WebElement button : buttons) {
            if(button.getText().contains("保存")){
                button.click();
            }
            break;
        }

    }


    public static final DatabusConfiguration getTestconfig() {
        FtpSrcConfiguration conf = new FtpSrcConfiguration();
        conf.setFtpAddrIndex("0");
        conf.setIp("10.4.66.206");
        conf.setUserName("u_lx_bus");
        conf.setLinkNum("10");
        conf.setPath("./ydj/test.txt");
        conf.setSchema("aa string,bb string");
        conf.setSep("__jdjdjd_");
        return conf;
    }
}