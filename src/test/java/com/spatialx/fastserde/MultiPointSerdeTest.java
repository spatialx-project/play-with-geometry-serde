package com.spatialx.fastserde;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateXYM;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.Point;

public class MultiPointSerdeTest {
  private static final GeometryFactory gf = new GeometryFactory();

  @Test
  public void testEmptyMultiPoint() {
    MultiPoint multiPoint = gf.createMultiPoint();
    multiPoint.setSRID(4326);
    byte[] bytes = GeometrySerializer.serialize(multiPoint);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(MultiPoint.class, geom);
    Assertions.assertTrue(geom.isEmpty());
    Assertions.assertEquals(4326, geom.getSRID());
  }

  @Test
  public void testMultiPoint() {
    MultiPoint multiPoint =
        gf.createMultiPointFromCoords(
            new Coordinate[] {
              new Coordinate(1, 2), new Coordinate(3, 4), new Coordinate(5, 6),
            });
    multiPoint.setSRID(4326);
    byte[] bytes = GeometrySerializer.serialize(multiPoint);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(MultiPoint.class, geom);
    Assertions.assertEquals(4326, geom.getSRID());
    MultiPoint multiPoint2 = (MultiPoint) geom;
    Assertions.assertEquals(3, multiPoint2.getNumGeometries());
    Assertions.assertEquals(1, multiPoint2.getGeometryN(0).getCoordinate().x);
    Assertions.assertEquals(2, multiPoint2.getGeometryN(0).getCoordinate().y);
    Assertions.assertEquals(3, multiPoint2.getGeometryN(1).getCoordinate().x);
    Assertions.assertEquals(4, multiPoint2.getGeometryN(1).getCoordinate().y);
    Assertions.assertEquals(5, multiPoint2.getGeometryN(2).getCoordinate().x);
    Assertions.assertEquals(6, multiPoint2.getGeometryN(2).getCoordinate().y);
  }

  @Test
  public void testMultiPointWithEmptyPoints() {
    Point[] points =
        new Point[] {
          gf.createPoint(new Coordinate(1, 2)),
          gf.createPoint(),
          gf.createPoint(new Coordinate(3, 4))
        };
    MultiPoint multiPoint = gf.createMultiPoint(points);
    byte[] bytes = GeometrySerializer.serialize(multiPoint);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(MultiPoint.class, geom);
    Assertions.assertEquals(0, geom.getSRID());
    MultiPoint multiPoint2 = (MultiPoint) geom;
    Assertions.assertEquals(3, multiPoint2.getNumGeometries());
    Assertions.assertEquals(1, multiPoint2.getGeometryN(0).getCoordinate().x);
    Assertions.assertEquals(2, multiPoint2.getGeometryN(0).getCoordinate().y);
    Assertions.assertTrue(multiPoint2.getGeometryN(1).isEmpty());
    Assertions.assertEquals(3, multiPoint2.getGeometryN(2).getCoordinate().x);
    Assertions.assertEquals(4, multiPoint2.getGeometryN(2).getCoordinate().y);
  }

  @Test
  public void testMultiPointXYM() {
    MultiPoint multiPoint =
        gf.createMultiPointFromCoords(
            new Coordinate[] {
              new CoordinateXYM(1, 2, 3), new CoordinateXYM(4, 5, 6), new CoordinateXYM(7, 8, 9),
            });
    multiPoint.setSRID(4326);
    byte[] bytes = GeometrySerializer.serialize(multiPoint);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(MultiPoint.class, geom);
    Assertions.assertEquals(4326, geom.getSRID());
    MultiPoint multiPoint2 = (MultiPoint) geom;
    Assertions.assertEquals(3, multiPoint2.getNumGeometries());
    Assertions.assertEquals(1, multiPoint2.getGeometryN(0).getCoordinate().x);
    Assertions.assertEquals(2, multiPoint2.getGeometryN(0).getCoordinate().y);
    Assertions.assertEquals(3, multiPoint2.getGeometryN(0).getCoordinate().getM());
    Assertions.assertEquals(4, multiPoint2.getGeometryN(1).getCoordinate().x);
    Assertions.assertEquals(5, multiPoint2.getGeometryN(1).getCoordinate().y);
    Assertions.assertEquals(6, multiPoint2.getGeometryN(1).getCoordinate().getM());
    Assertions.assertEquals(7, multiPoint2.getGeometryN(2).getCoordinate().x);
    Assertions.assertEquals(8, multiPoint2.getGeometryN(2).getCoordinate().y);
    Assertions.assertEquals(9, multiPoint2.getGeometryN(2).getCoordinate().getM());
  }
}
