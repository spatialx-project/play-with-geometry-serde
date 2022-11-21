package com.spatialx.fastserde;

public class GeometryBufferFactory {
  private static boolean enableUnsafeGeometryBuffer = true;

  public static void toggleUnsafeGeometryBuffer(boolean enabled) {
    enableUnsafeGeometryBuffer = enabled;
  }

  public static GeometryBuffer create(int bufferSize) {
    if (UnsafeGeometryBuffer.isUnsafeAvailable() && enableUnsafeGeometryBuffer) {
      return new UnsafeGeometryBuffer(bufferSize);
    } else {
      return new ByteBufferGeometryBuffer(bufferSize);
    }
  }

  public static GeometryBuffer create(String bufferType, int bufferSize) {
    switch (bufferType) {
      case "bytebuffer":
        return new ByteBufferGeometryBuffer(bufferSize);
      case "unsafe":
        return new UnsafeGeometryBuffer(bufferSize);
      default:
        throw new IllegalArgumentException("Unknown buffer type: " + bufferType);
    }
  }

  public static GeometryBuffer wrap(byte[] bytes) {
    if (UnsafeGeometryBuffer.isUnsafeAvailable() && enableUnsafeGeometryBuffer) {
      return new UnsafeGeometryBuffer(bytes);
    } else {
      return new ByteBufferGeometryBuffer(bytes);
    }
  }

  public static GeometryBuffer wrap(String bufferType, byte[] bytes) {
    switch (bufferType) {
      case "bytebuffer":
        return new ByteBufferGeometryBuffer(bytes);
      case "unsafe":
        return new UnsafeGeometryBuffer(bytes);
      default:
        throw new IllegalArgumentException("Unknown buffer type: " + bufferType);
    }
  }
}
