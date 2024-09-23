<%-- 
    Document   : server_controller_grievance.jsp
    Created on : 7 Feb, 2024, 2:23:29 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Iterator"%>
<%@page import="Connection.dbconnection"%>
<%
  dbconnection db = new dbconnection();
   String key = request.getParameter("requestType").trim();
   System.out.print(key);
   

/////////////////////////////////////////////////////////////////STUDENT SECTION////////////////////////////////////////////////////////////////////////////////////////////

//STUDENT REGISTER

 if (key.equals("StudentRegister")) {
     String rn=request.getParameter("Rollno");
        String un=request.getParameter("name");
        String e = request.getParameter("department");
        String ph = request.getParameter("address");
        String pa = request.getParameter("email");
        String d = request.getParameter("passwd");
         String l=request.getParameter("phone");
        String a = request.getParameter("course");
        String h = request.getParameter("gender");
        String i = request.getParameter("image");
          String insertQry = "SELECT COUNT(*) FROM stud_reg WHERE `email`='"+pa+"' OR `phone`='"+l+"'";
        System.out.println(insertQry);
         Iterator it = db.getData(insertQry).iterator();
        System.out.println("heloooooooooooooooooo");
        if (it.hasNext()) {
            Vector vec = (Vector)it.next();
            int max_vid = Integer.parseInt(vec.get(0).toString());
            System.out.println(max_vid);
             if (max_vid == 0) {
                String sq = "INSERT into stud_reg(`rno`,`name`,`gender`,`depart`,`address`,`email`,`pass`,`phone`,`course`,`pic`)values('" + rn + "','"+un+"','"+h+"','" + e + "','" + ph + "','" + pa + "','" + d + "','" + l + "','" + a + "','" + i + "')";
                 String sq1 = "INSERT into login(u_id,`unam`,`pass`,`type`,`status`)values((select max(s_id) from stud_reg),'" + rn + "','" + d + "','student','1')";
                System.out.println(sq + "\n" + sq1);

                if (db.putData(sq) > 0 && db.putData(sq1) > 0) {

                      System.out.println("Registerd");
                    out.println("Registerd");

                } else {
                     System.out.println("Registration failed");
                    out.println("failed");
                }

            } else {
                System.out.println("Already Exist");
                out.println("Already Exist");
            }
        } else {
            out.println("failed");
        }

    }
 

 
 //STUDENT ADD GRIEVANCE

if (key.equals("AddGrievance")) {
        String id = request.getParameter("u_id");
        String da = request.getParameter("date");
         String ty = request.getParameter("type");
        String p = request.getParameter("proir");
        String se = request.getParameter("sento");
         String  d= request.getParameter("des");
         String i = request.getParameter("image");
         String sql="INSERT INTO `grievance`(`u_id`,`date`,`type`,`prior`,`sento`,`des`,`pic`,`status`)VALUES('"+id+"','"+da+"','"+ty+"','"+p+"','"+se+"','"+d+"','"+i+"','sent')";
        int res2 = db.putData(sql);
        if (res2 > 0) {
            out.print("success");
        } else {
            out.print("failed");

        }
    }



//STUDENT VIEW GRIEVANCES

if (key.equals("MyGrievances")) {
    String id = request.getParameter("u_id");
        String str3 = "SELECT * FROM `grievance` WHERE `u_id`='"+id+"'";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("g_id", v.get(0).toString());
                jsonobj.put("g_uid", v.get(1).toString());
                 jsonobj.put("g_date", v.get(2).toString());
                jsonobj.put("g_typ", v.get(3).toString());
                jsonobj.put("g_prio", v.get(4).toString());
                jsonobj.put("g_sento", v.get(5).toString());
                jsonobj.put("g_des", v.get(6).toString());
                jsonobj.put("g_pic", v.get(7).toString());
                jsonobj.put("g_status", v.get(8).toString());
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }

 

// STUDENT VIEW GRIEVANCE REPLIES

if (key.equals("StudGrievanceRply")) {
    String id = request.getParameter("id");
        String st = "SELECT * FROM `grievance_reply` WHERE `r_uid`='"+id+"'";
        Iterator it = db.getData(st).iterator();
        if (it.hasNext()) {
            Vector v = (Vector) it.next();
            String ress = v.get(0) + "#" + v.get(1)+"#"+ v.get(2)+"#"+ v.get(3)+"#"+ v.get(4)+"#"+ v.get(5)+"#"+ v.get(6)+"#"+ v.get(7)+"#";
            out.print(ress);
            System.out.println("ss"+ress);
        } else {
            out.print("failed");
             System.out.println("No"+st);
        }
    }



