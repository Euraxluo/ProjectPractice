/**
 * 某个产品(事件)
 */
public class Article {
    //实体类
    private String articleTitle;
    private String articleContent;

    public Article(String articleTitle, String articleContent) {
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }
}
