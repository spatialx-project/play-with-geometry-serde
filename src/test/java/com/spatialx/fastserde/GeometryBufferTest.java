package com.spatialx.fastserde;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.CoordinateXYM;
import org.locationtech.jts.geom.CoordinateXYZM;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;

public class GeometryBufferTest {
  static Stream<String> geometryBufferTypes() {
    return Stream.of("bytebuffer", "unsafe");
  }

  @ParameterizedTest
  @MethodSource("geometryBufferTypes")
  public void testPutGetByte(String bufferType) {
    GeometryBuffer buffer = GeometryBufferFactory.create(bufferType, 1);
    buffer.putByte(0, (byte) 1);
    assertEquals((byte) 1, buffer.getByte(0));
  }

  @ParameterizedTest
  @MethodSource("geometryBufferTypes")
  public void testPutGetBytes(String bufferType) {
    GeometryBuffer buffer = GeometryBufferFactory.create(bufferType, 4);
    buffer.putBytes(2, new byte[] {3, 4});
    buffer.putBytes(0, new byte[] {1, 2});
    byte[] bytes = new byte[4];
    buffer.getBytes(bytes, 2, 2);
    assertEquals((byte) 3, bytes[0]);
    assertEquals((byte) 4, bytes[1]);
    buffer.getBytes(bytes, 0, 4);
    assertEquals((byte) 1, bytes[0]);
    assertEquals((byte) 2, bytes[1]);
    assertEquals((byte) 3, bytes[2]);
    assertEquals((byte) 4, bytes[3]);
  }

  @ParameterizedTest
  @MethodSource("geometryBufferTypes")
  public void testPutGetInt(String bufferType) {
    GeometryBuffer buffer = GeometryBufferFactory.create(bufferType, 4);
    buffer.putInt(0, 1);
    assertEquals(1, buffer.getInt(0));
  }

  @ParameterizedTest
  @MethodSource("geometryBufferTypes")
  public void testPutGetCoordinate(String bufferType) {
    GeometryBuffer buffer = GeometryBufferFactory.create(bufferType, 16);
    buffer.putCoordinate(0, new Coordinate(1, 2));
    CoordinateSequence coordinates = buffer.getCoordinate(0);
    assertEquals(1, coordinates.size());
    Coordinate coordinate = coordinates.getCoordinate(0);
    assertEquals(1, coordinate.x);
    assertEquals(2, coordinate.y);
  }

  @ParameterizedTest
  @MethodSource("geometryBufferTypes")
  public void testPutGetCoordinateXYZ(String bufferType) {
    GeometryBuffer buffer = GeometryBufferFactory.create(bufferType, 24);
    buffer.setCoordinateType(CoordinateType.XYZ);
    buffer.putCoordinate(0, new Coordinate(1, 2, 3));
    CoordinateSequence coordinates = buffer.getCoordinate(0);
    assertEquals(1, coordinates.size());
    Coordinate coordinate = coordinates.getCoordinate(0);
    assertEquals(1, coordinate.x);
    assertEquals(2, coordinate.y);
    assertEquals(3, coordinate.getZ());
  }

  @ParameterizedTest
  @MethodSource("geometryBufferTypes")
  public void testPutGetCoordinateXYM(String bufferType) {
    GeometryBuffer buffer = GeometryBufferFactory.create(bufferType, 24);
    buffer.setCoordinateType(CoordinateType.XYM);
    buffer.putCoordinate(0, new CoordinateXYM(1, 2, 3));
    CoordinateSequence coordinates = buffer.getCoordinate(0);
    assertEquals(1, coordinates.size());
    Coordinate coordinate = coordinates.getCoordinate(0);
    assertEquals(1, coordinate.x);
    assertEquals(2, coordinate.y);
    assertEquals(3, coordinate.getM());
  }

  @ParameterizedTest
  @MethodSource("geometryBufferTypes")
  public void testPutGetCoordinateXYZM(String bufferType) {
    GeometryBuffer buffer = GeometryBufferFactory.create(bufferType, 32);
    buffer.setCoordinateType(CoordinateType.XYZM);
    buffer.putCoordinate(0, new CoordinateXYZM(1, 2, 3, 4));
    CoordinateSequence coordinates = buffer.getCoordinate(0);
    assertEquals(1, coordinates.size());
    Coordinate coordinate = coordinates.getCoordinate(0);
    assertEquals(1, coordinate.x);
    assertEquals(2, coordinate.y);
    assertEquals(3, coordinate.getZ());
    assertEquals(4, coordinate.getM());
  }