//STUDENT EDIT GRIEVANCE

 if (key.equals("EditGrievance")) {
     String gid = request.getParameter("g_id");
      String id = request.getParameter("u_id");
        String da = request.getParameter("date");
         String ty = request.getParameter("type");
        String p = request.getParameter("proir");
        String se = request.getParameter("sento");
         String  d= request.getParameter("des");
         String i = request.getParameter("image");
//        System.out.println("IMAG==="+i);
        String sq5 = "UPDATE `grievance` SET date='"+da+"',type='"+ty+"',prior='"+p+"',sento='"+se+"',des='"+d+"',pic='"+i+"',status='sent' WHERE u_id='"+id+"'AND g_id='"+gid+"'";
       System.out.println("EEE"+sq5);
        int res6 = db.putData(sq5);
        if (res6 > 0 ) {
            out.print("success");
        } else {
            out.print("failed");
        }
       
    }


 
 //STUDENT REQUEST APPOINTMENTS

if (key.equals("StudentREqAppointment")) {
        String id = request.getParameter("u_id");
        String da = request.getParameter("title");
         String ty = request.getParameter("type");
         String se = request.getParameter("sent");
         String  d= request.getParameter("des");
         String sql="INSERT INTO `appointments`(`u_id`,`title`,`des`,`type`,`sent`,`status`)VALUES('"+id+"','"+da+"','"+d+"','"+ty+"','"+se+"','sent')";
        int res2 = db.putData(sql);
        if (res2 > 0) {
            out.print("success");
        } else {
            out.print("failed");

        }
    }


//STUDENTS VIEW SCHEDULED APPOINTMENTS

if (key.equals("StudentViewAppointments")) {
     String id=request.getParameter("u_id");
        String str3 = "SELECT `appointments`.`title`,`appointments`.`des`,`scheduled_appointments`.* FROM `appointments`,`scheduled_appointments` WHERE `appointments`.`a_id`=`scheduled_appointments`.`aid` AND `scheduled_appointments`.`u_id`='"+id+"'";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("a_tit", v.get(0).toString());
                jsonobj.put("a_des", v.get(1).toString());
                 jsonobj.put("sc_id", v.get(2).toString());
                jsonobj.put("sc_fid", v.get(3).toString());
                jsonobj.put("sc_aid", v.get(4).toString());
                jsonobj.put("sc_uid", v.get(5).toString());
                jsonobj.put("sc_date", v.get(6).toString());
                jsonobj.put("sc_time", v.get(7).toString());
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }


// STUDENT PROFILE

if (key.equals("myProfile")) {
    String id = request.getParameter("id");
        String st = "SELECT * FROM `stud_reg` WHERE `s_id`='"+id+"'";
        Iterator it = db.getData(st).iterator();
        if (it.hasNext()) {
            Vector v = (Vector) it.next();
            String ress = v.get(0) + "#" + v.get(1)+"#"+ v.get(2)+"#"+ v.get(3)+"#"+ v.get(4)+"#"+ v.get(5)+"#"+ v.get(6)+"#"+ v.get(7)+"#"+ v.get(8)+"#"+ v.get(9)+"#"+ v.get(10)+"#";
            out.print(ress);
            System.out.println("ss"+ress);
        } else {
            out.print("failed");
             System.out.println("No"+st);
        }
    }



// EDIT PROFILE

 if (key.equals("edit_profile")) {
      String id=request.getParameter("u_id");
      String r=request.getParameter("Rollno");
        String na=request.getParameter("name");
        String em = request.getParameter("email");
        String pa = request.getParameter("passwd");
        String ad=request.getParameter("address");
        String de = request.getParameter("department");
        String co = request.getParameter("course");
        String ph = request.getParameter("phone");
        String ge=request.getParameter("gender");
        String i=request.getParameter("image");
//        System.out.println("IMAG==="+i);
        String sq5 = "UPDATE `stud_reg` SET rno='"+r+"',name='"+na+"',gender='"+ge+"',depart='"+de+"',address='"+ad+"',email='"+em+"',pass='"+pa+"',phone='"+ph+"',course='"+co+"',pic='"+i+"' WHERE s_id='"+id+"'";
        String st = "UPDATE `login` SET pass='"+pa+"',unam='"+r+"' WHERE u_id='"+id+"' AND type='student'";
       System.out.println("EEE"+sq5);
        int res6 = db.putData(sq5);
        int res7 = db.putData(st);
        if (res6 > 0 && res7 > 0) {
            out.print("success");
        } else {
            out.print("failed");
        }
       
    }
 
 

 
 //LOGIN
