
package caretechapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import caretechapp.dbutil.DBConnection;
import caretechapp.pojo.PatientPojo;
import java.util.ArrayList;
import java.util.List;
import caretechapp.pojo.DoctorPojo;


        
public class PatientDao {
  public static String getNewPatientId()throws  SQLException{
   Connection conn=DBConnection.getConnection();
   Statement st=conn.createStatement();
   ResultSet rs=st.executeQuery("Select max(patient_id) from patients");
   rs.next();
   int patId=101;
   String id=rs.getString(1);
   if(id!=null){
       String num=id.substring(3);
       patId=Integer.parseInt(num)+1;
    }
   return "PAT"+patId;
} 
  public static boolean addPatient(PatientPojo pat)throws SQLException{
     Connection conn=DBConnection.getConnection();
     PreparedStatement ps=conn.prepareStatement("Insert into patients values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
     ps.setString(1,pat.getPatientId());
     ps.setString(2, pat.getFirstName());
     ps.setString(3,pat.getLastName());
     ps.setInt(4, pat.getAge());
     ps.setString(5, pat.getGender());
     ps.setString(6, pat.getMarriedStatus());
     ps.setString(7, pat.getAddress());
     ps.setString(8,pat.getCity());
     ps.setString(9, pat.getMno());
     ps.setDate(10, pat.getDate());
     ps.setInt(11, pat.getOtp());
     ps.setString(12,pat.getOpd());
     ps.setString(13, pat.getDoctorId());
     ps.setString(14, pat.getAptStatus());
     return ps.executeUpdate()==1;
}
    public static List<PatientPojo> getAllPatientDetails()throws SQLException{
    Connection conn=DBConnection.getConnection();
    Statement st=conn.createStatement();
    ResultSet rs=st.executeQuery("Select * from patients order by patient_id");
    List<PatientPojo>patList=new ArrayList<>();
    while(rs.next()){
    PatientPojo pat=new PatientPojo();
    pat.setPatientId(rs.getString("patient_id"));
    pat.setFirstName(rs.getString("first_name"));
    pat.setLastName(rs.getString("last_name"));
    pat.setAge(rs.getInt("age"));
    pat.setGender(rs.getString("gender"));
    pat.setMarriedStatus(rs.getString("m_status"));
    pat.setAddress(rs.getString("address"));
    pat.setCity(rs.getString("city"));
    pat.setMno(rs.getString("mobile_no"));
    pat.setDate(rs.getDate("p_date"));
    pat.setOpd(rs.getString("opd"));
    pat.setAptStatus(rs.getString("status"));
    pat.setDoctorId(rs.getString("doctor_id"));
    patList.add(pat);   
    
    }
    return patList;
} 
  public static List<PatientPojo> getAllPatientsByDoctorId(String doctorId)throws SQLException{
    Connection conn=DBConnection.getConnection();
    PreparedStatement ps=conn.prepareStatement("Select * from patients where doctor_id=? and status='REQUEST' order by patient_id");
    ps.setString(1,doctorId);
    ResultSet rs=ps.executeQuery();
    List<PatientPojo>patList=new ArrayList<>();
    while(rs.next()){
    PatientPojo pat=new PatientPojo();
    pat.setPatientId(rs.getString("patient_id"));
    pat.setFirstName(rs.getString("first_name")+" "+rs.getString("last_name"));
    pat.setDate(rs.getDate("p_date"));
    pat.setOpd(rs.getString("opd"));
    patList.add(pat);   
    
    }
    return patList;
} 
  public static boolean updateStatus(String patientId)throws SQLException{
     Connection conn=DBConnection.getConnection();
     PreparedStatement ps=conn.prepareStatement("Update patients set status='CONFIRM' where patient_id=?" );
     ps.setString(1, patientId);
     return ps.executeUpdate()==1;
     
     
 }
   public static List<String> getAllPatientId()throws SQLException{
    Connection conn=DBConnection.getConnection();
    Statement st=conn.createStatement();
    ResultSet rs=st.executeQuery("Select patient_id from patients");
    List<String> patIdList=new ArrayList<>();
    while(rs.next()){
        patIdList.add(rs.getString(1));
    }
    return patIdList;
}  
    public static boolean updatePatient(PatientPojo pat)throws SQLException{
     Connection conn=DBConnection.getConnection();
     PreparedStatement ps=conn.prepareStatement("Update  patients set first_name=?, last_name=?,Age=?, gender=?, m_status=?, address=?, city=?, mobile_no=?, p_date=?, opd=?, doctor_id=?, status=? where patient_id=?");
     ps.setString(1, pat.getFirstName());
     ps.setString(2, pat.getLastName());
     ps.setInt(3, pat.getAge());
     ps.setString(4, pat.getGender());
     ps.setString(5, pat.getMarriedStatus());
     ps.setString(6, pat.getAddress());
     ps.setString(7, pat.getCity());
     ps.setString(8, pat.getMno());
     ps.setDate(9, pat.getDate());
     ps.setString(10, pat.getOpd());
     ps.setString(11, pat.getDoctorId());
     ps.setString(12, pat.getAptStatus());
     ps.setString(13, pat.getPatientId());
     return ps.executeUpdate()==1;
 }
     public static PatientPojo getAllPatientDetails(String patId)throws SQLException{
    Connection conn=DBConnection.getConnection();
    PreparedStatement ps=conn.prepareStatement("Select * from patients where patient_id=?");
    ps.setString(1,patId);
    ResultSet rs=ps.executeQuery();
    rs.next();
    PatientPojo pat=new PatientPojo();
    pat.setFirstName(rs.getString(2));
    pat.setLastName(rs.getString(3));
    pat.setAge(rs.getInt(4));                  
    pat.setGender(rs.getString(5));
    pat.setMarriedStatus(rs.getString(6));
    pat.setAddress(rs.getString(7));
    pat.setCity(rs.getString(8));
    pat.setMno(rs.getString(9));
    pat.setDate(rs.getDate(10));
    pat.setOpd(rs.getString(12));
    pat.setDoctorId(rs.getString(13));
    pat.setAptStatus(rs.getString(14));
    return pat;
       
     }
    public static boolean deletePatientById(String patId)throws SQLException{
     Connection conn=DBConnection.getConnection();
     PreparedStatement ps=conn.prepareStatement("Select from patients where patient_id=?");
     ps.setString(1, patId);
     ResultSet rs=ps.executeQuery();
     rs.next();
     AppointmentDao.deletePatientrByid(patId);
     ps=conn.prepareStatement("Delete from patients where patient_id=?");
     ps.setString(1, patId);
     return ps.executeUpdate()==1;
}
   
}
