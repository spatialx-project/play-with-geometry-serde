package com.spatialx.fastserde;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.CoordinateXYM;
import org.locationtech.jts.geom.CoordinateXYZM;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;

public class LineStringSerdeTest {
  private static final GeometryFactory gf = new GeometryFactory();

  @Test
  public void testEmptyLineString() {
    LineString lineString = gf.createLineString();
    lineString.setSRID(4326);
    byte[] bytes = GeometrySerializer.serialize(lineString);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(LineString.class, geom);
    Assertions.assertTrue(geom.isEmpty());
    Assertions.assertEquals(4326, geom.getSRID());
  }

  @Test
  public void testLineString() {
    Coordinate[] coordinates =
        new Coordinate[] {
          new Coordinate(1.0, 2.0), new Coordinate(3.0, 4.0), new Coordinate(5.0, 6.0),
        };
    LineString lineString = gf.createLineString(coordinates);
    lineString.setSRID(4326);
    byte[] bytes = GeometrySerializer.serialize(lineString);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(LineString.class, geom);
    Assertions.assertEquals(4326, geom.getSRID());
    Assertions.assertEquals(3, geom.getNumPoints());
    CoordinateSequence coordSeq = ((LineString) geom).getCoordinateSequence();
    Coordinate coord = coordSeq.getCoordinate(0);
    Assertions.assertEquals(1.0, coord.x);
    Assertions.assertEquals(2.0, coord.y);
    Assertions.assertTrue(Double.isNaN(coord.getZ()));
    Assertions.assertTrue(Double.isNaN(coord.getM()));
    coord = coordSeq.getCoordinate(1);
    Assertions.assertEquals(3.0, coord.x);
    Assertions.assertEquals(4.0, coord.y);
    coord = coordSeq.getCoordinate(2);
    Assertions.assertEquals(5.0, coord.x);
    Assertions.assertEquals(6.0, coord.y);
  }

  @Test
  public void testLineStringXYZ() {
    Coordinate[] coordinates =
        new Coordinate[] {
          new Coordinate(1.0, 2.0, 3.0), new Coordinate(4.0, 5.0, 6.0),
        };
    LineString lineString = gf.createLineString(coordinates);
    lineString.setSRID(4326);
    byte[] bytes = GeometrySerializer.serialize(lineString);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(LineString.class, geom);
    Assertions.assertEquals(4326, geom.getSRID());
    Assertions.assertEquals(2, geom.getNumPoints());
    CoordinateSequence coordSeq = ((LineString) geom).getCoordinateSequence();
    Coordinate coord = coordSeq.getCoordinate(0);
    Assertions.assertEquals(1.0, coord.x);
    Assertions.assertEquals(2.0, coord.y);
    Assertions.assertEquals(3.0, coord.getZ());
    Assertions.assertTrue(Double.isNaN(coord.getM()));
    coord = coordSeq.getCoordinate(1);
    Assertions.assertEquals(4.0, coord.x);
    Assertions.assertEquals(5.0, coord.y);
    Assertions.assertEquals(6.0, coord.getZ());
  }

  @Test
  public void testLineStringXYM() {
    Coordinate[] coordinates =
        new Coordinate[] {
          new CoordinateXYM(1.0, 2.0, 3.0), new CoordinateXYM(4.0, 5.0, 6.0),
        };
    LineString lineString = gf.createLineString(coordinates);
    lineString.setSRID(4326);
    byte[] bytes = GeometrySerializer.serialize(lineString);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(LineString.class, geom);
    Assertions.assertEquals(4326, geom.getSRID());
    Assertions.assertEquals(2, geom.getNumPoints());
    CoordinateSequence coordSeq = ((LineString) geom).getCoordinateSequence();
    Coordinate coord = coordSeq.getCoordinate(0);
    Assertions.assertEquals(1.0, coord.x);
    Assertions.assertEquals(2.0, coord.y);
    Assertions.assertTrue(Double.isNaN(coord.getZ()));
    Assertions.assertEquals(3.0, coord.getM());
    coord = coordSeq.getCoordinate(1);
    Assertions.assertEquals(4.0, coord.x);
    Assertions.assertEquals(5.0, coord.y);
    Assertions.assertTrue(Double.isNaN(coord.getZ()));
    Assertions.assertEquals(6.0, coord.getM());
  }

  @Test
  public void testLineStringXYZM() {
    Coordinate[] coordinates =
        new Coordinate[] {
          new CoordinateXYZM(1.0, 2.0, 3.0, 4.0), new CoordinateXYZM(5.0, 6.0, 7.0, 8.0),
        };
    LineString lineString = gf.createLineString(coordinates);
    lineString.setSRID(4326);
    byte[] bytes = GeometrySerializer.serialize(lineString);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(LineString.class, geom);
    Assertions.assertEquals(4326, geom.getSRID());
    Assertions.assertEquals(2, geom.getNumPoints());
    CoordinateSequence coordSeq = ((LineString) geom).getCoordinateSequence();
    Coordinate coord = coordSeq.getCoordinate(0);
    Assertions.assertEquals(1.0, coord.x);
    Assertions.assertEquals(2.0, coord.y);
    Assertions.assertEquals(3.0, coord.getZ());
    Assertions.assertEquals(4.0, coord.getM());
    coord = coordSeq.getCoordinate(1);
    Assertions.assertEquals(5.0, coord.x);
    Assertions.assertEquals(6.0, coord.y);
    Assertions.assertEquals(7.0, coord.getZ());
    Assertions.assertEquals(8.0, coord.getM());
  }
}
