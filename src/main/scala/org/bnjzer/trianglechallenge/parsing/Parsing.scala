package org.bnjzer.trianglechallenge.parsing

import java.util.StringJoiner

import scala.util.matching.Regex
import scala.util.{Failure, Success, Try}

/**
  * Library containing generic parsing functions
  */
object Parsing {

  /**
    * Takes a string representing a number and formats it correctly. A well formatted number
    * has no leading zero and always has a decimal part, without trailing zeros.
    *
    * For example, 005 as input would return 5.0 as output and 1.2300 would return 1.23.
    *
    * If the input contains something that is not a digit or a point or if it contains several points,
    * a [[Failure]] is returned.
    *
    * @param doubleStr String representing a number
    * @return [[Try]] with well formatted string representation of the number if it could be parsed
    *         or an exception otherwise
    */
  def parseDoubleStr(doubleStr: String): Try[String] = {
    if (doubleStr == null || doubleStr.isEmpty)
      Failure(new ParsingException("The input string is null or empty"))
    else {
      // We check that the string has a good format using a regex. At this point We know that the string is not empty.
      // "." would be a valid input and would become 0.0
      val doubleFormat = new Regex("^\\d*.?\\d*$")

      doubleStr match {
        case doubleFormat() =>
          val result = new StringJoiner("")

          // remove leading zero and add one if the number starts with a point
          if (doubleStr.charAt(0) == '.')
            result.add("0" + doubleStr)
          else
            result.add(doubleStr.replaceFirst("^0+(?!$)", ""))

          // If the number is an integer we add ".0", else if it ends with a point we just add a zero and
          // otherwise we remove trailing zeros.
          if (!doubleStr.contains(".")) {
            result.add(".0")
            Success(result.toString)
          } else if (doubleStr.last == '.') {
            result.add("0")
            Success(result.toString)
          } else
            Success(result.toString.replaceAll("(\\.\\d)0+$", "$1"))
        case _ => Failure(new ParsingException(s"$doubleStr is not a valid number"))
      }
    }
  }

  /**
    * Calls [[parseDoubleStr]] for each string contained in the sequence and returns a sequence
    * with all the numbers well formatted.
    *
    * If all the strings can't be correctly parsed, a [[Failure]] is returned.
    *
    * @param seqOfDoublesStr Sequence containing the numbers we want to parse
    * @return [[Try]] with all the values well formatted or an exception if the strings can't be well parsed
    */
  def parseSeqOfDoublesStr(seqOfDoublesStr: Seq[String]): Try[Seq[String]] = {
    if (seqOfDoublesStr == null)
      Failure(new ParsingException("Sequence of strings to parse is null"))
    else
      Try(seqOfDoublesStr.map(parseDoubleStr).map(_.get))
  }
}
