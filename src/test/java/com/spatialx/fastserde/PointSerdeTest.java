package com.spatialx.fastserde;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateXYM;
import org.locationtech.jts.geom.CoordinateXYZM;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

public class PointSerdeTest {
  private static final GeometryFactory gf = new GeometryFactory();

  @Test
  public void testEmptyPoint() {
    byte[] bytes = GeometrySerializer.serialize(gf.createPoint());
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(Point.class, geom);
    Assertions.assertTrue(geom.isEmpty());
    Assertions.assertEquals(0, geom.getSRID());
  }

  @Test
  public void testEmptyPointWithSRID() {
    Point point = gf.createPoint();
    point.setSRID(4326);
    byte[] bytes = GeometrySerializer.serialize(point);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(Point.class, geom);
    Assertions.assertTrue(geom.isEmpty());
    Assertions.assertEquals(4326, geom.getSRID());
  }

  @Test
  public void test2DPoint() {
    Point point = gf.createPoint(new Coordinate(1.0, 2.0));
    point.setSRID(4326);
    byte[] bytes = GeometrySerializer.serialize(point);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(Point.class, geom);
    Assertions.assertFalse(geom.isEmpty());
    Assertions.assertEquals(4326, geom.getSRID());
    Assertions.assertEquals(1.0, geom.getCoordinate().x);
    Assertions.assertEquals(2.0, geom.getCoordinate().y);
    Assertions.assertTrue(Double.isNaN(geom.getCoordinate().getZ()));
    Assertions.assertTrue(Double.isNaN(geom.getCoordinate().getM()));
  }

  @Test
  public void testXYZPoint() {
    Point point = gf.createPoint(new Coordinate(1.0, 2.0, 3.0));
    point.setSRID(4326);
    byte[] bytes = GeometrySerializer.serialize(point);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(Point.class, geom);
    Assertions.assertFalse(geom.isEmpty());
    Assertions.assertEquals(4326, geom.getSRID());
    Assertions.assertEquals(1.0, geom.getCoordinate().x);
    Assertions.assertEquals(2.0, geom.getCoordinate().y);
    Assertions.assertEquals(3.0, geom.getCoordinate().getZ());
    Assertions.assertTrue(Double.isNaN(geom.getCoordinate().getM()));
  }

  @Test
  public void testXYMPoint() {
    Point point = gf.createPoint(new CoordinateXYM(1.0, 2.0, 3.0));
    byte[] bytes = GeometrySerializer.serialize(point);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(Point.class, geom);
    Assertions.assertFalse(geom.isEmpty());
    Assertions.assertEquals(0, geom.getSRID());
    Assertions.assertEquals(1.0, geom.getCoordinate().x);
    Assertions.assertEquals(2.0, geom.getCoordinate().y);
    Assertions.assertTrue(Double.isNaN(geom.getCoordinate().getZ()));
    Assertions.assertEquals(3.0, geom.getCoordinate().getM());
  }

  @Test
  public void testXYZMPoint() {
    Point point = gf.createPoint(new CoordinateXYZM(1.0, 2.0, 3.0, 4.0));
    point.setSRID(4326);
    byte[] bytes = GeometrySerializer.serialize(point);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(Point.class, geom);
    Assertions.assertFalse(geom.isEmpty());
    Assertions.assertEquals(4326, geom.getSRID());
    Assertions.assertEquals(1.0, geom.getCoordinate().x);
    Assertions.assertEquals(2.0, geom.getCoordinate().y);
    Assertions.assertEquals(3.0, geom.getCoordinate().getZ());
    Assertions.assertEquals(4.0, geom.getCoordinate().getM());
  }
}
