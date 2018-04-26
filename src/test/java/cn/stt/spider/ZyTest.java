package cn.stt.spider;

import org.junit.Test;
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
public class ZyTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZyTest.class);

    @Test
    public void test1() throws IOException {
        System.out.println(ZySpider.getAllTypes());
        //小说 文学 传记 历史 青春 心理 社科 励志 政治 军事 经济 管理 理财 旅游 美食 时尚 健身 孕产 少儿 亲子 科学 科普 医学 教辅 外语 工业 宗教 哲学 文化 艺术 修养 养生 两性 风水 家居 建筑 法律 农业 休闲 体育 外文 计算机 国学经典 掌阅公版
    }

    @Test
    public void test2() throws IOException {
        List<Book> bookTypeList = ZySpider.getBookTypeList();
        for (Book bookType : bookTypeList) {
            long startTime = System.currentTimeMillis();
            String type = bookType.getType();
            List<Book> bookList = new ArrayList<>();
            ZySpider.getBookList(bookList, bookType);
            LOGGER.info("bookList.size()={}", bookList.size());
            double price = 1;
            List<Book> bookDetailList = ZySpider.getBookDetailList(bookList, price);
            LOGGER.info("bookDetailList.size()={}", bookDetailList.size());
            FileWriter fw = new FileWriter(new File("d:\\zybook_" + type + "_" + price + ".txt"));
            for (Book book : bookDetailList) {
                fw.write(book.getBookInfo() + System.lineSeparator());
                fw.flush();
            }
            fw.close();
            LOGGER.info("{} 运行时间={}s", type, (System.currentTimeMillis() - startTime) / 1000);
        }

    }
}
