package com.spatialx;

import com.spatialx.fastserde.GeometryBufferFactory;
import com.spatialx.fastserde.GeometrySerializer;
import org.locationtech.jts.geom.Geometry;

public class FastSerdeByteBuffer {
  public static byte[] serialize(Geometry geometry) {
    GeometryBufferFactory.toggleUnsafeGeometryBuffer(false);
    return GeometrySerializer.serialize(geometry);
  }

  public static Geometry deserialize(byte[] buffer) {
    GeometryBufferFactory.toggleUnsafeGeometryBuffer(false);
    return GeometrySerializer.deserialize(buffer);
  }
}
