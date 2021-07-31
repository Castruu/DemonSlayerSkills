package dslayer.draxy.database;

import dslayer.draxy.DSMain;

import java.io.File;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Connection {
    public static java.sql.Connection con = null;

    public static void openConnectionSQLite(){
        File file = new File(DSMain.getInstance().getDataFolder(), "database.db");

        String URL = "jdbc:sqlite:" + file;

        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(URL);
            createTable();
            System.out.print("A conexao SQLite foi iniciada!");
        } catch (Exception e) {
            System.out.println("Na tentativa de conectar com SQLite, um erro aconteceu!");
            DSMain.getInstance().getPluginLoader().disablePlugin(DSMain.getInstance());
            e.printStackTrace();
        }
    }

    public static void createTable(){
        PreparedStatement stm = null;

        try {
            stm = con.prepareStatement("CREATE TABLE IF NOT EXISTS level (uuid CHAR(16), nivel INT, skill1 INT, skill2 INT, skill3 INT, points INT, active INT)");
            System.out.println("Tabela level carregadas com sucesso!");
            stm.execute();
            stm.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Falha ao criar a tabela de níveis!");
            DSMain.getInstance().getPluginLoader().disablePlugin(DSMain.getInstance());
        }
    }

    public static void close(){
        if(con != null) {
            try {
                con.close();
                System.out.println("Connection closed with DATABASE");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erro ao fechar!");
            }
        }
    }
}
