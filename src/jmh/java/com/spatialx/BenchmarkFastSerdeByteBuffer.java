package com.spatialx;

import org.locationtech.jts.geom.Geometry;

public class BenchmarkFastSerdeByteBuffer extends BenchmarkSerdeBase {
  protected byte[] serialize(Geometry geom) {
    return FastSerdeByteBuffer.serialize(geom);
  }

  protected Geometry deserialize(byte[] buf) {
    return FastSerdeByteBuffer.deserialize(buf);
  }
}
