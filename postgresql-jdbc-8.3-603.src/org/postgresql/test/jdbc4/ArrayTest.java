/*-------------------------------------------------------------------------
*
* Copyright (c) 2007-2008, PostgreSQL Global Development Group
*
* IDENTIFICATION
*   $PostgreSQL: pgjdbc/org/postgresql/test/jdbc4/ArrayTest.java,v 1.3 2008/01/08 06:56:31 jurka Exp $
*
*-------------------------------------------------------------------------
*/
package org.postgresql.test.jdbc4;

import java.sql.*;
import junit.framework.TestCase;
import org.postgresql.test.TestUtil;

public class ArrayTest extends TestCase {

    private Connection _conn;

    public ArrayTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        _conn = TestUtil.openDB();
    }

    protected void tearDown() throws SQLException {
        TestUtil.closeDB(_conn);
    }

    public void testCreateArrayOfInt() throws SQLException {
        PreparedStatement pstmt = _conn.prepareStatement("SELECT ?::int[]");
        Integer in[] = new Integer[3];
        in[0] = 0;
        in[1] = -1;
        in[2] = 2;
        pstmt.setArray(1, _conn.createArrayOf("int4", in));

        ResultSet rs = pstmt.executeQuery();
        assertTrue(rs.next());
        Array arr = rs.getArray(1);
        Integer out[] = (Integer [])arr.getArray();

        assertEquals(3, out.length);
        assertEquals(0, out[0].intValue());
        assertEquals(-1, out[1].intValue());
        assertEquals(2, out[2].intValue());
    }

    public void testCreateArrayOfMultiString() throws SQLException {
        PreparedStatement pstmt = _conn.prepareStatement("SELECT ?::text[]");
        String in[][] = new String[2][2];
        in[0][0] = "a";
        in[0][1] = "";
        in[1][0] = "\\";
        in[1][1] = "\"\\'z";
        pstmt.setArray(1, _conn.createArrayOf("text", in));

        ResultSet rs = pstmt.executeQuery();
        assertTrue(rs.next());
        Array arr = rs.getArray(1);
        String out[][] = (String [][])arr.getArray();

        assertEquals(2, out.length);
        assertEquals(2, out[0].length);
        assertEquals("a", out[0][0]);
        assertEquals("", out[0][1]);
        assertEquals("\\", out[1][0]);
        assertEquals("\"\\'z", out[1][1]);
    }

    public void testCreateArrayOfNull() throws SQLException {
        if (!TestUtil.haveMinimumServerVersion(_conn, "8.2"))
            return;

        PreparedStatement pstmt = _conn.prepareStatement("SELECT ?");
        String in[] = new String[2];
        in[0] = null;
        in[1] = null;
        pstmt.setArray(1, _conn.createArrayOf("int8", in));

        ResultSet rs = pstmt.executeQuery();
        assertTrue(rs.next());
        Array arr = rs.getArray(1);
        Long out[] = (Long [])arr.getArray();

        assertEquals(2, out.length);
        assertNull(out[0]);
        assertNull(out[1]);
    }

    public void testCreateEmptyArrayOfIntViaAlias() throws SQLException {
        PreparedStatement pstmt = _conn.prepareStatement("SELECT ?::int[]");
        Integer in[] = new Integer[0];
        pstmt.setArray(1, _conn.createArrayOf("integer", in));

        ResultSet rs = pstmt.executeQuery();
        assertTrue(rs.next());
        Array arr = rs.getArray(1);
        Integer out[] = (Integer [])arr.getArray();

        assertEquals(0, out.length);
    }

    public void testCreateArrayWithoutServer() throws SQLException {
        String in[][] = new String[2][2];
        in[0][0] = "a";
        in[0][1] = "";
        in[1][0] = "\\";
        in[1][1] = "\"\\'z";

        Array arr = _conn.createArrayOf("varchar", in);
        String out[][] = (String [][])arr.getArray();

        assertEquals(2, out.length);
        assertEquals(2, out[0].length);
        assertEquals("a", out[0][0]);
        assertEquals("", out[0][1]);
        assertEquals("\\", out[1][0]);
        assertEquals("\"\\'z", out[1][1]);
    }

    public void testCreatePrimitiveArray() throws SQLException {
        double in[][] = new double[2][2];
        in[0][0] = 3.5;
        in[0][1] = -4.5;
        in[1][0] = 10.0/3;
        in[1][1] = 77;

        Array arr = _conn.createArrayOf("float8", in);
        Double out[][] = (Double [][])arr.getArray();

        assertEquals(2, out.length);
        assertEquals(2, out[0].length);
        assertEquals(3.5, out[0][0], 0.00001);
        assertEquals(-4.5, out[0][1], 0.00001);
        assertEquals(10.0/3, out[1][0], 0.00001);
        assertEquals(77, out[1][1], 0.00001);
    }

}
