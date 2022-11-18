package com.spatialx;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import org.apache.sedona.core.geometryObjects.GeometrySerde;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.ByteOrderValues;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBReader;
import org.locationtech.jts.io.WKBWriter;
import org.locationtech.jts.io.WKTReader;

public class Main {
    private static final GeometryFactory factory = new GeometryFactory();
    private static final Kryo kryo = new Kryo();
    private static final WKTReader wktReader = new WKTReader();
    private static final WKBWriter wkbWriter = new WKBWriter(2, ByteOrderValues.LITTLE_ENDIAN);
    private static final WKBReader wkbReader = new WKBReader(factory);

    public static void main(String[] args) throws ParseException {
        Geometry polygon = wktReader.read("POLYGON((0 0, 1 0, 1 1, 0 1, 0 0))");
        GeometrySerde serde = new GeometrySerde();
        Output out = new Output(1000);
        serde.write(kryo, out, polygon);
        printByteArray(out.toBytes());
        printByteArray(wkbWriter.write(polygon));
    }

    /**
     * Print byte array as a sequence of hexadecimal strings
     * @param bytes
     */
    public static void printByteArray(byte[] bytes) {
        for (byte b : bytes) {
            System.out.print(String.format("%02X ", b));
        }
        System.out.println();
    }
}
