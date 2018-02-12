package cn.stt.spider;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2018/2/11.
 */
public class Book {
    private String type;
    private String typeUrl;
    private String bookUrl;
    private String name;
    private String author;
    private String wordCount;
    private String price;

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public String getTypeUrl() {
        return typeUrl;
    }

    public void setTypeUrl(String typeUrl) {
        this.typeUrl = typeUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getWordCount() {
        return wordCount;
    }

    public void setWordCount(String wordCount) {
        this.wordCount = wordCount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBookInfo() {
        return "Book{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", wordCount='" + wordCount + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
