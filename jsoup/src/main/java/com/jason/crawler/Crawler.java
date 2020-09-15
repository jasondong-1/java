package com.jason.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;


public class Crawler {
    private static String[] uas = {
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1",
            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:6.0) Gecko/20100101 Firefox/6.0",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
            "Mozilla/5.0 (Windows; U; Windows NT 6.1; ) AppleWebKit/534.12 (KHTML, like Gecko) Maxthon/3.0 Safari/534.12",
            "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.41 Safari/535.1 QQBrowser/6.9.11079.201",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36Edge/13.10586",
            "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.94 Safari/537.36",
            "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0",
            "Opera/9.80 (Windows NT 6.1; WOW64; U; en) Presto/2.10.229 Version/11.62"
    };

    private static int index = Math.abs(new Random().nextInt());

    private String getUa() {
        return uas[(index++) % (uas.length)];
    }

    public void livermore() throws IOException {
        File input = new File("/home/jason/Documents/港股打新/全年统计/livermore.html");
        Document doc = Jsoup.parse(input, "UTF-8", "https://www.jesselivermore.com/");
        Element element = doc.getElementById("statistics");
        Elements trs = element.getElementsByTag("tr");
        PrintWriter out = new PrintWriter("/home/jason/Documents/港股打新/全年统计/livermore");
        for (Element e : trs) {
            Elements tds = e.getElementsByTag("td");
            List<String> builder = new ArrayList<>();
            for (Element td : tds) {
                builder.add(td.text().trim());
            }
            String codeAndName = builder.get(0);
            String[] arr = codeAndName.split("\\s+", -1);
            String[] nameArr = Arrays.copyOf(arr, arr.length - 1);
            String name = String.join(" ", nameArr);
            String code = arr[arr.length - 1];
            builder.set(0, name);
            builder.add(1, code);
            out.println(String.join("\t", builder));
        }
        out.flush();
        out.close();
    }

    public void aastock() throws FileNotFoundException {
        //PrintWriter out = new PrintWriter("/home/jason/Documents/港股打新/全年统计/aastock");
        PrintWriter out = new PrintWriter(new FileOutputStream("/home/jason/Documents/港股打新/全年统计/aastock", true), true);
        for (int i = 2; i <= 11; i++) {
            System.out.println("开始爬取第 " + i + " 页！");
            try {
                getAStockInfo(i, out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        out.flush();
        out.close();

    }

    private Document getDoc(String url) {
        boolean stop = false;
        Document doc = null;

        for (int i = 0; i < 4; i++) {
            System.out.println(url + " 尝试第 " + i + "次！");
            try {
                Thread.sleep(200);
                doc = Jsoup.connect(url).timeout(100000)
                        .userAgent(getUa())
                        .get();
                stop = true;
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            if (stop) {
                break;
            }
        }
        return doc;
    }

    private void getOtherInfo(List<String> list, String url, String baseurl) throws IOException {
        /*Document doc = getDoc(url);
        if(doc ==null){
            return;
        }
        Element li = doc.getElementsByClass("tabMenu").get(1);
        String ref = li.getElementsByTag("li").get(2).getElementsByTag("a").first().attr("href");*/
        //Document doc2 = getDoc(baseurl + ref);
        Document doc2 = getDoc(url);
        if (doc2 == null) {
            return;
        }
        //Elements tbs = doc2.select("table.ns2 mar15T");
        Elements trs = doc2.select("div#UCIPOInfo").first().getElementsByTag("div").first().getElementsByTag("table").first().getElementsByTag("tr");
        for (int i = 0; i < 6; i++) {
            Element e = trs.get(i);
            String s = e.getElementsByTag("td").get(1).text();
            list.add(s);
        }
    }

    public void getAStockInfo(int page, PrintWriter out) throws IOException {
        //需要补充page
        String baseUrl = "http://www.aastocks.com";
        String url = "http://www.aastocks.com/sc/stocks/market/ipo/listedipo.aspx?s=3&o=0&page=" + page;
        Document doc = getDoc(url);
        if (doc == null) {
            return;
        }
        Element list = doc.getElementById("IPOListed");
        Element tbody = list.getElementsByTag("tbody").first();
        Elements trs = tbody.getElementsByTag("tr");//.getElementsByTag("td").get(1);
        for (Element tr : trs) {
            Elements tds = tr.getElementsByTag("td");
            List<String> lx = new ArrayList<>(15);
            for (Element td : tds) {
                Elements as = td.getElementsByTag("a");
                //name  and code
                if (!as.isEmpty()) {
                    String s = null;
                    for (int i = 0; i < 2; i++) {
                        Element a = as.get(i);
                        /*if (s == null) {
                            s = a.attr("href");
                        }*/
                        String text = a.text();
                        lx.add(text);
                        if (i == 1) {
                            s = text.replace(".HK", "");
                        }
                    }
                    String urlx = "http://www.aastocks.com/sc/stocks/market/ipo/upcomingipo/ipo-info?symbol=" + s + "&s=3&o=1#info";
                    //System.out.println(urlx);
                    getOtherInfo(lx, urlx, baseUrl);
                } else {
                    lx.add(td.text());
                }
            }
            out.println(String.join("\t", lx));
        }

    }

    private String handle(String s) {
        if (s == null) {
            throw new NullPointerException();
        }
        return s.replaceAll("\t", "");
    }

    public void pdfInfo() throws FileNotFoundException {
        File file = new File("/home/jason/Documents/港股打新/全年统计/htmlx");
        PrintWriter out = new PrintWriter(new FileOutputStream("/home/jason/Documents/港股打新/全年统计/pdf", false), true);
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
        for (File f : files) {
            Document doc = null;
            try {
                doc = Jsoup.parse(f, "UTF-8", "https://www.jesselivermore.com/");
                Element div = doc.select("div#titleSearchResultPanel").first();
                Element tbody = div.getElementsByTag("tbody").first();
                Elements trs = tbody.getElementsByTag("tr");
                for (Element tr : trs) {
                    tr.getElementsByTag("span").remove();
                    Elements tds = tr.getElementsByTag("td");
                    List<String> list = new ArrayList<>(6);
                    for (int i = 0; i < 4; i++) {
                        Element td = tds.get(i);
                        if (i == 3) {
                            Elements divs = td.getElementsByTag("div");
                            Element div1 = divs.get(0);
                            Element div2 = divs.get(1);
                            list.add(handle(div1.text()));
                            list.add(handle(div2.text()));
                            list.add(handle(div2.getElementsByTag("a").attr("href")));
                        } else {
                            list.add(handle(td.text()));
                        }
                    }
                    out.println(String.join("\t", list));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        out.close();

    }

    public String shoupanjia(String code) {
        //sogo
        //Document doc = getDoc("https://www.sogou.com/web?query=03680%E6%9A%97%E7%9B%98%E6%94%B6&_ast=1580903784&_asf=www.sogou.com&w=01029901&p=40040100&dp=1&cid=&s_from=result_up");
        Document doc = getDoc("https://www.so.com/s?q=" + code + "%E6%9A%97%E7%9B%98%E6%94%B6&src=srp&fr=none&psid=29e423d3ded247533641b28caaa4c619");
        String tab = "\t\t";
        if (doc == null) {
            return code + tab;
        }

        Element div = doc.selectFirst("ul.result");
        if (div == null) {
            return code + tab;
        }
        Elements lis = div.getElementsByTag("li");
        List<String> list = new ArrayList<>(3);

        for (int i = 0; i < 3; i++) {
            String cd = "";
            if (i == 0) {
                cd = code;
            }
            list.add(cd + tab + lis.get(i).text());
        }
        return String.join("\n", list);
    }

    public void genshoupanjia() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/home/jason/Documents/港股打新/全年统计/codex"))));
             PrintWriter out = new PrintWriter("/home/jason/Documents/港股打新/全年统计/shoupanprice");
        ) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                String shou = shoupanjia2(line);
                System.out.println(shou);
                out.println(shou);
                Thread.sleep(5000);
            }
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String shoupanjia2(String code) {
        System.setProperty("webdriver.chrome.driver", "/usr/local/webdriver/chrome/chromedriver");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
        String tag = "\t\t";
        String res = code + tag;
        try {
            driver.get("https://www.so.com/s?q=" + code + "%E6%9A%97%E7%9B%98%E6%94%B6&src=srp&fr=none&psid=5fa975a3e3291fe1ab6a54a257491b9e");
            //driver.findElement(By.name("q")).sendKeys("cheese" + Keys.ENTER);
            WebElement we = driver.findElement(By.className("result"));
            List<WebElement> list = we.findElements(By.tagName("li"));
            List<String> arr = new ArrayList<>(3);
            for (int i = 0; i < 3; i++) {
                String text = list.get(i).findElement(By.tagName("div")).getText();
                if (i == 0) {
                    arr.add(code + tag + text);
                } else {
                    arr.add("" + tag + text);
                }
            }
            res = String.join("\n", arr);
            //WebElement firstResult = wait.until(presenceOfElementLocated(By.cssSelector("h3>div")));
            //System.out.println(firstResult.getAttribute("textContent"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        return res;
    }

    public void genShoupanjia2() {
        WebDriver driver = getDriver();

        Random ran = new Random();
        BufferedReader reader = null;
        PrintWriter out = null;
        try {
            driver.get("https://www.so.com");

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/home/jason/Documents/港股打新/全年统计/codex"))));
            out = new PrintWriter("/home/jason/Documents/港股打新/全年统计/shoupanprice4");
            String line = null;
            while ((line = reader.readLine()) != null) {
                String code = line.trim();
                String s = shoupanjia(code, driver);
                out.println(s);
                out.flush();
                //Thread.sleep((long) (Math.ceil(Math.random() * 4) * 1000));
            }
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.close();
            driver.quit();
        }

    }

    public String shoupanjia(String code, WebDriver driver) {
        String tag = "\t\t";
        String res = code + tag;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20).getSeconds());
        try {
            for (int m = 0; m < 2; m++) {
                driver.findElement(By.name("q")).clear();
                //Thread.sleep(1000);
                driver.findElement(By.name("q")).sendKeys(code + "暗盘收" + Keys.ENTER);
                //Thread.sleep(3000);
                //wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("result")));
                boolean is = wait.until(dr -> dr.findElement(By.cssSelector("ul.result")).getText().contains(code));
                WebElement we = driver.findElement(By.cssSelector("ul.result"));
                List<WebElement> list = we.findElements(By.tagName("li"));
                //System.out.println();
                List<String> arr = new ArrayList<>(3);
                int i = 0;
                for (WebElement wt : list) {
                    WebElement div = null;
                    try {
                        div = wt.findElement(By.tagName("div"));
                    } catch (Exception e) {
                        continue;
                    }

                    String text = div.getText();
                    //System.out.println(text);
                    if (i == 0) {
                        arr.add(code + tag + text);
                    } else {
                        arr.add("" + tag + text);
                    }

                    if (i == 2) {
                        break;
                    }
                    i++;
                }
                res = String.join("\n", arr);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(driver.getPageSource());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return res;
    }

    public WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/webdriver/chrome/chromedriver");
        WebDriver driver = new ChromeDriver();
        //driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return driver;
    }

    public void test() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/webdriver/chrome/chromedriver");
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://www.iqiyi.com");
            driver.findElement(By.className("search-box-input")).sendKeys("青蜂侠" + Keys.ENTER);
            //WebElement we = driver.findElement(By.className("result"));

        } catch (Exception e) {
            e.printStackTrace();
            try {
                Thread.sleep(100000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        } finally {
            driver.quit();
        }
    }

    private void getKaiPanJia(String content, String filename) {
        WebDriver driver = getDriver();
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/home/jason/Documents/港股打新/全年统计/codex"))));
                PrintWriter out = new PrintWriter("/home/jason/Documents/港股打新/全年统计/"+filename);
        ) {
            driver.get("https://www.baidu.com");
            String line = null;
            while ((line = reader.readLine()) != null) {
                String code = line.trim();
                kaipanjia(driver, code, out,content);
                out.flush();
                Thread.sleep((long) (Math.ceil(Math.random() * 4) * 1000));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private void kaipanjia(WebDriver driver, String code, PrintWriter out,String content) {
        String tab = "\t\t";
        try {
            driver.findElement(By.cssSelector("input#kw")).clear();
            driver.findElement(By.cssSelector("input#kw")).sendKeys(code + content + Keys.ENTER);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20).getSeconds());
            wait.until(dr -> dr.findElement(By.cssSelector("div#content_left>div:nth-of-type(1)")).getText().contains(code));

            List<WebElement> wes = driver.findElements(By.cssSelector("div#content_left>div"));
            List<String> list = new ArrayList<>(3);
            int i = 0;
            for (WebElement we : wes) {
                String s = (we.getText()).replaceAll("[\n]", "\n\t\t");
                if (i == 0) {
                    list.add(code + tab + s);
                } else {
                    list.add("" + tab + s);
                }
                i++;
                if (i == 3) {
                    break;
                }
            }
            //System.out.println(String.join("\n",list));
            out.println(String.join("\n", list));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Crawler crawler = new Crawler();
        //crawler.livermore();
        //crawler.aastock();
        //crawler.pdfInfo();
        //System.out.println(crawler.shoupanjia("00924"));
        //crawler.genshoupanjia();
        //crawler.genShoupanjia2();
        //crawler.test();
        //crawler.kaipanjia("00924");
        //crawler.getKaiPanJia("首日","kaipanprice");
        crawler.getKaiPanJia("暗盘开","暗盘开盘价");
    }
}
