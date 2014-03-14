package com.kalllx.exception;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

public class TestProcedureOne {

public TestProcedureOne() {

}

public static void main(String[] args) {

String driver = "oracle.jdbc.driver.OracleDriver";
String strUrl = "jdbc:oracle:thin:@//hz2dl1016:1523/GEC.LOCAL.SHARE";
Connection conn = null;
CallableStatement cstmt = null;

try {

Class.forName(driver);
conn = DriverManager.getConnection(strUrl, "XUYONG_TEST", "XUYONG_TEST");

cstmt = conn.prepareCall("{call get_tee_2()}");

/*cstmt.registerOutParameter(1, Types.INTEGER);

cstmt.setInt(2,4);
cstmt.setInt(3,5);
cstmt.setInt(4,6);*/

cstmt.execute();
//System.out.println("调用函数add_three_numbers:"+cstmt.getInt(1));

} catch (SQLException ex2) {
ex2.printStackTrace();
} catch (Exception ex2) {
ex2.printStackTrace();
}

finally {

try {

if (cstmt != null) {
cstmt.close();
if (conn != null) {
conn.close();
}
}
} catch (SQLException e) {
System.out.println("SQL state" + e.getSQLState());
System.out.println("错误消息" + e.getMessage());
System.out.println("错误代码" + e.getErrorCode());
e.printStackTrace();
}

}

}

}