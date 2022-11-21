package com.spatialx.fastserde;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;

public class PolygonSerdeTest {
  private static final GeometryFactory gf = new GeometryFactory();

  @Test
  public void testEmptyPolygon() {
    Polygon polygon = gf.createPolygon();
    polygon.setSRID(4326);
    byte[] bytes = GeometrySerializer.serialize(polygon);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(Polygon.class, geom);
    Assertions.assertTrue(geom.isEmpty());
    Assertions.assertEquals(4326, geom.getSRID());
  }

  @Test
  public void testPolygonWithoutHoles() {
    LinearRing shell =
        gf.createLinearRing(
            new Coordinate[] {
              new Coordinate(0, 0),
              new Coordinate(0, 1),
              new Coordinate(1, 1),
              new Coordinate(1, 0),
              new Coordinate(0, 0)
            });
    Polygon polygon = gf.createPolygon(shell);
    polygon.setSRID(4326);
    byte[] bytes = GeometrySerializer.serialize(polygon);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(Polygon.class, geom);
    Assertions.assertEquals(4326, geom.getSRID());
    Assertions.assertEquals(polygon, geom);
  }

  @Test
  public void testPolygonWithHoles() {
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
    Polygon polygon = gf.createPolygon(shell, new LinearRing[] {hole1, hole2});
    polygon.setSRID(4326);
    byte[] bytes = GeometrySerializer.serialize(polygon);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(Polygon.class, geom);
    Assertions.assertEquals(4326, geom.getSRID());
    Assertions.assertEquals(polygon, geom);
  }
}
