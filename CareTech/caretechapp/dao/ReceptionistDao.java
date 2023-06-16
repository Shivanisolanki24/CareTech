package caretechapp.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import caretechapp.dbutil.DBConnection;
import caretechapp.pojo.DoctorPojo;
import caretechapp.pojo.ReceptionistPojo;
import caretechapp.pojo.UserProfile;
import caretechapp.pojo.UserPojo;
import caretechapp.dao.UserDao;
import caretechapp.dao.EmpDao;
import caretechapp.pojo.EmpPojo;

public class ReceptionistDao {
     public static void updateName(String currName,String newName)throws SQLException{
     Connection conn=DBConnection.getConnection();
     PreparedStatement ps=conn.prepareStatement("Update receptionists set receptionist_name=? where receptionist_name=?");
     ps.setString(1, newName);
     ps.setString(2, currName);
     ps.executeUpdate();
}
  public static String getNewRecId()throws  SQLException{
   Connection conn=DBConnection.getConnection();
   Statement st=conn.createStatement();
   ResultSet rs=st.executeQuery("Select max(receptionist_id) from receptionists");
   rs.next();
   int recId=101;
   String id=rs.getString(1);
   if(id!=null){
       String num=id.substring(3);
       recId=Integer.parseInt(num)+1;
    }
   return "REC"+recId;
}      
  public static boolean addReceptionist(ReceptionistPojo rec)throws SQLException{
     Connection conn=DBConnection.getConnection();
     PreparedStatement ps=conn.prepareStatement("Insert into receptionists values(?,?,?)");
     ps.setString(1,rec.getReceptionistId());
     ps.setString(2,rec.getReceptionistName());
     ps.setString(3,rec.getGender());
     
     return ps.executeUpdate()==1;
}
  public static List<ReceptionistPojo> getAllReceptionistDetails()throws SQLException{
    Connection conn=DBConnection.getConnection();
    Statement st=conn.createStatement();
    ResultSet rs=st.executeQuery("Select * from receptionists order by receptionist_id");
    List<ReceptionistPojo>recList=new ArrayList<>();
    while(rs.next()){
    ReceptionistPojo recep=new ReceptionistPojo();
    recep.setReceptionistId(rs.getString(1));
    recep.setReceptionistName(rs.getString(2));
    recep.setGender(rs.getString(3));
    recList.add(recep);
    
    }
    return recList;
} 
  public static boolean deleteReceptionistById(String recId)throws SQLException{
     Connection conn=DBConnection.getConnection();
     PreparedStatement ps=conn.prepareStatement("Select receptionist_name from  receptionists where  receptionist_id=?");
     ps.setString(1, recId);
     ResultSet rs=ps.executeQuery();
     rs.next();
     String recName=rs.getString(1);
     UserDao.deleteUserByName(recName);
     ps=conn.prepareStatement("Delete from  receptionists where  receptionist_id=?");
     ps.setString(1, recId);
     return ps.executeUpdate()==1;
}
  public static void deleteReceptionistByName(String name)throws SQLException{
     Connection conn=DBConnection.getConnection();
     PreparedStatement ps=conn.prepareStatement("delete from receptionists  where receptionist_name=?");
     ps.setString(1, name);
     ps.executeUpdate();
}
  public static List<String> getAllReceptionistId()throws SQLException{
    Connection conn=DBConnection.getConnection();
    Statement st=conn.createStatement();
    ResultSet rs=st.executeQuery("Select receptionist_id from receptionists");
    List<String> recIdList=new ArrayList<>();
    while(rs.next()){
        recIdList.add(rs.getString(1));
    }
    return recIdList;
}  
  public static ReceptionistPojo getReceptionistDetails(String receId)throws SQLException{
    Connection conn=DBConnection.getConnection();
    PreparedStatement ps=conn.prepareStatement("Select * from receptionists where receptionist_id=?");
    ps.setString(1,receId);
    ResultSet rs=ps.executeQuery();
    rs.next();
    ReceptionistPojo rec=new ReceptionistPojo();
    rec.setReceptionistId(rs.getString(1));
    rec.setReceptionistName(rs.getString(2));
    rec.setGender(rs.getString(3));
    return rec;
}
  public static boolean updateReceptionist(ReceptionistPojo rec)throws SQLException{
     updateName(rec);
     Connection conn=DBConnection.getConnection();
     PreparedStatement ps=conn.prepareStatement("Update receptionists set receptionist_name=?, gender=? where receptionist_id=?");
     ps.setString(1, rec.getReceptionistName());
     ps.setString(2, rec.getGender());
     ps.setString(3, rec.getReceptionistId());
     return ps.executeUpdate()==1;
 }
  public static void updateName( ReceptionistPojo rec)throws SQLException{
     Connection conn=DBConnection.getConnection();
     PreparedStatement ps=conn.prepareStatement("Select receptionist_name from receptionists where receptionist_id=?");
     ps.setString(1, rec.getReceptionistId());
     ResultSet rs=ps.executeQuery();
     rs.next();
     String currName=rs.getString(1);
     String newName=rec.getReceptionistName();
     UserDao.updateName(currName,newName);
     EmpDao.updateName(currName, newName);
     
 }
}