package ru.javaops.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlExecute<T> {
    T execute(PreparedStatement ps) throws SQLException;
}