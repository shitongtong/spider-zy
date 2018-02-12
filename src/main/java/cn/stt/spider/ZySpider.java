package cn.stt.spider;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2018/2/11.
 */
public class ZySpider {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZySpider.class);

    /**
     * 获取类型及其链接
     *
     * @return
     * @throws IOException
     */
    public static List<Book> getBookTypeList() throws IOException {
        String zyUrl = "http://www.ireader.com/index.php?ca=booksort.index&pca=booksort.index";
        Document document = Jsoup.connect(zyUrl).get();
        Elements elements = document.select("div.content>div>div.difgenre").get(1).select("div>ul>li>a");
        Iterator<Element> iterator = elements.iterator();
        List<Book> bookList = new ArrayList<>();
        Book book = new Book();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            String hrefUrl = element.attr("href");
            String text = element.text();
            book = new Book();
            book.setType(text);
            book.setTypeUrl(hrefUrl);
            bookList.add(book);
        }
        return bookList;
    }

    public static void getBookList(List<Book> bookList, Book bookType) throws IOException {
        if (bookType == null) {
            return;
        }
        String type = bookType.getType();
        String typeUrl = bookType.getTypeUrl();
        Document document = Jsoup.connect(typeUrl).get();
        Elements elements = document.select("div.show>ul>li>a");
        Iterator<Element> iterator = elements.iterator();
        Book book = new Book();
        while (iterator.hasNext()) {
            book = new Book();
            book.setType(type);
            String href = iterator.next().attr("href");
            book.setBookUrl(href);
            bookList.add(book);
        }
        //下一页
        String href = document.select("div.changepage>span>a.down").attr("href");
        if (StringUtils.isNotBlank(href)) {
            bookType.setTypeUrl(href);
            getBookList(bookList, bookType);
        }
    }

    /**
     * 书籍详情列表
     *
     * @param bookList
     * @param price
     * @return 价格小于price的书籍列表
     */
    public static List<Book> getBookDetailList(List<Book> bookList, double price) {
        if (bookList == null) {
            return null;
        }
        List<Book> books = new ArrayList<>();
        for (Book book : bookList) {
            String bookUrl = book.getBookUrl();
            try {
                Document doc = Jsoup.connect(bookUrl).get();
                Elements bookR = doc.select("div.bookR");
                String priceStr = bookR.select("div.bookinf02>div.left>p>i.price").text();
                if (priceStr.contains("千字")) {
                    continue;
                }
                if (!priceStr.contains("免费")) {
                    double bookPrice = Double.parseDouble(priceStr.replace("价格：", "").replace("元", ""));
                    if (bookPrice > price) {
                        continue;
                    }
                }

                //书籍信息：作者、字数、出版社、评分、评分人数...
                Elements bookInfo1 = bookR.select("div.bookinf01");
                String bookName = bookInfo1.select("div.bookname>h2>a").text();
                String author = bookInfo1.select("p>span").first().text();
                String wordCount = bookInfo1.select("p>span").get(1).text();

                book.setName(bookName);
                book.setAuthor(author);
                book.setWordCount(wordCount);
                book.setPrice(priceStr);

                books.add(book);
            } catch (Exception e) {
                LOGGER.error("bookUrl={}", bookUrl);
                LOGGER.error("{}", e);
            }
        }
        return books;
    }

    /**
     * 获取zy所有书籍类型
     *
     * @return
     * @throws IOException
     */
    public static String getAllTypes() throws IOException {
        String zyUrl = "http://www.ireader.com/index.php?ca=booksort.index&pca=booksort.index";
        Document document = Jsoup.connect(zyUrl).get();
        Elements elements = document.select("div.content>div>div.difgenre").get(1).select("div>ul>li>a");
        return elements.text();
    }
}