if (key.equals("login")) {
        String ln = request.getParameter("uname");
        String lp = request.getParameter("pass");
        String st = "SELECT * from login where unam='" + ln + "' and pass='" + lp + "' AND status='1'";
        System.out.println(st);
        
        Iterator it = db.getData(st).iterator();
        if (it.hasNext()) {
            Vector v = (Vector) it.next();
            String ress = v.get(1) + "#" + v.get(4)+"#";
            out.print(ress);
            System.out.println("ss"+ress);
        } else {
            out.print("failed");
             System.out.println("No"+st);
        }
    }



//////////////////////////////////////////////////////////////////////FACULTY SECTION/////////////////////////////////////////////////////////////////////////////////////////

//FACULTY REGISTER

 if (key.equals("FacultyRegister")) {
        String un=request.getParameter("name");
        String e = request.getParameter("department");
        String ph = request.getParameter("address");
        String pa = request.getParameter("email");
        String d = request.getParameter("passwd");
         String l=request.getParameter("phone");
        String a = request.getParameter("desgnation");
        String h = request.getParameter("gender");
        String i = request.getParameter("image");
          String insertQry = "SELECT COUNT(*) FROM faculty_reg WHERE `email`='"+pa+"' OR `phone`='"+l+"'";
        System.out.println(insertQry);
         Iterator it = db.getData(insertQry).iterator();
        System.out.println("heloooooooooooooooooo");
        if (it.hasNext()) {
            Vector vec = (Vector)it.next();
            int max_vid = Integer.parseInt(vec.get(0).toString());
            System.out.println(max_vid);
             if (max_vid == 0) {
                String sq = "INSERT into faculty_reg(`name`,`gender`,`depart`,`address`,`email`,`pass`,`phone`,`desig`,`pic`)values('"+un+"','"+h+"','" + e + "','" + ph + "','" + pa + "','" + d + "','" + l + "','" + a + "','" + i + "')";
                 String sq1 ="INSERT into login(u_id,`unam`,`pass`,`type`,`status`)values((select max(s_id) from stud_reg),'" + pa + "','" + d + "','faculty','0')";

                System.out.println(sq + "\n" + sq1);

                if (db.putData(sq) > 0 && db.putData(sq1) > 0) {

                      System.out.println("Registerd");
                    out.println("Registerd");

                } else {
                     System.out.println("Registration failed");
                    out.println("failed");
                }

            } else {
                System.out.println("Already Exist");
                out.println("Already Exist");
            }
        } else {
            out.println("failed");
        }

    }

 
 //FACULTY VIEW GRIEVANCES

if (key.equals("FacViewGrievances")) {
     String id=request.getParameter("u_id");
        String str3 = "SELECT `grievance`.*, `faculty_reg`.`f_id`,`faculty_reg`.`desig` FROM `grievance`,`faculty_reg` WHERE `faculty_reg`.`desig`=`grievance`.`sento` AND `faculty_reg`.`f_id`='"+id+"' AND `grievance`.`status`='sent'";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("g_id", v.get(0).toString());
                jsonobj.put("g_uid", v.get(1).toString());
                 jsonobj.put("g_date", v.get(2).toString());
                jsonobj.put("g_typ", v.get(3).toString());
                jsonobj.put("g_prio", v.get(4).toString());
                jsonobj.put("g_sento", v.get(5).toString());
                jsonobj.put("g_des", v.get(6).toString());
                jsonobj.put("g_pic", v.get(7).toString());
                jsonobj.put("g_status", v.get(8).toString());
                  jsonobj.put("f_id", v.get(9).toString());
                jsonobj.put("f_des", v.get(10).toString());
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }



 //FACULTY SENT REPLY

