package com.spatialx;

import org.locationtech.jts.geom.Geometry;

public class BenchmarkKryoSerdeOptimized extends BenchmarkSerdeBase {
  protected byte[] serialize(Geometry geom) {
    return KryoSerdeOptimized.serialize(geom);
  }

  protected Geometry deserialize(byte[] buf) {
    return KryoSerdeOptimized.deserialize(buf);
  }
}
