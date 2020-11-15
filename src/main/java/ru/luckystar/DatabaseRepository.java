package ru.luckystar;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseRepository {

    public Connection connection = null;
    public String databaseType;
    public String defaultLanguage;

    public DatabaseRepository(String absolutePath, String databaseType, String url, String login, String password, String defaultlanguage) {
        this.databaseType = databaseType.toLowerCase();
        this.defaultLanguage = defaultlanguage;
        try {
            if (this.databaseType.equals("mysql")) {
                /* MySQL init */
                HikariDataSource ds = new HikariDataSource();
                ds.setJdbcUrl("jdbc:mysql://" + url + "?useSSL=false");
                ds.setUsername(login);
                ds.setPassword(password);
                connection = ds.getConnection();
            } else if (this.databaseType.equals("sqlite")) {
                /* SQLite init */
                Class.forName("org.sqlite.JDBC").newInstance();
                connection = DriverManager.getConnection("jdbc:sqlite://" + absolutePath + "/players.db");

            }
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS `players` (`uuid` varchar(255) NOT NULL UNIQUE,`language` varchar(255))");
        } catch (Exception e) {
            Logger.getLogger("minecraft").log(Level.WARNING, "§4[Languages] §cFailed Connect To DataBase");
            e.printStackTrace();
        }
    }

    public void savePlayer(UUID uuid) {
        try {
            if (this.databaseType.equals("sqlite")) {
                this.connection.createStatement().execute("INSERT OR IGNORE INTO players (uuid, language) VALUES (\'" + uuid + "\', \'" + defaultLanguage + "\')");
            } else {
                this.connection.createStatement().execute("INSERT IGNORE INTO players (uuid, language) VALUES (\'" + uuid + "\', \'" + defaultLanguage + "\')");
            }
        } catch (SQLException e) {
            Logger.getLogger("minecraft").log(Level.WARNING,"§4[Languages] §cFailed Save Player To DataBase");
            e.printStackTrace();
        }
    }

    public void updatePlayer(UUID uuid, String lang) {
        try {
            this.connection.createStatement().execute("UPDATE players SET language=\'" + lang + "\' WHERE uuid=\'" + uuid + "\'");
        } catch (SQLException throwables) {
            Logger.getLogger("minecraft").log(Level.WARNING,"§4[Languages] §cFailed Update Player In DataBase");
            throwables.printStackTrace();
        }
    }

    public String getPlayerLang(UUID uuid) {
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery("SELECT language FROM players WHERE uuid=\'" + uuid + "\'");
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException throwables) {
            Logger.getLogger("minecraft").log(Level.WARNING,"§4[Languages] §cFailed Get Player Language In DataBase");
            throwables.printStackTrace();
        }
        return "ru";
    }
}
