package com.gesoftware.venta.db;

import com.gesoftware.venta.logging.LoggingUtility;
import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCP;

import java.io.InputStream;
import java.util.AbstractList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;

/**
 * DB connection class definition
 **/
public final class DBConnection {
    /* Connections pool */
    private BoneCP m_Pool;

    /**
     * DB Statement class definition
     **/
    public final class DBStatement {
        private final PreparedStatement m_Statement;
        private final Connection m_Connection;

        /* *
         * METHOD: Class constructor
         *  PARAM: [IN] connection - current connection
         *  PARAM: [IN] statement  - statement, created from connection
         * AUTHOR: Dmitry Eliseev
         * */
        private DBStatement(final Connection connection, final PreparedStatement statement) {
            m_Connection = connection;
            m_Statement  = statement;
        } /* End of 'DBStatement::DBStatement' class */

        /* *
         * METHOD: Integer parameter setter
         * RETURN: True if success, False otherwise
         *  PARAM: [IN] index - parameter position
         *  PARAM: [IN] value - parameter value
         * AUTHOR: Dmitry Eliseev
         * */
        public final boolean setInteger(final int index, final int value) {
            try {
                m_Statement.setInt(index, value);
                return true;
            } catch (final SQLException e) {
                LoggingUtility.debug("Can't set integer value: " + value + " because of " + e.getMessage());
            }

            return false;
        } /* End of 'DBStatement::setInteger' class */

        /* *
         * METHOD: Long parameter setter
         * RETURN: True if success, False otherwise
         *  PARAM: [IN] index - parameter position
         *  PARAM: [IN] value - parameter value
         * AUTHOR: Dmitry Eliseev
         * */
        public final boolean setLong(final int index, final long value) {
            try {
                m_Statement.setLong(index, value);
                return true;
            } catch (final SQLException e) {
                LoggingUtility.debug("Can't set long value: " + value + " because of " + e.getMessage());
            }

            return false;
        } /* End of 'DBStatement::setLong' class */

        /* *
         * METHOD: String parameter setter
         * RETURN: True if success, False otherwise
         *  PARAM: [IN] index - parameter position
         *  PARAM: [IN] value - parameter value
         * AUTHOR: Dmitry Eliseev
         * */
        public final boolean setString(final int index, final String value) {
            try {
                m_Statement.setString(index, value);
            } catch (final SQLException e) {
                LoggingUtility.debug("Can't set string value: " + value + " because of " + e.getMessage());
            }

            return false;
        } /* End of 'DBStatement::setString' class */

        /* *
         * METHOD: Enum parameter setter
         * RETURN: True if success, False otherwise
         *  PARAM: [IN] index - parameter position
         *  PARAM: [IN] value - parameter value
         * AUTHOR: Dmitry Eliseev
         * */
        public final boolean setEnum(final int index, final Enum value) {
            return setString(index, value.name());
        } /* End of 'DBStatement::setEnum' method */

