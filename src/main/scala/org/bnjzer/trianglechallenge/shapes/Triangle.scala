package org.bnjzer.trianglechallenge.shapes

import scala.util.{Failure, Success, Try}

/**
  * A triangle can't be instantiated without using the companion object
  */
class Triangle private(side1: String, side2: String, side3: String) {
  val triangleType = {
    if (!side1.equals(side2) && !side2.equals(side3) && !side1.equals(side3))
      TriangleType.SCALENE
    else if (side1 == side2 && side2 == side3)
      TriangleType.EQUILATERAL
    else
      TriangleType.ISOSCELES
  }

  override def toString = s"Triangle $triangleType with lengths $side1 $side2 $side3"
}

/**
  * Companion object used to instantiate triangles with error handling for the parameters.
  */
object Triangle {

  /**
    * Creates a triangle from string lengths.
    *
    * @param sides Sequence with the 3 lengths of the triangle
    * @return [[Try]] with the triangle if it was successfully built or a [[Failure]] otherwise
    */
  def buildFromStrings(sides: Seq[String]): Try[Triangle] = {
    // checks that all the 3 sides are correctly defined
    if (sides.size != 3 || !sides.forall(s => Option(s).isDefined && s.nonEmpty && "^\\d+.?\\d+$".r.pattern.matcher(s).matches()))
      Failure(new IllegalArgumentException("There aren't 3 valid lengths"))
    else
      Try(new Triangle(sides.head, sides(1), sides(2)))
  }
}