if (key.equals("Faculty_sent_reply")) {
        String id = request.getParameter("f_id");
        String da = request.getParameter("g_id");
         String ty = request.getParameter("u_id");
        String p = request.getParameter("opinion");
        String se = request.getParameter("des");
         String  d= request.getParameter("date");
         String sql="INSERT INTO `grievance_reply`(`r_gid`,`r_fid`,`r_uid`,`opinion`,`des`,`date`,`status`)VALUES('"+da+"','"+id+"','"+ty+"','"+p+"','"+se+"','"+d+"','initiated')";
        String sq2="UPDATE `grievance` SET `status`='replied' WHERE `g_id`='"+da+"'";
         int res2 = db.putData(sql);
         int res3 = db.putData(sq2);
        if (res2 > 0 && res3 > 0) {
            out.print("success");
        } else {
            out.print("failed");

        }
    }


 

//FACULTY VIEW APPOINTMENTS

if (key.equals("FacAppointments")) {
     String id=request.getParameter("u_id");
        String str3 = "SELECT `stud_reg`.`name`,`stud_reg`.`course`, `faculty_reg`.`name`,`appointments`.* FROM `stud_reg`,`appointments`,`faculty_reg` WHERE `appointments`.`u_id`=`stud_reg`.`s_id` AND `appointments`.`sent`=`faculty_reg`.`desig` AND `appointments`.`status`='sent' AND `faculty_reg`.`f_id`='"+id+"'";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("s_nam", v.get(0).toString());
                jsonobj.put("s_course", v.get(1).toString());
                 jsonobj.put("f_des", v.get(2).toString());
                jsonobj.put("a_id", v.get(3).toString());
                jsonobj.put("auid", v.get(4).toString());
                jsonobj.put("a_tit", v.get(5).toString());
                jsonobj.put("a_des", v.get(6).toString());
                jsonobj.put("a_typ", v.get(7).toString());
                jsonobj.put("a_sent", v.get(8).toString());
                  jsonobj.put("a_status", v.get(9).toString());
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }



//FACULTY SCHEDULE APPOINTMENTS

if (key.equals("ScheduledAppointment")) {
        String id = request.getParameter("f_id");
        String da = request.getParameter("a_id");
         String ty = request.getParameter("u_id");
        String p = request.getParameter("time");
         String  d= request.getParameter("date");
         String sql="INSERT INTO `scheduled_appointments`(`f_id`,`aid`,`u_id`,`date`,`time`)VALUES('"+id+"','"+da+"','"+ty+"','"+d+"','"+p+"')";
        String sq2="UPDATE `appointments` SET `status`='replied' WHERE `a_id`='"+da+"'";
         int res2 = db.putData(sql);
         int res3 = db.putData(sq2);
        if (res2 > 0 && res3 > 0) {
            out.print("success");
        } else {
            out.print("failed");

        }
    }


// FACULTY PROFILE

if (key.equals("FacultyProfile")) {
    String id = request.getParameter("id");
        String st = "SELECT * FROM `faculty_reg` WHERE `f_id`='"+id+"'";
        Iterator it = db.getData(st).iterator();
        if (it.hasNext()) {
            Vector v = (Vector) it.next();
            String ress = v.get(0) + "#" + v.get(1)+"#"+ v.get(2)+"#"+ v.get(3)+"#"+ v.get(4)+"#"+ v.get(5)+"#"+ v.get(6)+"#"+ v.get(7)+"#"+ v.get(8)+"#"+ v.get(9)+"#";
            out.print(ress);
            System.out.println("ss"+ress);
        } else {
            out.print("failed");
             System.out.println("No"+st);
        }
    }
 


//FACULTY EDIT PROFILE

 if (key.equals("Faculty_edit_profile")) {
      String id=request.getParameter("f_id");
      String un=request.getParameter("name");
        String e = request.getParameter("department");
        String ph = request.getParameter("address");
        String pa = request.getParameter("email");
        String d = request.getParameter("passwd");
         String l=request.getParameter("phone");
        String a = request.getParameter("desgnation");
        String h = request.getParameter("gender");
        String i = request.getParameter("image");
        String sq5 = "UPDATE `faculty_reg` SET name='"+un+"',gender='"+h+"',depart='"+e+"',address='"+ph+"',email='"+pa+"',pass='"+d+"',phone='"+l+"',desig='"+a+"',pic='"+i+"' WHERE f_id='"+id+"'";
        String st = "UPDATE `login` SET pass='"+d+"',unam='"+pa+"' WHERE u_id='"+id+"' AND type='faculty'";
       System.out.println("EEE"+sq5);
        int res6 = db.putData(sq5);
        int res7 = db.putData(st);
        if (res6 > 0 && res7 > 0) {
            out.print("success");
        } else {
            out.print("failed");
        }
       
    }


