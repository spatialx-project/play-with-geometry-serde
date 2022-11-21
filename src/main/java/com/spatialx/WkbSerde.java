package com.spatialx;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.ByteOrderValues;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBReader;
import org.locationtech.jts.io.WKBWriter;

public class WkbSerde {
  private static final GeometryFactory factory = new GeometryFactory();

  public static byte[] serialize(Geometry geometry) {
    WKBWriter wkbWriter = new WKBWriter(getDimension(geometry), ByteOrderValues.LITTLE_ENDIAN);
    return wkbWriter.write(geometry);
  }

  public static Geometry deserialize(byte[] wkb) throws ParseException {
    WKBReader wkbReader = new WKBReader(factory);
    return wkbReader.read(wkb);
  }

  public static int getDimension(Geometry geometry) {
    return geometry.getCoordinate() != null
            && !java.lang.Double.isNaN(geometry.getCoordinate().getZ())
        ? 3
        : 2;
  }
}
