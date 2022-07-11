package io.github.dbcar.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBancoDeDados {

//    public static String status = "Não conectou...";
//
    public ConexaoBancoDeDados() {
    }
//
//    public static Connection getConnection() {

//        Connection connection = null;
//
//        try {
//            String driverName = "com.mysql.cj.jdbc.Driver";
//
//            Class.forName(driverName);
//
//            String serverName = "localhost"; // caminho do servidor do BD
//
//            String mydatabase = "dbcar"; // nome do seu banco de dados
//
////            String url = "jdbc:oracle:thin:@" + SERVER + ":" + PORT + ":" + DATABASE;
//            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
////
//            String username = "root"; // nome de um usuário de seu BD
////
//            String password = ""; // sua senha de acesso
////
//            connection = DriverManager.getConnection(url, username, password);
//
//            if (connection != null) {
//                status = ("STATUS--->Conectado com sucesso!");
//            } else {
//                status = ("STATUS--->Não foi possivel realizar conexão");
//            }
//
//            return connection;
//
//        } catch (ClassNotFoundException e) {
//
//            System.out.println("O driver expecificado nao foi encontrado.");
//            return null;
//
//        } catch (SQLException e) {
//
//            System.out.println("Nao foi possivel conectar ao Banco de Dados.");
//            return null;
//
//        }
//    }
//
//    public static String getStatus() {
//        return status;
//    }
//
//    public static boolean closeConnection() {
//        try {
//
//            ConexaoBancoDeDados.getConnection().close();
//            return true;
//
//        } catch (SQLException e) {
//            return false;
//        }
//    }
//
//    public static Connection rebootConnection() {
//
//        closeConnection();
//        return ConexaoBancoDeDados.getConnection();
//    }
        private static final String SERVER = "localhost";
        private static final String PORT = "1521"; // Porta TCP padrão do Oracle
        private static final String DATABASE = "xe";

        // Configuração dos parâmetros de autenticação
        private static final String USER = "system";
        private static final String PASS = "oracle";

        public static Connection getConnection() throws SQLException {
            String url = "jdbc:oracle:thin:@" + SERVER + ":" + PORT + ":" + DATABASE;

            // Abre-se a conexão com o Banco de Dados
            Connection con = DriverManager.getConnection(url, USER, PASS);

            // sempre usar o schema vem_ser
            con.createStatement().execute("alter session set current_schema=VEM_SER");

            return con;
        }

        public static boolean closeConnection() {
            try {

                ConexaoBancoDeDados.getConnection().close();
                return true;

            } catch (SQLException e) {
                return false;
            }
        }

}
