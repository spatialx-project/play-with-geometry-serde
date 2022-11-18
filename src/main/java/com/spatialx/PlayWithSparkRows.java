package com.spatialx;

import org.apache.spark.sql.catalyst.expressions.UnsafeRow;

public class PlayWithSparkRows {
    public static void main(String[] args) {
        byte[] buffer = new byte[64];
        UnsafeRow row = new UnsafeRow(4);
        row.pointTo(buffer, buffer.length);
        row.setInt(0, 100);
        row.setDouble(1, 3.14);
        row.setLong(2, 10000);
        Main.printByteArray(row.getBytes());
    }
}
