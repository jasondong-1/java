package com.jason.databus;

import com.google.common.collect.Lists;
import com.jason.conf.DatabusConfiguration;
import com.jason.conf.FtpDestConfiguration;
import com.jason.conf.FtpSrcConfiguration;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class JinShanTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebElement canva;
    private Rectangle canvaRect;
    private int index = 0;
    private Map<String, WebElement> nodeNameElement = new HashMap<>(10);

    public JinShanTest() {
        this.driver = getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(120).getSeconds());
    }

    public WebDriver getDriver() {
        //System.setProperty("webdriver.chrome.driver", "/usr/local/webdriver/chrome/chromedriver");
        System.setProperty("webdriver.chrome.driver", "C:\\notos\\software\\webdriver\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        //WebDriver driver = new ChromeDriver(chromeOptions);
        WebDriver driver = new ChromeDriver();
        //driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return driver;
    }


    public static void waitx() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitx(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void login() throws InterruptedException {
        if (logger.isInfoEnabled()) {
            logger.info("开始登录数据总线系统 !");
        }
        driver.manage().window().maximize();
        driver.get("https://www.sh.ctc.com/BDOP/bddt/admin/portals/index#");
        //找到动态吗认证并切换
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input#sendsms")));
        waitx(5000);
        try {
            driver.findElement(By.cssSelector("input#tipsButton")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<WebElement> lis = driver.findElements(By.cssSelector("ul#tab_ul li"));
        for (WebElement li : lis) {
            if (li.getText().contains("动态码认证")) {
                li.findElement(By.tagName("span")).click();
                break;
            }
        }
        //输入用户名
        driver.findElement(By.cssSelector("input#m_userid")).sendKeys("w_ws_dhf");
        //输入密码
        driver.findElement(By.cssSelector("input#m_password")).sendKeys("veuimima1#");

        //点击获取验证码
        driver.findElement(By.cssSelector("input#sendsms")).click();
        //从控制台读取验证码
        Scanner scan = null;
        try {
            scan = new Scanner(System.in);
            System.out.println("请输入验证码：\n");
            String mobilesec = scan.nextLine();
            //输入验证码
            driver.findElement(By.cssSelector("input#mobilesec")).sendKeys(mobilesec);
        } finally {
            if (scan != null) {
                scan.close();
            }
        }

        //点击登录
        driver.findElement(By.cssSelector("input[value='登录']")).click();
        wait.until(presenceOfElementLocated(By.cssSelector("li#group-select")));

    }

    public void selectGroup() {
        driver.findElement(By.cssSelector("li#group-select")).click();
        waitx();
        driver.findElement(By.cssSelector("li[data-name='(理想)工作台测试组']")).click();
        waitx();
    }


    public void openDatabus() throws Exception {
        if (logger.isInfoEnabled()) {
            logger.info("登录完成，即将进入数据总线页面！");
        }
        //等待直到导航栏的出现
        wait.until(presenceOfElementLocated(By.cssSelector("ul.sidebar-menu.font-ulz.tree")));
        List<WebElement> elements = driver.findElements(By.cssSelector("ul.sidebar-menu.font-ulz.tree li"));
        //找到数据通道与加工的导航栏进行点击，如果不点击页面不会加载数据总线连接
        for (WebElement e : elements) {
            if (e.getText().contains("平台通道")) {
                e.click();
            }
        }
        waitx();
        //wait.until(presenceOfElementLocated(By.cssSelector("a[menuname='数据总线']")));
        // 等待出现数据总线链接
        //wait.until(dr -> dr.findElement(By.cssSelector("ul.nav-content")).getText().contains("数据总线"));
        String originalWindow = driver.getWindowHandle();
        List<WebElement> lis = driver.findElements(By.cssSelector("ul.treeview-menu li"));
        //找到数据总线链接并点击链接，此时 数据总线 页面会以新的标签页展示
        for (WebElement li : lis) {
            if (li.getText().contains("数据总线")) {
                li.click();
                break;
            }
        }

        Set<String> handles = driver.getWindowHandles();
        if (logger.isInfoEnabled()) {
            logger.info("当前打开的窗口数目是： " + handles.size());
        }
        //wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        if (handles.size() != 2) {
            throw new Exception("打开的窗口数不是两个");
        }

        for (String s : handles) {
            if (!s.equals(originalWindow)) {
                if (logger.isInfoEnabled()) {
                    logger.info("打开数据总线  标签页 成功，driver即将切换到数据总线页面 ");
                }
                driver.switchTo().window(s);
                break;
            }
        }

        if (logger.isInfoEnabled()) {
            logger.info("当前页面的title： " + driver.getTitle());
        }
    }

    public List<WebElement> getAllcpts() {
        return driver.findElement(By.cssSelector("div#workflow svg")).findElements(By.tagName("svg"));
    }

    public String getNodeName(WebElement svg) {
        WebElement element = svg.findElement(By.cssSelector("text"));
        String html = svg.getAttribute("innerHTML");
        Document doc = Jsoup.parse(html);
        Element text = doc.getElementsByTag("text").get(0);
        String nodename = text.attr("nodeName");
        return nodename;
    }

    public void collectComponent() {
        List<WebElement> svgs = getAllcpts();
        for (WebElement svg : svgs) {
            String nodename = getNodeName(svg);
            if (logger.isInfoEnabled()) {
                logger.info("当前组件的nodename是：" + nodename);
            }
            if (!nodeNameElement.containsKey(nodename)) {
                nodeNameElement.put(nodename, svg);
            }
        }
    }

    public WebElement addCpt2map() {
        WebElement elem = null;
        List<WebElement> svgs = getAllcpts();
        for (WebElement svg : svgs) {
            elem = svg;
            String nodename = getNodeName(svg);
            if (logger.isInfoEnabled()) {
                logger.info("当前组件的nodename是：" + nodename);
            }
            if (!nodeNameElement.containsKey(nodename)) {
                nodeNameElement.put(nodename, svg);
                break;
            }
        }
        return elem;
    }

    public void openCanvas() {
        wait.until(presenceOfElementLocated(By.id("exerciseEditBtn")));
        //点击小瓶子
        driver.findElement(By.cssSelector("div#exerciseEditBtn")).click();

        wait.until(presenceOfElementLocated(By.cssSelector("a#exerciseZtree_2_a")));
        //点击加号展开列表
        driver.findElement(By.cssSelector("span#exerciseZtree_2_switch")).click();

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("ul#exerciseZtree_2_ul")));

        //点击对应实验
        List<WebElement> elements = driver.findElements(By.cssSelector("ul#exerciseZtree_2_ul li"));
        for (WebElement element : elements) {
            if (element.getText().contains("ftptest22")) {
                element.findElement(By.cssSelector("span[title='切换作业']")).click();
                break;
            }
        }

        wait.until(presenceOfElementLocated(By.cssSelector("ul#plugin_ztree")));
        //初始化 canva，canvaRect
        getCanva();
        getCanvaRect();
        //将画布内的组件添加到map中
        waitx();
        collectComponent();
    }

    public WebElement getCanva() {
        if (canva == null) {
            canva = driver.findElement(By.cssSelector("div#canvasDiv"));
        }
        return canva;
    }

    public Rectangle getCanvaRect() {
        if (canvaRect == null) {
            canvaRect = getCanva().getRect();
        }
        return canvaRect;
    }

    /**
     * 拖拽组件
     *
     * @param cptName 组件名称
     * @param x 在画布中的位置 x 坐标
     * @param y 在画布中的位置 y 坐标
     * @return 成功拖拽出的组件
     * @throws Exception
     */

    /**
     * 拖拽组件
     *
     * @param cptName
     * @param x
     * @param y
     * @return
     * @throws Exception
     */
    public WebElement dragComponent(String grpName, String cptName, int x, int y) throws Exception {
        //所有的组件
        List<WebElement> allCpt = driver.findElements(By.cssSelector("ul#plugin_ztree>li"));
        Rectangle rect = getCanvaRect();
        WebElement src = null;
        outter:
        for (WebElement e : allCpt) {
            String title = e.findElement(By.tagName("a")).getAttribute("title");
            if (title.equals(grpName)) {
                List<WebElement> cpts = e.findElements(By.tagName("li"));
                for (WebElement e2 : cpts) {
                    String s = e2.getText();
                    if (logger.isInfoEnabled()) {
                        logger.info(s);
                    }

                    if (s.contains(cptName)) {
                        src = e2.findElement(By.tagName("a"));
                        break outter;
                    }
                }
            }
        }

        if (src == null) {
            throw new Exception("未找到组件" + grpName + cptName);
        }

        Actions actionProvider = new Actions(driver);
        actionProvider.dragAndDropBy(src, rect.getX() + x, rect.getY() + y).build().perform();
        return addCpt2map();
    }

    public void editCpt(WebElement element, DatabusConfiguration conf) {
        //双击组件
        Actions actionProvider = new Actions(driver);
        // Perform double-click action on the element
        actionProvider.doubleClick(element).build().perform();
        String css = "div#" + getNodeName(element);
        wait.until(presenceOfElementLocated(By.cssSelector(css)));
        waitx();
        WebElement edit = driver.findElement(By.cssSelector(css));
        conf.config(edit, driver);
        //等待组件变成绿色
        wait.until(ExpectedConditions.attributeToBe(element.findElement(By.tagName("rect")), "fill", "#d6f4c5"));
    }

    private WebElement findElementByAttr(WebElement element, String tagName, String attr, String value) {
        Document doc = Jsoup.parse(element.getAttribute("innerHTML"));

        Elements elements = doc.getElementsByTag(tagName);
        String id = null;
        for (Element e : elements) {
            String s = e.attr(attr);
            if (s.contains(value)) {
                id = e.attr("id");
                System.out.println(id);
                break;
            }
        }
        if (id != null) {
            return element.findElement(By.cssSelector(tagName + "[id='" + id + "']"));
        } else {
            return null;
        }
    }

    //todo 连线
    public void link(WebElement src, String out, WebElement dest, String in) throws Exception {
        //ellipse

        List<WebElement> srcElements = src.findElements(By.tagName("ellipse"));

        WebElement srcElement = findElementByAttr(src, "ellipse", "channelName", out);
        wait.until(ExpectedConditions.elementToBeClickable(srcElement));
        WebElement desElement = findElementByAttr(dest, "ellipse", "channelName", in);
        if (srcElement == null || desElement == null) {
            throw new Exception("未找到连线小圆圈");
        }

        Actions actions = new Actions(driver);
        //actions.click(src).build().perform();
        actions.moveToElement(srcElement);
        actions.clickAndHold(srcElement).perform();
        actions.dragAndDrop(srcElement, desElement).perform();
        waitx(3000);
        actions.release().perform();
        if (logger.isInfoEnabled()) {
            String srcS = src.getText();
            String destS = dest.getText();
            logger.info("成功建立连线，from " + srcS + ",to " + destS);
        }
    }

    public void test() {
        try {
            login();
            selectGroup();
            openDatabus();
            /*openCanvas();
            WebElement ftp0 = dragComponent("数据源组件", "ftp", 0, -200);
            editCpt(ftp0, FtpSrcConfiguration.getTestconfig());
            WebElement ftp1 = dragComponent("目标组件", "ftp", 0, -100);
            link(ftp0, "out_1", ftp1, "in_1");
            editCpt(ftp1, FtpDestConfiguration.getTestconfig());*/
            Thread.sleep(30000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //driver.quit();
        }
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        JinShanTest test = new JinShanTest();
        String s = "中国";
        String s2 = new String();
        test.test();
    }
}
