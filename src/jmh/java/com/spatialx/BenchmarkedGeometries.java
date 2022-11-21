package com.spatialx;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

public class BenchmarkedGeometries {
  public GeometryFactory factory;
  public int segments;

  public Point testPoint;
  public LineString testLineString;
  public Polygon testPolygon;
  public Polygon testPolygonWithHoles;
  public MultiPolygon testMultiPolygon;
  public GeometryCollection testGeometryCollection;

  public void setup(int segments) {
    this.segments = segments;
    factory = new GeometryFactory();
    initPoint();
    initLineString();
    initPolygon();
    initPolygonWithHoles();
    initMultiPolygon();
    initGeometryCollection();
  }

  public void initPoint() {
    Coordinate coordinate = new Coordinate(10, 20);
    testPoint = factory.createPoint(coordinate);
  }

  public void initLineString() {
    Coordinate[] coordinates = new Coordinate[segments];
    for (int k = 0; k < coordinates.length; k++) {
      coordinates[k] = new Coordinate(k, k);
    }
    testLineString = factory.createLineString(coordinates);
  }

  public void initPolygon() {
    testPolygon = (Polygon) testPoint.buffer(1, segments / 4);
  }

  public void initPolygonWithHoles() {
    int quadrantSegments = segments / 4;
    Polygon outer = (Polygon) testPoint.buffer(10, quadrantSegments);
    Point innerCenter1 = factory.createPoint(new Coordinate(5, 20));
    Point innerCenter2 = factory.createPoint(new Coordinate(15, 20));
    Polygon inner1 = (Polygon) innerCenter1.buffer(3, quadrantSegments);
    Polygon inner2 = (Polygon) innerCenter2.buffer(3, quadrantSegments);
    LinearRing outerRing = outer.getExteriorRing();
    LinearRing[] innerRings = new LinearRing[2];
    innerRings[0] = inner1.getExteriorRing().reverse();
    innerRings[1] = inner2.getExteriorRing().reverse();
    testPolygonWithHoles = factory.createPolygon(outerRing, innerRings);
  }

  public void initMultiPolygon() {
    int quadrantSegments = segments / 4;
    Polygon[] polygons = new Polygon[3];
    polygons[0] = (Polygon) testPoint.buffer(1, quadrantSegments);
    polygons[1] = (Polygon) testPoint.buffer(2, quadrantSegments);
    polygons[2] = (Polygon) testPoint.buffer(3, quadrantSegments);
    testMultiPolygon = factory.createMultiPolygon(polygons);
  }

  public void initGeometryCollection() {
    Geometry[] geometries =
        new Geometry[] {
          testPoint, testLineString, testPolygon, testPolygonWithHoles, testMultiPolygon
        };
    testGeometryCollection = factory.createGeometryCollection(geometries);
  }
}
