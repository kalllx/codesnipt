package com.kalllx.dynamicproxy;

import java.util.List;

import com.kalllx.ardb.annotation.Belong;
import com.kalllx.ardb.annotation.Column;
import com.kalllx.ardb.annotation.ID;
import com.kalllx.ardb.annotation.Table;

@Table 
public class Article extends Base
{
    @ID
    int articleId;
    @Column
    String title;
    @Column
    String content;
    
    @Belong
    List<Author> authors;

    public int getArticleId()
    {
        return articleId;
    }

    public void setArticleId(int articleId)
    {
        this.articleId = articleId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public List<Author> getAuthors()
    {
	lazyLoad("getAuthors");
        return authors;
    }

    public void setAuthors(List<Author> authors)
    {
        this.authors = authors;
    }

    @Override
    public String toString()
    {
	return "Article [articleId=" + articleId + ", title=" + title + ", content=" + content + ", authors=" + authors + "]";
    }
    
    
}
