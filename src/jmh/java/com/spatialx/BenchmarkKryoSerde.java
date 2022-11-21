package com.spatialx;

import org.locationtech.jts.geom.Geometry;

public class BenchmarkKryoSerde extends BenchmarkSerdeBase {
  protected byte[] serialize(Geometry geom) {
    return KryoSerde.serialize(geom);
  }

  protected Geometry deserialize(byte[] buf) {
    return KryoSerde.deserialize(buf);
  }
}
