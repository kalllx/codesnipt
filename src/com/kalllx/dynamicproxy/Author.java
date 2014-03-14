package com.kalllx.dynamicproxy;

import java.util.List;

import com.kalllx.ardb.annotation.Column;
import com.kalllx.ardb.annotation.Has;
import com.kalllx.ardb.annotation.ID;
import com.kalllx.ardb.annotation.Table;

@Table
public class Author
{
@ID    
int id;
@Column
String name;
@Column
int age;
@Has(M2M=true)
List<Article> articles;
}
