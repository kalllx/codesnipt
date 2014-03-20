package com.et.ar;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.et.ar.annotations.Column;
import com.et.ar.annotations.DependentType;
import com.et.ar.annotations.HasMany;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.et.ar.exception.ActiveRecordException;

public class ARTest
{

    @Test
    public void testAdd() throws ActiveRecordException
    {
	User user = new User();
	user.setName("name1");
	user.setAddr("addr1");
	user.setEmail("name1@gmail.com");
	user.setTime(new Time(new java.util.Date().getTime()) );
	user.setDate(new Date(new java.util.Date().getTime()) );
	user.setTimestamp(new Timestamp(new java.util.Date().getTime()));
	List<Book> books = new ArrayList<Book>();
	Book book1 = new Book();
	book1.setName("kalll不是");
	books.add(book1);
	Book book2  = new Book();
	book2.setName("whooo");
	books.add(book2);
	user.setBooks(books);
	
	/*
	 *    long total = User.count(User.class, null, null);
        List<User> users = User.findAll(User.class, null, null, "id", limit, start);

        Map<String,Object> result = new HashMap<String,Object>();
        result.put("total", total);
        result.put("users", users);
	 */
	user.save();
	System.out.println(user.getId());
    }

    @Test
    public void testUpdate() throws ActiveRecordException
    {
	User user = User.find(User.class, 2);
	user.setRemark("user remark");
	//user.getBooks();
	user.save();
    }

    @Test
    public void testDelete() throws ActiveRecordException
    {
	User user = User.find(User.class, 4);
	user.getName();
	for(Book book: user.getBooks())
	{
	    System.out.println(book.getName());
	}
	//user.destroy();
    }

    @Test
    public void testSelectAll() throws ActiveRecordException
    {
	List<User> users = User.findAll(User.class);
	for (User user : users)
	{
	    System.out.println(user.getName());
	}
	
	System.out.println(Json.toJson(users));
    }

    @Test
    public void testSelectAllWithWhere() throws ActiveRecordException
    {
	List<User> users = User.findAll(User.class, "addr like ?", new Object[]
		 
	{ "%百花路%" });
	for (User user : users)
	{
	    System.out.println(user.getName());
	}
    }
    
    
    @Test
    public void testJson() throws ActiveRecordException
    {
	User user = new User();
	user.setName("name1");
	user.setAddr("addr1");
	user.setEmail("name1@gmail.com");
	System.out.println(Json.toJson(user));
    }

    
    public static void main(String[] args)
    {
	  OrmInfo orm = OrmInfo.getOrmInfo(User.class);
	  orm = OrmInfo.getOrmInfo(User.class);
/*	StringBuffer strSQL = new StringBuffer();
	strSQL.append("CREATE TABLE IF NOT EXISTS ");
	strSQL.append(orm.table);
	strSQL.append(" ( ");
	if (null != orm.id)
	{
	    if (orm.idType == int.class || orm.idType == Integer.class)
		strSQL.append(orm.id).append("   ").append("INTEGER PRIMARY KEY AUTOINCREMENT,");
	    else
		strSQL.append(orm.id).append("   ").append("TEXT PRIMARY KEY,");
	}

	for (ColumnField field : orm.columnFields)
	{
	    strSQL.append(field.getName() + " ").append(Adapter.java2SqlMaps.get(field.getType()) + ",");
	}
	strSQL.deleteCharAt(strSQL.length() - 1);
	strSQL.append(")");
	System.out.println(strSQL);*/
    }
    
}
/*
create table user (id integer primary KEY autoincrement,  name text,  addr text, email text, remark text,  date text,  time text,  timestamp text,b text);
 */
@Table(name = "user")
class User extends ActiveRecordBase
{
    @Id
    private Integer id;
    @Column
    private String name;
    @Column
    private String addr;
    @Column
    private String email;
    @Column
    private String remark;
    @Column
    private Date date;
    @Column
    private Time time;
    @Column
    private Timestamp timestamp;
    @Column
    private boolean b;
    @HasMany(foreignKey="owerId", dependent=DependentType.DESTROY)
    private List<Book> books;
    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    public String getName()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public String getAddr()
    {
	return addr;
    }

    public void setAddr(String addr)
    {
	this.addr = addr;
    }

    public String getEmail()
    {
	return email;
    }

    public void setEmail(String email)
    {
	this.email = email;
    }

    public String getRemark()
    {
	return remark;
    }

    public void setRemark(String remark)
    {
	this.remark = remark;
    }

    public List<Book> getBooks()
    {
        return books;
    }

    public void setBooks(List<Book> books)
    {
        this.books = books;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public Time getTime()
    {
        return time;
    }

    public void setTime(Time time)
    {
        this.time = time;
    }

    public Timestamp getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp)
    {
        this.timestamp = timestamp;
    }

    public Boolean getB()
    {
        return b;
    }

    public void setB(Boolean b)
    {
        this.b = b;
    }
    
    

}
@Table(name = "book")
class Book extends ActiveRecordBase{
    @Id
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer owerId;
    public Integer getId()
    {
        return id;
    }
    public void setId(Integer id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public Integer getOwerId()
    {
        return owerId;
    }
    public void setOwerId(Integer owerId)
    {
        this.owerId = owerId;
    }
    
}
