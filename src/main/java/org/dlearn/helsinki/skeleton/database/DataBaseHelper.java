
package org.dlearn.helsinki.skeleton.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.function.Supplier;


public class DataBaseHelper {

    public static <T> T query(Supplier<Connection> connection, String statement, FailableConsumer<PreparedStatement, SQLException> preparer, FailableFunction<Iterable<ResultSet>, T, SQLException> take) throws SQLException {
        try (final Connection c = connection.get()) {
            try (final PreparedStatement select = c.prepareStatement(statement)) {
                preparer.accept(select);
                try (final ResultSet result = select.executeQuery()) {
                    return take.apply(() -> new Iterator<ResultSet>() {
                        @Override
                        public boolean hasNext() {
                            try {
                                return result.next();
                            } catch (SQLException ex) {
                                throw new WrapperException(ex);
                            }
                        }

                        @Override
                        public ResultSet next() {
                            return result;
                        }
                    });
                } catch (WrapperException ex) {
                    throw ex.exception;
                }
            }
        }
    }
    
    public interface FailableFunction<I, O, E extends Throwable> {

        O apply(I i) throws E;
    }

    public interface FailableConsumer<I, E extends Throwable> {

        void accept(I i) throws E;
    }

    private static class WrapperException extends RuntimeException {
        private final SQLException exception;

        private WrapperException(SQLException ex) {
            exception = ex;
        }

    }
    
}
