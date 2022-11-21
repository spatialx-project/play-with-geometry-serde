package com.spatialx.fastserde;

enum CoordinateType {
  XY(1, 2, false, false),
  XYZ(2, 3, true, false),
  XYM(3, 3, false, true),
  XYZM(4, 4, true, true);

  public final int value;
  public final int ordinates;
  public final boolean hasZ;
  public final boolean hasM;
  public final int bytes;

  CoordinateType(int value, int ordinates, boolean hasZ, boolean hasM) {
    this.value = value;
    this.ordinates = ordinates;
    this.hasZ = hasZ;
    this.hasM = hasM;
    this.bytes = 8 * ordinates;
  }

  public static CoordinateType valueOf(int value) {
    switch (value) {
      case 1:
        return XY;
      case 2:
        return XYZ;
      case 3:
        return XYM;
      case 4:
        return XYZM;
      default:
        throw new IllegalArgumentException("Invalid coordinate type value: " + value);
    }
  }
}
