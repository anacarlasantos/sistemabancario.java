package conexao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexao {



    private static final String USUARIO = "root";
    private static final String SENHA = "1234";
    private static final String URL = "jdbc:mysql://localhost:3306/dbtabajara";



    public static Connection conn;

    /**
     * @return
     */
    public static Connection getConnection(){
        try {
        if(conn==null){
                conn=DriverManager.getConnection(URL, USUARIO, SENHA);
            return conn;

            }else{
                return conn;
            }

            } catch (SQLException e) {
    
                e.printStackTrace();
                return null;
            }
        }

    public static Connection abrir() {
        return null;
    }

    public static void fechar(Connection conn) {
    }
    }
    
         
    