/////////////////////////////////////////////////////////////////////////ADMIN SECTION/////////////////////////////////////////////////////////////////////////////////


//ADMIN VIEW FACULTY

if (key.equals("ViewFaculty")) {
        String str3 = "SELECT * FROM `faculty_reg`";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("f_id", v.get(0).toString());
                jsonobj.put("f_nam", v.get(1).toString());
                 jsonobj.put("f_gen", v.get(2).toString());
                jsonobj.put("f_dep", v.get(3).toString());
                jsonobj.put("f_add", v.get(4).toString());
                jsonobj.put("f_email", v.get(5).toString());
                jsonobj.put("f_pass", v.get(6).toString());
                jsonobj.put("f_phone", v.get(7).toString());
                jsonobj.put("f_des", v.get(8).toString());
                jsonobj.put("f_pic", v.get(9).toString());
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }


//ADMIN ACCEPT FACULTY

if (key.equals("AcceptFaculty")) {
        String  id=request.getParameter("id");
        String sqll = "UPDATE login SET status='1' WHERE u_id='"+id+"' AND type='faculty'";
                System.out.println(sqll);

        int resu = db.putData(sqll);
        if (resu > 0) {
            out.print("success");
        } else {
            out.print("failed");
        }
    }



//ADMIN VIEW STUDENTS

if (key.equals("ViewStudents")) {
        String str3 = "SELECT * FROM `stud_reg`";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("s_id", v.get(0).toString());
                jsonobj.put("rno", v.get(1).toString());
                 jsonobj.put("s_nam", v.get(2).toString());
                jsonobj.put("s_gender", v.get(3).toString());
                jsonobj.put("s_dep", v.get(4).toString());
                jsonobj.put("s_add", v.get(5).toString());
                jsonobj.put("s_email", v.get(6).toString());
                jsonobj.put("s_pass", v.get(7).toString());
                jsonobj.put("s_phone", v.get(8).toString());
                jsonobj.put("s_course", v.get(9).toString());
                jsonobj.put("s_pic", v.get(10).toString());
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }


//ADMIN VIEW GRIEVANCES

if (key.equals("AdminVIewGrievance")) {
        String str3 = "SELECT * FROM `grievance`";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("g_id", v.get(0).toString());
                jsonobj.put("g_uid", v.get(1).toString());
                 jsonobj.put("g_date", v.get(2).toString());
                jsonobj.put("g_typ", v.get(3).toString());
                jsonobj.put("g_prio", v.get(4).toString());
                jsonobj.put("g_sento", v.get(5).toString());
                jsonobj.put("g_des", v.get(6).toString());
                jsonobj.put("g_pic", v.get(7).toString());
                jsonobj.put("g_status", v.get(8).toString());
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }



//ADMIN VIEW GRIEVANCES REPLIES

if (key.equals("AdminViewActions")) {
        String str3 = "SELECT `grievance`.`type`, `grievance`.`des`, `grievance`.`pic`,`grievance_reply`.* FROM `grievance`, `grievance_reply` WHERE `grievance`.`g_id`=`grievance_reply`.`r_gid`";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("g_typ", v.get(0).toString());
                jsonobj.put("g_des", v.get(1).toString());
                  jsonobj.put("g_pic", v.get(2).toString());
                 jsonobj.put("re_id", v.get(3).toString());
                jsonobj.put("re_gid", v.get(4).toString());
                jsonobj.put("re_fid", v.get(5).toString());
                jsonobj.put("re_uid", v.get(6).toString());
                jsonobj.put("r_opinion", v.get(7).toString());
                jsonobj.put("r_des", v.get(8).toString());
                jsonobj.put("r_date", v.get(9).toString());
                jsonobj.put("r_status", v.get(10).toString());
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }



//ADMIN UPDATE STATUS

if (key.equals("AdminUpdateStatus")) {
        String  rid=request.getParameter("rid");
        String  uid=request.getParameter("uid");
        String  sta=request.getParameter("status");
        String sqll = "UPDATE grievance_reply SET status='"+sta+"' WHERE re_id='"+rid+"' AND r_uid='"+uid+"'";
                System.out.println(sqll);
        int resu = db.putData(sqll);
        if (resu > 0) {
            out.print("success");
        } else {
            out.print("failed");
        }
    }

%>

