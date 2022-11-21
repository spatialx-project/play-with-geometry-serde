package com.spatialx;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

public class Main {
  private static final WKTReader wktReader = new WKTReader();

  public static void main(String[] args) throws ParseException {
    Geometry polygon = wktReader.read("POLYGON((0 0, 1 0, 1 1, 0 1, 0 0))");
    byte[] shapeBuf = ShapeSerdeOptimized.serialize(polygon);
    printByteArray(shapeBuf);
    byte[] wkbBuf = WkbSerde.serialize(polygon);
    printByteArray(wkbBuf);
    byte[] fastBuf = FastSerdeUnsafe.serialize(polygon);
    printByteArray(fastBuf);
    byte[] kryoBuf = KryoSerdeOptimized.serialize(polygon);
    printByteArray(kryoBuf);
    System.out.println(ShapeSerdeOptimized.deserialize(shapeBuf));
    System.out.println(WkbSerde.deserialize(wkbBuf));
    System.out.println(FastSerdeUnsafe.deserialize(fastBuf));
    System.out.println(KryoSerdeOptimized.deserialize(kryoBuf));
  }

  /**
   * Print byte array as a sequence of hexadecimal strings
   *
   * @param bytes
   */
  public static void printByteArray(byte[] bytes) {
    System.out.printf("%d bytes: ", bytes.length);
    for (byte b : bytes) {
      System.out.printf("%02X ", b);
    }
    System.out.println();
  }
}
