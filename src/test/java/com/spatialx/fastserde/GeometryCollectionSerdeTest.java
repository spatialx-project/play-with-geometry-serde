package com.spatialx.fastserde;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

public class GeometryCollectionSerdeTest {
  private static final GeometryFactory gf = new GeometryFactory();

  @Test
  public void testEmptyGeometryCollection() {
    GeometryCollection geometryCollection = gf.createGeometryCollection();
    geometryCollection.setSRID(4326);
    byte[] bytes = GeometrySerializer.serialize(geometryCollection);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(GeometryCollection.class, geom);
    Assertions.assertTrue(geom.isEmpty());
    Assertions.assertEquals(4326, geom.getSRID());
  }

  @Test
  public void testGeometryCollection() {
    Point point = gf.createPoint(new Coordinate(10, 20));
    LineString lineString =
        gf.createLineString(new Coordinate[] {new Coordinate(0, 0), new Coordinate(1, 1)});
    Polygon polygon =
        gf.createPolygon(
            gf.createLinearRing(
                new Coordinate[] {
                  new Coordinate(0, 0),
                  new Coordinate(0, 1),
                  new Coordinate(1, 1),
                  new Coordinate(1, 0),
                  new Coordinate(0, 0)
                }),
            null);
    MultiPoint multiPoint =
        gf.createMultiPointFromCoords(
            new Coordinate[] {
              new Coordinate(10, 20), new Coordinate(30, 40), new Coordinate(50, 60)
            });
    MultiLineString multiLineString =
        gf.createMultiLineString(new LineString[] {lineString, lineString, lineString});
    MultiPolygon multiPolygon = gf.createMultiPolygon(new Polygon[] {polygon, polygon});
    GeometryCollection geometryCollection =
        gf.createGeometryCollection(
            new Geometry[] {
              gf.createPoint(),
              gf.createLineString(),
              gf.createPolygon(),
              point,
              lineString,
              polygon,
              gf.createMultiPoint(),
              gf.createMultiLineString(),
              gf.createMultiPolygon(),
              multiPoint,
              multiLineString,
              multiPolygon
            });
    geometryCollection.setSRID(4326);
    byte[] bytes = GeometrySerializer.serialize(geometryCollection);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(GeometryCollection.class, geom);
    Assertions.assertEquals(4326, geom.getSRID());
    Assertions.assertEquals(geometryCollection.getNumGeometries(), geom.getNumGeometries());
    for (int k = 0; k < geom.getNumGeometries(); k++) {
      Assertions.assertEquals(geometryCollection.getGeometryN(k), geom.getGeometryN(k));
    }
  }

  @Test
  public void testNestedGeometryCollection() {
    Point point = gf.createPoint(new Coordinate(10, 20));
    LineString lineString =
        gf.createLineString(new Coordinate[] {new Coordinate(0, 0), new Coordinate(1, 1)});
    MultiLineString multiLineString =
        gf.createMultiLineString(new LineString[] {lineString, lineString, lineString});
    GeometryCollection geomCollection1 =
        gf.createGeometryCollection(new Geometry[] {point, lineString, multiLineString});
    GeometryCollection geomCollection2 =
        gf.createGeometryCollection(
            new Geometry[] {
              point, geomCollection1, geomCollection1, multiLineString, geomCollection1
            });
    geomCollection2.setSRID(4326);
    byte[] bytes = GeometrySerializer.serialize(geomCollection2);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(GeometryCollection.class, geom);
    Assertions.assertEquals(4326, geom.getSRID());
    Assertions.assertEquals(geomCollection2.getNumGeometries(), geom.getNumGeometries());
    for (int k = 0; k < geom.getNumGeometries(); k++) {
      Assertions.assertEquals(geomCollection2.getGeometryN(k), geom.getGeometryN(k));
    }
  }
}
