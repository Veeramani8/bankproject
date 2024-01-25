import java.sql.*;
import java.util.Scanner;

abstract  class Bank {
    long accno;
    String name;
    long mob;
    long curbal;
     public abstract void deposit() throws Exception;
    public abstract void withdraw() throws Exception;
    public abstract void balance() throws Exception;

}
class customer extends Bank  {

    Scanner sc=new Scanner(System.in);
    @Override
    public void balance() throws Exception {
        Connection con = jdbc.getConnection();
        String q="select * from accdet where accno = ?";
        PreparedStatement ps=con.prepareStatement(q);
        ps.setLong(1,accno);
        ResultSet rs=ps.executeQuery();
        while (rs.next()){
            System.out.println(rs.getLong("curbal"));
        }
    }

    @Override
    public void deposit() throws Exception {
        Connection con = jdbc.getConnection();
        String q="select * from accdet where accno= ? and mob=?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1,accno);
        ps.setLong(2,mob);
        ResultSet rs = ps.executeQuery();
        long ba=0;
        while(rs.next()) {
            long no = rs.getLong("accno");
            long mo = rs.getLong("mob");
            ba = rs.getLong("curbal");
            String na = rs.getString("name");
        }

        long in=sc.nextLong();
        String r="update accdet set curbal=? where accno = ?";
        PreparedStatement pr=con.prepareStatement(r);
        pr.setLong(1,in+ba);
        pr.setLong(2,accno);
        int i=pr.executeUpdate();
        if (i>0){
            System.out.println("deposit successful");
            balance();
        }

    }

    @Override
    public void withdraw() throws Exception {
        Connection con = jdbc.getConnection();
        String q="select * from accdet where accno= ? and mob=?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1,accno);
        ps.setLong(2,mob);
        ResultSet rs = ps.executeQuery();
        long ba=0;
        while(rs.next()) {
            long no = rs.getLong("accno");
            long mo = rs.getLong("mob");
            ba = rs.getLong("curbal");
            String na = rs.getString("name");
        }

        long wi=sc.nextLong();

        String r="update accdet set curbal=? where accno = ?";
        PreparedStatement pr=con.prepareStatement(r);
        pr.setLong(1,ba-wi);
        pr.setLong(2,accno);
        int i=pr.executeUpdate();
        if (i>0){
            System.out.println("withdraw success");
            balance();
        }
    }
}
class run{
    public static void main(String[] args) throws Exception{
        Connection con = jdbc.getConnection();
        Scanner obj=new Scanner(System.in);
        customer obj1=new customer();
        System.out.println("ENTER ACCOUNT NO.");
        obj1.accno=obj.nextLong();
        System.out.println("ENTER MOBILE NO.");
        obj1.mob=obj.nextLong();
        String q="select * from accdet where accno= ? and mob=?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj1.accno);
        ps.setLong(2, obj1.mob);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            long no=rs.getLong("accno");
            long mo=rs.getLong("mob");
            long ba=rs.getLong("curbal");
            String na=rs.getString("name");
            System.out.println("acc no is:"+no);
            System.out.println("mobile:"+mo);
            System.out.println("name:"+na);
            System.out.println("balance:"+ba);
            System.out.println("1.BALANCE");
            System.out.println("2.DEPOSIT");
            System.out.println("3.WITHDRAW");
            System.out.println("4.EXIT");
            System.out.println("Enter the option:");
            int b= obj.nextInt();
            switch (b){
                case 1:
                    obj1.balance();
                    break;
                case 2:
                    obj1.deposit();
                    break;
                case 3:
                    obj1.withdraw();
                    break;
                case 4:
                    System.exit(0);
                    break;
            }
        }
    }
}
class jdbc {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "123456");
    }
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
