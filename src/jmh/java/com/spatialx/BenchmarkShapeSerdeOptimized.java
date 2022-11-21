package com.spatialx;

import org.locationtech.jts.geom.Geometry;

public class BenchmarkShapeSerdeOptimized extends BenchmarkSerdeBase {
  protected byte[] serialize(Geometry geom) {
    return ShapeSerdeOptimized.serialize(geom);
  }

  protected Geometry deserialize(byte[] buf) {
    return ShapeSerdeOptimized.deserialize(buf);
  }
}
