
package org.dlearn.helsinki.skeleton.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataBaseHelper {

    private static final Logger log = LogManager
            .getLogger(DataBaseHelper.class);

    public static <T> T query(Supplier<Connection> connection, String statement,
            FailableConsumer<PreparedStatement, SQLException> preparer,
            FailableFunction<Iterable<ResultSet>, T, SQLException> take)
            throws SQLException {
        try (final Connection c = connection.get()) {
            try (final PreparedStatement select = c
                    .prepareStatement(statement)) {
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

    public static boolean doesGroupClassMatch(final Connection dbConnection,
            int group_id, int class_id) throws SQLException {
        log.traceEntry(
                "Ensuring that groups {} class and supposed class {} match",
                group_id, class_id);
        try (final PreparedStatement insert = dbConnection.prepareStatement(
                "SELECT class_id FROM public.\"Groups\" WHERE _id=?")) {
            insert.setInt(1, group_id);
            try (final ResultSet result = insert.executeQuery()) {
                if (result.next()) {
                    if (class_id == result.getInt("class_id")) {
                        log.traceExit("Classes did match");
                        return true;
                    }
                } else {
                    log.traceExit("No such group exists");
                    return false;
                }
            }
        }

        log.traceExit("Classes didn't match");
        return true;
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
