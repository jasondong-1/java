package com.jason.databus;

import com.jason.conf.DatabusConfiguration;
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
import java.util.regex.Pattern;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

// for 天意云
public class DatabusTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebElement canva;
    private Rectangle canvaRect;
    private int index = 0;
    private Map<Integer, String> nodeNameCodeMap = new HashMap<>(10);

    public DatabusTest() {
        this.driver = getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(120).getSeconds());
    }

    public WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/webdriver/chrome/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        //WebDriver driver = new ChromeDriver(chromeOptions);
        WebDriver driver = new ChromeDriver();
        //driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return driver;
    }

    public void login() {
        if (logger.isInfoEnabled()) {
            logger.info("开始登录数据总线系统 !");
        }
        driver.get("http://192.168.1.181:8991/paas");
        driver.manage().window().maximize();
        //等待直到出现输入密码的框框
        wait.until(presenceOfElementLocated(By.cssSelector("input[type='password']")));
        //输入用户名
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("ys_user02");
        //输入密码
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("ideal@123");
        //点击登录
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }


    public void jinShanLogin() {
        if (logger.isInfoEnabled()) {
            logger.info("开始登录数据总线系统 !");
        }
        driver.manage().window().maximize();
        driver.get("https://www.sh.ctc.com/BDOP/bddt/admin/portals/index#");
        //找到动态吗认证并切换
        List<WebElement> lis = driver.findElements(By.cssSelector("ul#tab_ul li"));
        for (WebElement li : lis) {
            if (li.getText().contains("动态码认证")) {
                li.click();
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


    }

    public void openDatabus() throws Exception {
        if (logger.isInfoEnabled()) {
            logger.info("登录完成，即将进入数据总线页面！");
        }
        //等待直到导航栏的出现
        wait.until(presenceOfElementLocated(By.cssSelector("div.sub-navBox")));
        List<WebElement> elements = driver.findElement(By.cssSelector("div.sub-navBox")).findElements(By.cssSelector("div.sbox"));
        //找到数据通道与加工的导航栏进行点击，如果不点击页面不会加载数据总线连接
        for (WebElement e : elements) {
            if (e.getText().contains("数据通道与加工")) {
                e.click();
            }
        }
        //wait.until(presenceOfElementLocated(By.cssSelector("a[menuname='数据总线']")));
        // 等待出现数据总线链接
        wait.until(dr -> dr.findElement(By.cssSelector("ul.nav-content")).getText().contains("数据总线"));
        String originalWindow = driver.getWindowHandle();
        List<WebElement> lis = driver.findElement(By.cssSelector("ul.nav-content")).findElements(By.tagName("li"));
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
        return driver.findElement(By.cssSelector("div#workflow>svg")).findElements(By.tagName("svg"));
    }

    public String getNodeName(WebElement svg) {
        return svg.findElement(By.tagName("text")).getAttribute("nodeName");
    }

    public WebElement addCpt2map() {
        WebElement elem = null;
        List<WebElement> svgs = getAllcpts();
        for (WebElement svg : svgs) {
            elem = svg;
            String nodename = getNodeName(svg);
            if (logger.isInfoEnabled()) {
                logger.info(nodename);
            }
            if (!nodeNameCodeMap.containsKey(nodename)) {
                nodeNameCodeMap.put(index++, nodename);
                break;
            }
        }
        return elem;
    }

    public void openCanvas() {
        wait.until(presenceOfElementLocated(By.id("exerciseEditBtn")));
        driver.findElement(By.id("exerciseEditBtn")).click();
        wait.until(ExpectedConditions.textMatches(By.cssSelector("ul#exerciseZtree_1_ul"), Pattern.compile(".*金融.*")));
        List<WebElement> elements = driver.findElements(By.cssSelector("ul#exerciseZtree_1_ul li"));
        for (WebElement element : elements) {
            if (element.getText().contains("金融")) {
                element.findElement(By.tagName("span")).click();
                break;
            }
        }

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("ul#exerciseZtree_2_ul li")));
        List<WebElement> lists = driver.findElements(By.cssSelector("ul#exerciseZtree_2_ul li"));
        for (WebElement li : lists) {
            if (li.getText().contains("ee")) {
                li.findElement(By.cssSelector("span[title='切换作业']")).click();
                break;
            }
        }
        wait.until(presenceOfElementLocated(By.cssSelector("ul#plugin_ztree")));
        //初始化 canva，canvaRect
        getCanva();
        getCanvaRect();
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
    public WebElement dragComponent(String cptName, int x, int y) throws Exception {
        //所有的组件
        List<WebElement> allCpt = driver.findElements(By.cssSelector("ul#plugin_ztree>li"));
        Rectangle rect = getCanvaRect();
        WebElement src = null;
        outter:
        for (WebElement e : allCpt) {
            List<WebElement> cpts = e.findElements(By.tagName("li"));
            inner:
            for (WebElement e2 : cpts) {
                String s = e2.getText();
                if (logger.isInfoEnabled()) {
                    logger.info(s);
                }

                if (s.contains(cptName)) {
                    System.out.println(e2.getTagName());
                    src = e2.findElement(By.tagName("a"));
                    break outter;
                }
            }
        }

        if (src == null) {
            throw new Exception("未找到组件" + cptName);
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
        //conf.config();
    }

    public void link() {

    }

    public void test() {
        try {
            login();
            openDatabus();
            openCanvas();
            WebElement ftp0 = dragComponent("ftp", 0, -200);
            //editCpt(ftp0);
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    public static void main(String[] args) {
        DatabusTest test = new DatabusTest();
        test.test();
    }
}
