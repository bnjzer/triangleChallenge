package org.bnjzer.trianglechallenge

import org.bnjzer.trianglechallenge.parsing.Parsing
import org.bnjzer.trianglechallenge.shapes.Triangle

import scala.util.{Failure, Success}

object TriangleChallenge {
  def usage(): Unit = {
    println(
      s"""Usage: ${getClass.getSimpleName} <length1> <length2> <length3>

          |There must be 3 arguments (integer or decimal), each being a length of the triangle. An argument must contain
          |only digits and at most one point in case of a decimal number.

          |Example: ${getClass.getSimpleName} 3  5.5  1""".stripMargin)
  }

  def error(err: String): Unit = {
    System.err.println(s"Error: $err\n")
    usage()
    System.exit(1)
  }

  /**
    * Calls the parsing function, creates a triangle with the parsed values and
    * finally prints the information about the triangle.
    *
    * In case of a problem with the input arguments, an error message is displayed as well as the correct usage.
    *
    * @param args commandline arguments
    */
  def main(args: Array[String]): Unit = {
    if (args.length != 3) error("you must give 3 arguments")

    Parsing.parseSeqOfDoublesStr(args.toSeq)
      .flatMap(sides => Triangle.buildFromStrings(sides)) match {
        case Success(triangle) => println(triangle)
        case Failure(ex) =>
          ex match {
            case e: IllegalArgumentException => error(s"couldn't create a triangle from these arguments: ${e.getMessage}")
            case _ => error(s"unexpected failure happened: ${ex.getMessage}")
          }
      }
  }
}
