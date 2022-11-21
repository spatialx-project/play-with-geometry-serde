package com.spatialx.fastserde;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;

public class MultiPolygonSerdeTest {
  private static final GeometryFactory gf = new GeometryFactory();

  @Test
  public void testEmptyMultiPolygon() {
    MultiPolygon multiPolygon = gf.createMultiPolygon();
    multiPolygon.setSRID(4326);
    byte[] bytes = GeometrySerializer.serialize(multiPolygon);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(MultiPolygon.class, geom);
    Assertions.assertTrue(geom.isEmpty());
    Assertions.assertEquals(4326, geom.getSRID());
  }

  @Test
  public void testMultiPolygon() {
    LinearRing shell =
        gf.createLinearRing(
            new Coordinate[] {
              new Coordinate(0, 0),
              new Coordinate(0, 1),
              new Coordinate(1, 1),
              new Coordinate(1, 0),
              new Coordinate(0, 0)
            });
    LinearRing hole1 =
        gf.createLinearRing(
            new Coordinate[] {
              new Coordinate(0.1, 0.1),
              new Coordinate(0.1, 0.2),
              new Coordinate(0.2, 0.2),
              new Coordinate(0.2, 0.1),
              new Coordinate(0.1, 0.1)
            });
    LinearRing hole2 =
        gf.createLinearRing(
            new Coordinate[] {
              new Coordinate(0.3, 0.3),
              new Coordinate(0.3, 0.4),
              new Coordinate(0.4, 0.4),
              new Coordinate(0.4, 0.3),
              new Coordinate(0.3, 0.3)
            });
    LinearRing[] holes = new LinearRing[] {hole1, hole2};
    MultiPolygon multiPolygon =
        gf.createMultiPolygon(
            new Polygon[] {
              gf.createPolygon(shell), gf.createPolygon(), gf.createPolygon(shell, holes)
            });
    multiPolygon.setSRID(4326);
    byte[] bytes = GeometrySerializer.serialize(multiPolygon);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(MultiPolygon.class, geom);
    Assertions.assertEquals(4326, geom.getSRID());
    MultiPolygon mp = (MultiPolygon) geom;
    Assertions.assertEquals(3, mp.getNumGeometries());
    Assertions.assertEquals(multiPolygon, mp);
  }
}
