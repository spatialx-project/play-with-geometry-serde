package com.spatialx;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.apache.sedona.core.optimized.geometryObjects.GeometrySerde;
import org.locationtech.jts.geom.Geometry;

public class KryoSerdeOptimized {
  static final Kryo kryo = new Kryo();
  static final GeometrySerde serde = new GeometrySerde();
  static final Output output = new Output(32, -1);

  public static byte[] serialize(Geometry geometry) {
    output.clear();
    serde.write(kryo, output, geometry);
    return output.toBytes();
  }

  public static Geometry deserialize(byte[] buffer) {
    Input input = new Input(buffer);
    return (Geometry) serde.read(kryo, input, Geometry.class);
  }
}
