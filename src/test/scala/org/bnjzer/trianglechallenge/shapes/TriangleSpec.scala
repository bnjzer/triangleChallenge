package org.bnjzer.trianglechallenge.shapes

import org.bnjzer.trianglechallenge.UnitSpec

class TriangleSpec extends UnitSpec {
  val equilateralTriangle = Triangle.buildFromStrings("1.0", "1.0", "1.0").get
  val isoscelesTriangle = Triangle.buildFromStrings("1.0", "3.0", "1.0").get
  val scaleneTriangle = Triangle.buildFromStrings("1.0", "2.0", "3.0").get

  "A Triangle" should "be equilateral when all of its sides are equal" in {
    equilateralTriangle.triangleType shouldBe TriangleType.EQUILATERAL
  }

  it should "be isosceles when 2 of its sides are equal" in {
    isoscelesTriangle.triangleType shouldBe TriangleType.ISOSCELES
  }

  it should "be scalene when all of its sides have different lengths" in {
    scaleneTriangle.triangleType shouldBe TriangleType.SCALENE
  }

  it should "contain EQUILATERAL in its toString method when it's equilateral" in {
    assert(equilateralTriangle.toString.contains(TriangleType.EQUILATERAL.toString))

  }

  it should "contain ISOSCELES in its toString method when it's isosceles" in {
    assert(isoscelesTriangle.toString.contains(TriangleType.ISOSCELES.toString))
  }

  it should "contain SCALENE in its toString method when it's scalene" in {
    assert(scaleneTriangle.toString.contains(TriangleType.SCALENE.toString))
  }

  it should "throw a TriangleException when initialized with at least one null parameter" in {
    assertThrows[TriangleException] {
      Triangle.buildFromStrings("1.0", null, "5.0").get
    }
  }

  it should "throw a TriangleException when initialized with at least one empty parameter" in {
    assertThrows[TriangleException] {
      Triangle.buildFromStrings("1.0", "", "5.0").get
    }
  }

  it should "throw a TriangleException when initialized with at least one bad formatted length" in {
    assertThrows[TriangleException] {
      Triangle.buildFromStrings("1.0", "4e", "5.0").get
    }
  }
}
