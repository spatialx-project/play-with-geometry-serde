package com.spatialx.fastserde;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;

interface GeometryBuffer {
  CoordinateType getCoordinateType();

  void setCoordinateType(CoordinateType coordinateType);

  int getLength();

  void mark(int offset);

  int getMark();

  void putByte(int offset, byte value);

  byte getByte(int offset);

  void putBytes(int offset, byte[] bytes);

  void getBytes(byte[] bytes, int offset, int length);

  void putInt(int offset, int value);

  int getInt(int offset);

  void putCoordinate(int offset, Coordinate coordinate);

  CoordinateSequence getCoordinate(int offset);

  void putCoordinates(int offset, CoordinateSequence coordinates);

  CoordinateSequence getCoordinates(int offset, int numCoordinates);

  GeometryBuffer slice(int offset);

  byte[] toByteArray();
}
