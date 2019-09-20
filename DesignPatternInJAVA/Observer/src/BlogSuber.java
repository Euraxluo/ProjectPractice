/**
 * 观察者具体实现
 */
public class BlogSuber implements Observer{
    @Override
    public void update(Observable o, Object arg) {
        Article article = (Article) arg;
        System.out.println("订阅数:"+o.countObservers()+"的大博主发布了文章!");
        System.out.println("title:"+article.getArticleTitle());
        System.out.println("content:"+article.getArticleContent());
    }
}
