/**
 * 被观察者具体实现
 */
public class BlogPuber extends Observable {
    private String  BlogPuberName = "pub1";

    public String getBlogPuberName() {
        return BlogPuberName;
    }

    //被观察者
    public void publishBlog(String articleTitle,String articleContent){
        Article article = new Article(articleTitle, articleContent);
        System.out.println("Blog publish success:"+articleTitle+",content:"+articleContent);
        this.setChanged();//设置状态改变
        this.notifyObservers(article);
    }
}
