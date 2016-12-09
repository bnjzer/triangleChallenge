package org.bnjzer.trianglechallenge.parsing

import org.bnjzer.trianglechallenge.UnitSpec

class ParsingSpec extends UnitSpec {
  "Parsing 1 double (as string)" should "return 1 well formatted number" in {
    Parsing.parseDoubleStr("65.34").get shouldBe "65.34"
    Parsing.parseDoubleStr("3.0").get shouldBe "3.0"
  }

  it should "remove leading zeros" in {
    Parsing.parseDoubleStr("0003").get shouldBe "3.0"
  }

  it should "remove trailing decimal zeros" in {
    Parsing.parseDoubleStr("5.00000").get shouldBe "5.0"
  }

  it should "add a point and a zero if the number is an integer" in {
    Parsing.parseDoubleStr("3").get shouldBe "3.0"
  }

  it should "add a leading zero if the number starts with a point" in {
    Parsing.parseDoubleStr(".17").get shouldBe "0.17"
  }

  it should "return a Failure with a IllegalArgumentException when the number contains other than a digit or a point" in {
    assertThrows[IllegalArgumentException] {
      Parsing.parseDoubleStr("65y.4").get
    }
  }

  it should "return a Failure with a IllegalArgumentException when the number contains 2 points" in {
    assertThrows[IllegalArgumentException] {
      Parsing.parseDoubleStr("54.87.87").get
    }
  }

  it should "return a Failure with a IllegalArgumentException when the number is null" in {
    assertThrows[IllegalArgumentException] {
      Parsing.parseDoubleStr(null).get
    }
  }

  it should "return a Failure with a IllegalArgumentException when the number is empty" in {
    assertThrows[IllegalArgumentException] {
      Parsing.parseDoubleStr("").get
    }
  }

  "Parsing N doubles (as string)" should "return N well formatted numbers" in {
    val result = Parsing.parseSeqOfDoublesStr(Seq("1", "2.0", ".34"))
    assert(result.isSuccess)
    assert(result.get.head == "1.0" && result.get(1) == "2.0" && result.get(2) == "0.34")
  }

  it should "return a Failure with a IllegalArgumentException if at least one of the string is not a valid number" in {
    assertThrows[IllegalArgumentException] {
      Parsing.parseSeqOfDoublesStr(Seq("1", "e2.")).get
    }
  }

  it should "return a Failure with a IllegalArgumentException if the sequence is null" in {
    assertThrows[IllegalArgumentException] {
      Parsing.parseSeqOfDoublesStr(null).get
    }
  }
}