  @ParameterizedTest
  @MethodSource("geometryBufferTypes")
  public void testPutGetCoordinateWithUnmatchedCoordType(String bufferType) {
    GeometryBuffer buffer = GeometryBufferFactory.create(bufferType, 24);
    buffer.setCoordinateType(CoordinateType.XYZ);
    buffer.putCoordinate(0, new CoordinateXYM(1, 2, 3));
    CoordinateSequence coordinates = buffer.getCoordinate(0);
    assertEquals(1, coordinates.size());
    Coordinate coordinate = coordinates.getCoordinate(0);
    assertEquals(1, coordinate.x);
    assertEquals(2, coordinate.y);
    assertTrue(Double.isNaN(coordinate.getZ()));
  }

  @ParameterizedTest
  @MethodSource("geometryBufferTypes")
  public void testPutGetCoordinates(String bufferType) {
    GeometryBuffer buffer = GeometryBufferFactory.create(bufferType, 32);
    CoordinateSequence coordinates =
        new CoordinateArraySequence(new Coordinate[] {new Coordinate(1, 2), new Coordinate(3, 4)});
    buffer.putCoordinates(0, coordinates);
    CoordinateSequence coordinates2 = buffer.getCoordinates(0, 2);
    assertEquals(1, coordinates2.getCoordinate(0).x);
    assertEquals(2, coordinates2.getCoordinate(0).y);
    assertEquals(3, coordinates2.getCoordinate(1).x);
    assertEquals(4, coordinates2.getCoordinate(1).y);
  }

  @ParameterizedTest
  @MethodSource("geometryBufferTypes")
  public void testPutGetCoordinatesXYZ(String bufferType) {
    GeometryBuffer buffer = GeometryBufferFactory.create(bufferType, 48);
    buffer.setCoordinateType(CoordinateType.XYZ);
    CoordinateSequence coordinates =
        new CoordinateArraySequence(
            new Coordinate[] {new Coordinate(1, 2, 3), new Coordinate(4, 5, 6)});
    buffer.putCoordinates(0, coordinates);
    CoordinateSequence coordinates2 = buffer.getCoordinates(0, 2);
    assertEquals(1, coordinates2.getCoordinate(0).x);
    assertEquals(2, coordinates2.getCoordinate(0).y);
    assertEquals(3, coordinates2.getCoordinate(0).getZ());
    assertEquals(4, coordinates2.getCoordinate(1).x);
    assertEquals(5, coordinates2.getCoordinate(1).y);
    assertEquals(6, coordinates2.getCoordinate(1).getZ());
  }

  @ParameterizedTest
  @MethodSource("geometryBufferTypes")
  public void testPutGetCoordinatesXYM(String bufferType) {
    GeometryBuffer buffer = GeometryBufferFactory.create(bufferType, 48);
    buffer.setCoordinateType(CoordinateType.XYM);
    CoordinateSequence coordinates =
        new CoordinateArraySequence(
            new Coordinate[] {new CoordinateXYM(1, 2, 3), new CoordinateXYM(4, 5, 6)});
    buffer.putCoordinates(0, coordinates);
    CoordinateSequence coordinates2 = buffer.getCoordinates(0, 2);
    assertEquals(1, coordinates2.getCoordinate(0).x);
    assertEquals(2, coordinates2.getCoordinate(0).y);
    assertEquals(3, coordinates2.getCoordinate(0).getM());
    assertEquals(4, coordinates2.getCoordinate(1).x);
    assertEquals(5, coordinates2.getCoordinate(1).y);
    assertEquals(6, coordinates2.getCoordinate(1).getM());
  }

  @ParameterizedTest
  @MethodSource("geometryBufferTypes")
  public void testPutGetCoordinatesXYZM(String bufferType) {
    GeometryBuffer buffer = GeometryBufferFactory.create(bufferType, 64);
    buffer.setCoordinateType(CoordinateType.XYZM);
    CoordinateSequence coordinates =
        new CoordinateArraySequence(
            new Coordinate[] {new CoordinateXYZM(1, 2, 3, 4), new CoordinateXYZM(5, 6, 7, 8)});
    buffer.putCoordinates(0, coordinates);
    CoordinateSequence coordinates2 = buffer.getCoordinates(0, 2);
    assertEquals(1, coordinates2.getCoordinate(0).x);
    assertEquals(2, coordinates2.getCoordinate(0).y);
    assertEquals(3, coordinates2.getCoordinate(0).getZ());
    assertEquals(4, coordinates2.getCoordinate(0).getM());
    assertEquals(5, coordinates2.getCoordinate(1).x);
    assertEquals(6, coordinates2.getCoordinate(1).y);
    assertEquals(7, coordinates2.getCoordinate(1).getZ());
    assertEquals(8, coordinates2.getCoordinate(1).getM());
  }

