package com.github.enjektor.web.playground.repository;

import com.github.enjektor.core.annotations.Dependency;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.web.playground.utils.Base64Utils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Dependency
public class BasicAuthenticationRepository implements AuthenticationRepository {

    /**
     * create table auth (
     * auth_id serial PRIMARY KEY,
     * auth_username varchar(40) UNIQUE,
     * auth_pwd varchar(255),
     * auth_created_at timestamptz DEFAULT NOW()
     * <p>
     * )
     */

    @Inject
    private DataSource dataSource;

    @Inject
    private Base64Utils base64Utils;

    @Override
    public boolean isAuthenticated(String username, String password) {

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement("select auth_pwd from auth where auth_username = ?", ResultSet.TYPE_FORWARD_ONLY)) {

                preparedStatement.setString(1, username);

                final ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    final String pass = resultSet.getString(1);

                    resultSet.close();
                    System.out.println("AUTHENTICATION_DONE");
                    return password.equals(pass);
                }

            } catch (SQLException e) {
                connection.rollback();
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;
    }
}