        /* *
         * METHOD: Binary stream parameter setter
         * RETURN: True if success, False otherwise
         *  PARAM: [IN] index  - parameter position
         *  PARAM: [IN] stream - stream
         *  PARAM: [IN] long   - data length
         * AUTHOR: Dmitry Eliseev
         * */
        public final boolean setBinaryStream(final int index, final InputStream stream, final long length) {
            try {
                m_Statement.setBinaryStream(index, stream);
                return true;
            } catch (final SQLException e) {
                LoggingUtility.debug("Can't set stream value: " + stream + " because of " + e.getMessage());
            }

            return false;
        } /* End of 'DBStatement::setBinaryStream' method */
    } /* End of 'DBConnection::DBStatement' class */

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] host - Database service host
     *  PARAM: [IN] port - Database service port
     *  PARAM: [IN] name - Database name
     *  PARAM: [IN] user - Database user's name
     *  PARAM: [IN] pass - Database user's password
     * AUTHOR: Dmitry Eliseev
     * */
    public DBConnection(final String host, final int port, final String name, final String user, final String pass) {
        final BoneCPConfig config = new BoneCPConfig();
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + name);
        config.setUsername(user);
        config.setPassword(pass);

        /* Pool size configuration */
        config.setMaxConnectionsPerPartition(5);
        config.setMinConnectionsPerPartition(5);
        config.setPartitionCount(1);

        try {
            m_Pool = new BoneCP(config);
        } catch (final SQLException e) {
            LoggingUtility.error("Can't initialize connections pool: " + e.getMessage());
            m_Pool = null;
        }
    } /* End of 'DBConnection::DBConnection' method */

    @Override
    protected final void finalize() throws Throwable {
        super.finalize();

        if (m_Pool != null)
            m_Pool.shutdown();
    } /* End of 'DBConnection::finalize' method  */

    /* *
     * METHOD: Prepares statement using current connection
     * RETURN: Prepared statement
     *  PARAM: [IN] query - SQL query
     * AUTHOR: Dmitry Eliseev
     * */
    public final DBStatement createStatement(final String query) {
        try {
            LoggingUtility.debug("Total: " + m_Pool.getTotalCreatedConnections() + "; Free: " + m_Pool.getTotalFree() + "; Leased: " + m_Pool.getTotalLeased());

            final Connection connection = m_Pool.getConnection();
            return new DBStatement(connection, connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS));
        } catch (final SQLException e) {
            LoggingUtility.error("Can't create prepared statement using query: " + e.getMessage());
        } catch (final Exception e) {
            LoggingUtility.error("Connection wasn't established: " + e.getMessage());
        }

        return null;
    } /* End of 'DBConnection::createStatement' method */

    /* *
     * METHOD: Closes prepared statement
     *  PARAM: [IN] sql - prepared statement
     * AUTHOR: Dmitry Eliseev
     * */
    private void closeStatement(final DBStatement query) {
        if (query == null)
            return;

        try {
            if (query.m_Statement != null)
                query.m_Statement.close();

            if (query.m_Connection != null)
                query.m_Connection.close();
        } catch (final SQLException ignored) {}
    } /* End of 'DBConnection::closeStatement' method */

    /* *
     * METHOD: Executes prepared statement like INSERT query
     * RETURN: Inserted item identifier if success, 0 otherwise
     *  PARAM: [IN] sql - prepared statement
     * AUTHOR: Dmitry Eliseev
     * */
    public final long insert(final DBStatement query) {
        try {
            /* Query execution */
            query.m_Statement.execute();

            /* Obtain last insert ID */
            final ResultSet resultSet = query.m_Statement.getGeneratedKeys();
            if (resultSet.next())
                return resultSet.getInt(1);
        } catch (final SQLException e) {
            LoggingUtility.error("Can't execute insert query: " + query.toString());
        } finally {
            closeStatement(query);
        }

        /* Insertion failed */
        return 0;
    } /* End of 'DBConnection::insert' method */

    /* *
     * METHOD: Executes prepared statement like UPDATE query
     * RETURN: True if success, False otherwise
     *  PARAM: [IN] sql - prepared statement
     * AUTHOR: Dmitry Eliseev
     * */
    public final boolean update(final DBStatement query) {
        try {
            query.m_Statement.execute();
            return true;
        } catch (final SQLException e) {
            LoggingUtility.error("Can't execute update query: " + query.m_Statement.toString());
        } finally {
            closeStatement(query);
        }

        /* Update failed */
        return false;
    } /* End of 'DBConnection::update' method */

    /* *
     * METHOD: Executes prepared statement like COUNT != 0 query
     * RETURN: True if exists, False otherwise
     *  PARAM: [IN] sql - prepared statement
     * AUTHOR: Dmitry Eliseev
     * */
    public final boolean exists(final DBStatement query) {
        final AbstractList<Map<String, Object>> results = select(query);
        return results != null && results.size() != 0;
    } /* End of 'DBConnection::DBConnection' method */

    /* *
     * METHOD: Executes prepared statement like SELECT query
     * RETURN: List of records (maps) if success, null otherwise
     *  PARAM: [IN] sql - prepared statement
     * AUTHOR: Dmitry Eliseev
     * */
    public final AbstractList<Map<String, Object>> select(final DBStatement query) {
        try {
            /* Container for result set */
            final AbstractList<Map<String, Object>> results = new LinkedList<Map<String, Object>>();

            /* Query execution */
            query.m_Statement.execute();

            /* Determine columns meta data */
            final ResultSetMetaData metaData = query.m_Statement.getMetaData();

            /* Obtain real data */
            final ResultSet resultSet = query.m_Statement.getResultSet();
            while (resultSet.next()) {
                final Map<String, Object> row = new HashMap<String, Object>();

                /* Copying fetched data */
                for (int columnID = 1; columnID <= metaData.getColumnCount(); columnID++)
                    row.put(metaData.getColumnName(columnID), resultSet.getObject(columnID));

                /* Add row to results */
                results.add(row);
            }

            /* That's it */
            return results;
        } catch (final SQLException e) {
            LoggingUtility.error("Can't execute select query: " + query.toString());
        } finally {
            closeStatement(query);
        }

        /* Return empty result */
        return null;
    } /* End of 'DBConnection::select' method */
} /* End of 'DBConnection' class */
