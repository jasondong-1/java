package com.jason.conf;

import com.jason.databus.JinShanTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FtpDestConfiguration implements DatabusConfiguration {
    private final static String ADDR = "文件存放地址";
    private final static String LINK_NUM = "ftp最大连接数";
    private final static String FILE_TYPE = "ftp存放文件类型";
    private final static String SEP = "ftp文件列分割符号";
    private final static String COMPRESS = "输出文件设置 是否压缩";
    private final static String SAVE_MODE = "写入ftp时的保存方式";
    private String ftpAddrIndex;
    private String ip;
    private String userName;
    private String path;
    private String linkNum;
    private String sep;
    private String compress;
    private String saveMode;
    private Set<String> set;

    public FtpDestConfiguration() {
        set = new HashSet<>(10);
        set.add(ADDR);
        set.add(LINK_NUM);
        set.add(FILE_TYPE);
        set.add(SEP);
        set.add(COMPRESS);
        set.add(SAVE_MODE);
    }

    public String getFtpAddrIndex() {
        return ftpAddrIndex;
    }

    public void setFtpAddrIndex(String ftpAddrIndex) {
        this.ftpAddrIndex = ftpAddrIndex;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLinkNum() {
        return linkNum;
    }

    public void setLinkNum(String linkNum) {
        this.linkNum = linkNum;
    }


    public String getSep() {
        return sep;
    }

    public void setSep(String sep) {
        this.sep = sep;
    }


    public String getCompress() {
        return compress;
    }

    public void setCompress(String compress) {
        this.compress = compress;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    private void fillSep(WebElement tr, WebDriver driver) {
        tr.findElement(By.cssSelector("input")).sendKeys(getSep());
    }

    private void fillFileType(WebElement tr, WebDriver driver) {

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
                case FILE_TYPE:
                    fillFileType(tr, driver);
                    break;
                case SEP:
                    fillSep(tr, driver);
                    break;
                case COMPRESS:
                    fillCompress(tr, driver);
                    break;
                case SAVE_MODE:
                    fillSaveMode(tr, driver);
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
            if (button.getText().contains("保存")) {
                button.click();
            }
            break;
        }
    }

    private void fillSaveMode(WebElement tr, WebDriver driver) {
        Select select = new Select(tr.findElement(By.tagName("select")));
        select.selectByValue(getSaveMode());
    }

    private void fillCompress(WebElement tr, WebDriver driver) {

    }

    public static final DatabusConfiguration getTestconfig() {
        FtpDestConfiguration conf = new FtpDestConfiguration();
        conf.setIp("10.4.66.206");
        conf.setUserName("u_lx_bus");
        conf.setLinkNum("10");
        conf.setPath("./ydj/testxx");
        conf.setCompress("false");
        conf.setSaveMode("Overwrite");
        conf.setSep("\\t");
        return conf;
    }
}
