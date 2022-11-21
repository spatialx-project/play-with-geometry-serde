package com.spatialx.fastserde;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;

public class MultiLineStringSerdeTest {
  private static final GeometryFactory gf = new GeometryFactory();

  @Test
  public void testEmptyMultiLineString() {
    MultiLineString multiLineString = gf.createMultiLineString();
    multiLineString.setSRID(4326);
    byte[] bytes = GeometrySerializer.serialize(multiLineString);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(MultiLineString.class, geom);
    Assertions.assertTrue(geom.isEmpty());
    Assertions.assertEquals(4326, geom.getSRID());
  }

  @Test
  public void testMultiLineString() {
    MultiLineString multiLineString =
        gf.createMultiLineString(
            new LineString[] {
              gf.createLineString(
                  new Coordinate[] {
                    new Coordinate(1, 2), new Coordinate(3, 4), new Coordinate(5, 6),
                  }),
              gf.createLineString(),
              gf.createLineString(
                  new Coordinate[] {
                    new Coordinate(7, 8), new Coordinate(9, 10), new Coordinate(11, 12),
                  }),
            });
    multiLineString.setSRID(4326);
    byte[] bytes = GeometrySerializer.serialize(multiLineString);
    Geometry geom = GeometrySerializer.deserialize(bytes);
    Assertions.assertInstanceOf(MultiLineString.class, geom);
    Assertions.assertEquals(4326, geom.getSRID());
    MultiLineString multiLineString2 = (MultiLineString) geom;
    Assertions.assertEquals(3, multiLineString2.getNumGeometries());
    Assertions.assertEquals(multiLineString, multiLineString2);
  }
}
