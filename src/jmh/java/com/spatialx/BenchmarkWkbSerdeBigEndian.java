package com.spatialx;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;

public class BenchmarkWkbSerdeBigEndian extends BenchmarkSerdeBase {
  protected byte[] serialize(Geometry geom) {
    return WkbSerdeBigEndian.serialize(geom);
  }

  protected Geometry deserialize(byte[] buf) throws ParseException {
    return WkbSerdeBigEndian.deserialize(buf);
  }
}
