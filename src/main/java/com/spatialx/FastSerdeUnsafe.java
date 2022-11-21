package com.spatialx;

import com.spatialx.fastserde.GeometryBufferFactory;
import com.spatialx.fastserde.GeometrySerializer;
import org.locationtech.jts.geom.Geometry;

public class FastSerdeUnsafe {

  public static byte[] serialize(Geometry geometry) {
    GeometryBufferFactory.toggleUnsafeGeometryBuffer(true);
    return GeometrySerializer.serialize(geometry);
  }

  public static Geometry deserialize(byte[] buffer) {
    GeometryBufferFactory.toggleUnsafeGeometryBuffer(true);
    return GeometrySerializer.deserialize(buffer);
  }
}