  @ParameterizedTest
  @MethodSource("geometryBufferTypes")
  public void testPutGetCoordinatesWithUnmatchedCoordType(String bufferType) {
    GeometryBuffer buffer = GeometryBufferFactory.create(bufferType, 48);
    buffer.setCoordinateType(CoordinateType.XYZ);
    CoordinateSequence coordinates =
        new CoordinateArraySequence(
            new Coordinate[] {new CoordinateXYM(1, 2, 3), new CoordinateXYM(4, 5, 6)});
    buffer.putCoordinates(0, coordinates);
    CoordinateSequence coordinates2 = buffer.getCoordinates(0, 2);
    assertEquals(1, coordinates2.getCoordinate(0).x);
    assertEquals(2, coordinates2.getCoordinate(0).y);
    assertTrue(Double.isNaN(coordinates2.getCoordinate(0).getZ()));
    assertEquals(4, coordinates2.getCoordinate(1).x);
    assertEquals(5, coordinates2.getCoordinate(1).y);
    assertTrue(Double.isNaN(coordinates2.getCoordinate(1).getZ()));
  }

  @ParameterizedTest
  @MethodSource("geometryBufferTypes")
  public void testSlice(String bufferType) {
    GeometryBuffer buffer = GeometryBufferFactory.create(bufferType, 16);
    buffer.putBytes(0, new byte[] {1, 2, 3, 4});
    GeometryBuffer slice = buffer.slice(0);
    assertEquals((byte) 1, slice.getByte(0));
    assertEquals((byte) 2, slice.getByte(1));
    assertEquals((byte) 3, slice.getByte(2));
    assertEquals((byte) 4, slice.getByte(3));
    slice = slice.slice(1);
    assertEquals((byte) 2, slice.getByte(0));
    assertEquals((byte) 4, slice.getByte(2));
    slice = slice.slice(1);
    assertEquals((byte) 3, slice.getByte(0));
    assertEquals((byte) 4, slice.getByte(1));
    assertEquals(14, slice.getLength());
  }

  @ParameterizedTest
  @MethodSource("geometryBufferTypes")
  public void testToByteArray(String bufferType) {
    GeometryBuffer buffer = GeometryBufferFactory.create(bufferType, 2);
    buffer.putByte(0, (byte) 1);
    buffer.putByte(1, (byte) 2);
    byte[] bytes = buffer.toByteArray();
    assertEquals(2, bytes.length);
    assertEquals((byte) 1, bytes[0]);
    assertEquals((byte) 2, bytes[1]);
  }

  @ParameterizedTest
  @MethodSource("geometryBufferTypes")
  public void testMixedDataTypes(String bufferType) {
    GeometryBuffer buffer = GeometryBufferFactory.create(bufferType, 28);
    buffer.putByte(0, (byte) 1);
    buffer.putInt(4, 100);
    buffer.putCoordinate(8, new Coordinate(10, 20));
    buffer.putInt(24, 200);
    assertEquals((byte) 1, buffer.getByte(0));
    assertEquals(100, buffer.getInt(4));
    assertEquals(10, buffer.getCoordinate(8).getCoordinate(0).x);
    assertEquals(20, buffer.getCoordinate(8).getCoordinate(0).y);
    assertEquals(200, buffer.getInt(24));
  }

  @ParameterizedTest
  @MethodSource("geometryBufferTypes")
  public void testWrap(String bufferType) {
    byte[] bytes = new byte[] {1, 2, 3, 4};
    GeometryBuffer buffer = GeometryBufferFactory.wrap(bufferType, bytes);
    assertEquals(4, buffer.getLength());
    assertEquals((byte) 1, buffer.getByte(0));
    assertEquals((byte) 2, buffer.getByte(1));
    assertEquals((byte) 3, buffer.getByte(2));
    assertEquals((byte) 4, buffer.getByte(3));
  }
}
