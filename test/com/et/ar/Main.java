package com.et.ar;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class Main {
  public static void main(String[] args) throws Exception {
    Connection conn = getConnection();
    Statement st = conn
        .createStatement();

   // st.executeUpdate("create table survey (id int,myDate DATE);");
    String INSERT_RECORD = "insert into survey(id, myDate) values(?, ?)";
    
    PreparedStatement pstmt = conn.prepareStatement(INSERT_RECORD);
    pstmt.setString(1, "1");
    java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
    pstmt.setDate(2, sqlDate);
    
    pstmt.executeUpdate();
    
    ResultSet rs = st.executeQuery("SELECT * FROM survey");

    outputResultSet(rs);

    rs.close();
    st.close();
    conn.close();
  }
  private static void outputResultSet(ResultSet rs) throws Exception {
    ResultSetMetaData rsMetaData = rs.getMetaData();
    int numberOfColumns = rsMetaData.getColumnCount();
    for (int i = 1; i < numberOfColumns + 1; i++) {
      String columnName = rsMetaData.getColumnName(i);
      System.out.print(columnName + "   ");

    }
    System.out.println();
    System.out.println("----------------------");

    while (rs.next()) {
      for (int i = 1; i < numberOfColumns + 1; i++) {
        System.out.print(rs.getString(i) + "   ");
        System.out.print(rs.getTimestamp(i) + "   ");
      }
     
      System.out.println();
    }

  }

  private static Connection getConnection() throws Exception {
    Class.forName("org.sqlite.JDBC");
    String url = "jdbc:sqlite:data/test.db";

    return DriverManager.getConnection(url, "sa", "");
  }
}