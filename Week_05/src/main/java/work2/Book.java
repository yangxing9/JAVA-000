package work2;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 14:53
 */
@Component("annotationBook")
public class Book {

    private String name;

    private String author;

    private String price;

    public Book(){

    }

    public Book(String name, String author, String price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

    public void printBook(){
        System.out.println("book name : " + this.name + ",anthor : " + this.author + ", price : " + this.price);
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
