package com.rock7.challenge.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface SqlObject {
    List<PreparedStatement> toSqlInsertStatements(Connection connection) throws SQLException;
}
