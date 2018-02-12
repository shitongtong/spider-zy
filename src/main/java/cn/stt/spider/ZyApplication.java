package cn.stt.spider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2018/2/11.
 */
public class ZyApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZyApplication.class);

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        double price = 2;
        List<Book> bookTypeList = ZySpider.getBookTypeList();
        Book jsjBook = new Book();
        for (Book book : bookTypeList) {
            String type = book.getType();
            if ("社科".equals(type)) {
                jsjBook = book;
                String typeUrl = book.getTypeUrl();
                LOGGER.info("{}:{}", type, typeUrl);
            }
        }
        List<Book> bookList = new ArrayList<>();
        ZySpider.getBookList(bookList, jsjBook);
        LOGGER.info("bookList.size()={}", bookList.size());
        List<Book> bookDetailList = ZySpider.getBookDetailList(bookList, price);
        LOGGER.info("bookDetailList.size()={}", bookDetailList.size());
        FileWriter fw = new FileWriter(new File("d:\\zybook_" + jsjBook.getType() + "_" + price + ".txt"));
        for (Book book : bookDetailList) {
            fw.write(book.getBookInfo() + System.lineSeparator());
            fw.flush();
        }
        fw.close();
        LOGGER.info("运行时间={}s", (System.currentTimeMillis() - startTime) / 1000);
    }
}
