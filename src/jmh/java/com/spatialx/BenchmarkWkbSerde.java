package com.spatialx;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;

public class BenchmarkWkbSerde extends BenchmarkSerdeBase {
  protected byte[] serialize(Geometry geom) {
    return WkbSerde.serialize(geom);
  }

  protected Geometry deserialize(byte[] buf) throws ParseException {
    return WkbSerde.deserialize(buf);
  }
}
