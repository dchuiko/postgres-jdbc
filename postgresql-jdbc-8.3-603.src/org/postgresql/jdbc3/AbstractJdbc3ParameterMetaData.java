/*-------------------------------------------------------------------------
*
* Copyright (c) 2005-2008, PostgreSQL Global Development Group
*
* IDENTIFICATION
*   $PostgreSQL: pgjdbc/org/postgresql/jdbc3/AbstractJdbc3ParameterMetaData.java,v 1.2 2008/01/08 06:56:29 jurka Exp $
*
*-------------------------------------------------------------------------
*/
package org.postgresql.jdbc3;

import java.sql.SQLException;
import java.sql.ParameterMetaData;

import org.postgresql.util.GT;
import org.postgresql.util.PSQLState;
import org.postgresql.util.PSQLException;
import org.postgresql.core.BaseConnection;
import org.postgresql.jdbc2.TypeInfoCache;

public abstract class AbstractJdbc3ParameterMetaData {

    private final BaseConnection _connection;
    private final int _oids[];

    public AbstractJdbc3ParameterMetaData(BaseConnection connection, int oids[]) {
        _connection = connection;
        _oids = oids;
    }

    public String getParameterClassName(int param) throws SQLException {
        checkParamIndex(param);
        return _connection.getJavaClass(_oids[param-1]);
    }

    public int getParameterCount() {
        return _oids.length;
    }

    // For now report all parameters as inputs.  CallableStatements may
    // have one output, but ignore that for now.
    public int getParameterMode(int param) throws SQLException {
        checkParamIndex(param);
        return ParameterMetaData.parameterModeIn;
    }

    public int getParameterType(int param) throws SQLException {
        checkParamIndex(param);
        return _connection.getSQLType(_oids[param-1]);
    }

    public String getParameterTypeName(int param) throws SQLException {
        checkParamIndex(param);
        return _connection.getPGType(_oids[param-1]);
    }

    // we don't know this
    public int getPrecision(int param) throws SQLException {
        checkParamIndex(param);
        return 0;
    }

    // we don't know this
    public int getScale(int param) throws SQLException {
        checkParamIndex(param);
        return 0;
    }

    // we can't tell anything about nullability
    public int isNullable(int param) throws SQLException {
        checkParamIndex(param);
        return ParameterMetaData.parameterNullableUnknown;
    }

    // pg doesn't have unsigned numbers
    public boolean isSigned(int param) throws SQLException {
        checkParamIndex(param);
        return TypeInfoCache.isSigned(_oids[param-1]);
    }

    private void checkParamIndex(int param) throws PSQLException {
        if (param < 1 || param > _oids.length)
            throw new PSQLException(GT.tr("The parameter index is out of range: {0}, number of parameters: {1}.", new Object[]{new Integer(param), new Integer(_oids.length)}), PSQLState.INVALID_PARAMETER_VALUE);
    }

}
