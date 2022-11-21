package com.spatialx;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

public class ShapeSerde {
  private static final GeometryFactory factory = new GeometryFactory();

  public static byte[] serialize(Geometry geometry) {
    return org.apache.sedona.core.formatMapper.shapefileParser.parseUtils.shp.ShapeSerde.serialize(
        geometry);
  }

  public static Geometry deserialize(byte[] buffer) {
    return org.apache.sedona.core.formatMapper.shapefileParser.parseUtils.shp.ShapeSerde
        .deserialize(buffer, factory);
  }
}
