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
  * Companion object used to instantiate triangles with error handling for the parameters
  */
object Triangle {

  /**
    * Creates a triangle from string lengths.
    *
    * @param side1 Length of the first side
    * @param side2 Length of the second side
    * @param side3 Length of the third side
    * @return [[Try]] with the triangle if it was successfully built or an exception otherwise
    */
  def buildFromStrings(side1: String, side2: String, side3: String): Try[Triangle] = {
    // check if one of the side is null or empty (avoids to compute the regex if it's empty)
    if (side1 == null || side1.isEmpty || side2 == null || side2.isEmpty || side3 == null || side3.isEmpty)
      Failure(new TriangleException("At least one of the triangle's lengths is null or empty"))
    else {
      // check if all the lengths are well formated
      val lengthFormat = "^\\d+.?\\d+$".r.pattern
      if (!lengthFormat.matcher(side1).matches() || !lengthFormat.matcher(side2).matches() || !lengthFormat.matcher(side3).matches())
        Failure(new TriangleException("At least one of the triangle's lengths has not a correct format"))
      else
        Success(new Triangle(side1, side2, side3))
    }
  }
}