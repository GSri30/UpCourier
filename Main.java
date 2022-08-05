import java.util.*;
import java.sql.*;

public class IMT2019030{
    
    static final String DATABASE = "courier_management_IMT2019030";
    static final String DB_PACKAGES = "packages";
    static final String DB_STUDENTS = "students";
    static final String DB_SOURCE_DETAILS = "source_details";

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = String.format("jdbc:mysql://localhost/%s?useSSL=false",DATABASE);
    static final String USER = "root";
    static final String PASS = "Harsha@123";

    static Connection conn = null;
    static Statement stmt = null;
    static String sql = "";

    public static void main(String[] args){
    
        System.out.println("\nWELCOME TO COURIER MANAGEMENT SYSTEM DATABASE!\n\n");

        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        while(true){
            int n = menu();
            int terminate = process_action(n);
            if(terminate == 1) break;
        }
    }

    public static int menu(){
        System.out.println("\nMain Menu:");
        System.out.println("----------");
        System.out.println("0.Connect to the database");
        System.out.println("1.Insert a new package.");
        System.out.println("2.Insert a new student.");
        System.out.println("3.Insert a new source.");
        System.out.println("4.Modify a package.");
        System.out.println("5.Delete a package.");
        System.out.println("6.Search a package using package ID.");
        System.out.println("7.Exit the Database.");
        System.out.println("8.Quit the program.");

        System.out.println("\nChoose a number to perform the corresponding action : ");

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        return n;
    }

    public static int process_action(int n){
        Scanner sc = new Scanner(System.in);
        String dbname = null;
        int status;

        int package_id;
        String package_name,student_id, student_name, student_email, student_phone_number, source_id, source_name, source_contact;

        switch(n){
            case 0:
                System.out.println("\nYou have chosen to connect to the database.\n");
                try{
                    Class.forName(JDBC_DRIVER);
                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    stmt = conn.createStatement();
                    showresult(0);
                }
                catch(Exception e){
                    e.printStackTrace();
                    showresult(1);
                }
                break;
            case 1:
                System.out.println("\nYou have chosen to insert a new package.\n\nEnter the package ID : ");
                package_id = Integer.parseInt(sc.nextLine());
                System.out.println("\nEnter the student ID : ");
                student_id = sc.nextLine();
                System.out.println("\nEnter the source ID : ");
                source_id = sc.nextLine();
                package_name = "Name-of-" + package_id;
                
                status = insert_package( package_id, package_name, student_id, source_id );
                showresult(status);

                break;
            case 2:
                System.out.println("\nYou have chosen to insert a new student.\n\nEnter the roll number : ");
                student_id = sc.nextLine();
                student_name = "Name-of-" + student_id;
                student_email = "Email-of-" + student_id;
                student_phone_number = "Phone-of-" + student_id;

                status = insert_student( student_id, student_name, student_email, student_phone_number );
                showresult(status);

                break;
            case 3:
                System.out.println("\nYou have chosen to insert a new source.\n\nEnter the source ID : ");
                source_id = sc.nextLine();
                source_name = "Name-of-" + source_id;
                source_contact = "Contact-of-" + source_id;

                status = insert_source( source_id, source_name, source_contact );
                showresult(status);

                break;
            case 4:
                System.out.println("\nYou have chosen to modify a package.\n\nEnter the package ID : ");

                package_id = sc.nextInt();
                package_name = "Updated-Name-of-" + package_id;

                status = update( package_id, package_name );
                showresult(status);

                break;
            case 5:
                System.out.println("\nYou have chosen to delete a package.\n\nEnter the package ID : ");
            
                package_id = sc.nextInt();

                status = delete( package_id );
                showresult(status);

                break;
            case 6:
                System.out.println("\nYou have chosen to search a package using package ID.\n\nEnter the package ID : ");
                
                package_id = sc.nextInt();

                status = search( package_id );
                showresult(status);

                break;
            case 7:
                System.out.println("\nYou have chosen to exit the Database.\n");
                
                status = exit();
                showresult(status);

                break;
            case 8:
                System.out.println("\nProgram is terminated as you have chosen to quit.\n");
                return 1;
            default:
                System.out.println("\nEnter a valid action number.\n"); 
        }
        return 0;
    }

    public static int insert_package(int package_id, String package_name, String student_id, String source_id){
        String sql = String.format("INSERT INTO %s VALUES(%d,'%s','%s','%s');",DB_PACKAGES,package_id,package_name,student_id,source_id);
        try{
            int rs = stmt.executeUpdate(sql);
        }
        catch(Exception e){
            System.out.println(e);
            return 1;
        }
        return 0;
    }

    public static int insert_student(String student_id, String student_name, String student_email, String student_phone_number){
        String sql = String.format("INSERT INTO %s VALUES('%s','%s','%s','%s');",DB_STUDENTS,student_id,student_name, student_email, student_phone_number);
        try{
            int rs = stmt.executeUpdate(sql);
        }
        catch(Exception e){
            System.out.println(e);
            return 1;
        }
        return 0;
    }

    public static int insert_source(String source_id, String source_name, String source_contact){
        String sql = String.format("INSERT INTO %s VALUES('%s','%s','%s')",DB_SOURCE_DETAILS,source_id,source_name,source_contact);
        try{
            int rs = stmt.executeUpdate(sql);
        }
        catch(Exception e){
            System.out.println(e);
            return 1;
        }
        return 0;
    }

    public static int update(int package_id, String new_name){
        String sql = String.format("UPDATE %s SET package_name = '%s' WHERE package_id = %d",DB_PACKAGES,new_name,package_id);
        try{
            int rs = stmt.executeUpdate(sql);
        }
        catch(Exception e){
            System.out.println(e);
            return 1;
        }
        return 0;
    }
    
    public static int delete(int package_id){
        String sql = String.format("DELETE FROM %s WHERE package_id = %d",DB_PACKAGES,package_id);
        try{
            int rs = stmt.executeUpdate(sql);
        }
        catch(Exception e){
            System.out.println(e);
            return 1;
        }
        return 0;
    }
    
    public static int search(int package_id){
        String sql = String.format("SELECT * from %s WHERE package_id = %d",DB_PACKAGES,package_id);
        try{
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("");
            while(rs.next()){
                System.out.println("Package ID : " + rs.getInt("package_id"));
                System.out.println("Package Name : " + rs.getString("package_name"));
                System.out.println("Student ID : " + rs.getInt("student_id"));
                System.out.println("Source ID : " + rs.getInt("source_id"));
                System.out.println("");
            }
            System.out.println("No more results to fetch!");
            rs.close();
        }
        catch(Exception e){
            System.out.println(e);
            return 1;
        }
        return 0;
    }

    public static int exit(){
        try{
            stmt.close();
            conn.close();
        }
        catch(Exception e){
            System.out.println(e);
            return 1;
        }
        return 0;
    }

    public static void showresult(int status){
        if(status == 0){
            System.out.println("\nSuccess\n");
            return;
        }
        System.out.println("\nFailed. Status Code : \n"+status);
    }
}