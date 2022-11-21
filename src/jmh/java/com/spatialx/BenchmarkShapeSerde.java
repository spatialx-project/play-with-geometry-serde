package com.spatialx;

import org.locationtech.jts.geom.Geometry;

public class BenchmarkShapeSerde extends BenchmarkSerdeBase {
  protected byte[] serialize(Geometry geom) {
    return ShapeSerde.serialize(geom);
  }

  protected Geometry deserialize(byte[] buf) {
    return ShapeSerde.deserialize(buf);
  }
}
