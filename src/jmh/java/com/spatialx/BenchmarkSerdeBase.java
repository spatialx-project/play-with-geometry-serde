package com.spatialx;

import java.util.concurrent.TimeUnit;
import org.locationtech.jts.geom.Geometry;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Benchmark)
@Fork(3)
@Warmup(iterations = 5, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.Throughput)
public abstract class BenchmarkSerdeBase {

  BenchmarkedGeometries geometries = new BenchmarkedGeometries();
  byte[] pointBuf;
  byte[] lineStringBuf;
  byte[] polygonBuf;
  byte[] polygonWithHolesBuf;
  byte[] multiPolygonBuf;
  byte[] geometryCollectionBuf;

  @Param({"4", "16", "48"})
  public int segments;

  @Setup(Level.Trial)
  public void doSetup() throws Throwable {
    geometries.setup(segments);
    pointBuf = serialize(geometries.testPoint);
    lineStringBuf = serialize(geometries.testLineString);
    polygonBuf = serialize(geometries.testPolygon);
    polygonWithHolesBuf = serialize(geometries.testPolygonWithHoles);
    multiPolygonBuf = serialize(geometries.testMultiPolygon);
    try {
      geometryCollectionBuf = serialize(geometries.testGeometryCollection);
    } catch (Throwable e) {
      geometryCollectionBuf = null;
    }
  }

  protected abstract byte[] serialize(Geometry geom) throws Throwable;

  protected abstract Geometry deserialize(byte[] buf) throws Throwable;

  @Benchmark
  public byte[] serializePoint() throws Throwable {
    return serialize(geometries.testPoint);
  }

  @Benchmark
  public byte[] serializeLineString() throws Throwable {
    return serialize(geometries.testLineString);
  }

  @Benchmark
  public byte[] serializePolygon() throws Throwable {
    return serialize(geometries.testPolygon);
  }

  @Benchmark
  public byte[] serializePolygonWithHoles() throws Throwable {
    return serialize(geometries.testPolygonWithHoles);
  }

  @Benchmark
  public byte[] serializeMultiPolygon() throws Throwable {
    return serialize(geometries.testMultiPolygon);
  }

  @Benchmark
  public byte[] serializeGeometryCollection() throws Throwable {
    return serialize(geometries.testGeometryCollection);
  }

  @Benchmark
  public Geometry deserializePoint() throws Throwable {
    return deserialize(pointBuf);
  }

  @Benchmark
  public Geometry deserializeLineString() throws Throwable {
    return deserialize(lineStringBuf);
  }

  @Benchmark
  public Geometry deserializePolygon() throws Throwable {
    return deserialize(polygonBuf);
  }

  @Benchmark
  public Geometry deserializePolygonWithHoles() throws Throwable {
    return deserialize(polygonWithHolesBuf);
  }

  @Benchmark
  public Geometry deserializeMultiPolygon() throws Throwable {
    return deserialize(multiPolygonBuf);
  }

  @Benchmark
  public Geometry deserializeGeometryCollection() throws Throwable {
    if (geometryCollectionBuf == null) {
      throw new UnsupportedOperationException("GeometryCollection is not supported by this serde");
    }
    return deserialize(geometryCollectionBuf);
  }
}
