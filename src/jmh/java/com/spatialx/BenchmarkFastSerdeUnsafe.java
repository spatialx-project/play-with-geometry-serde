package com.spatialx;

import org.locationtech.jts.geom.Geometry;

public class BenchmarkFastSerdeUnsafe extends BenchmarkSerdeBase {
  protected byte[] serialize(Geometry geom) {
    return FastSerdeUnsafe.serialize(geom);
  }

  protected Geometry deserialize(byte[] buf) {
    return FastSerdeUnsafe.deserialize(buf);
  }
}
