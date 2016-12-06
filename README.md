# Triangle challenge

## Goal

Write a program that will determine the type of a triangle. It should take the lengths of the triangle's three sides as input and return whether the triangle is equilateral, isoscele or scalene.  

First I wrote the reasoning and implementation sections, then all the functions' signature, then all the unit tests and finally I implemented the functions.

## Assumptions

1. So that the program can be used in most use cases, triangle's sides aren't necessarily integers and can have decimals.
2. Program will be launched via commandline with the 3 lengths as arguments and the triangle's type will be printed on the standard output.

## Reasoning

The problem can be divided into 3 main steps:  

1. Parsing the arguments;
2. Comparing the lengths;
3. Printing the triangle's type.  

### 1) Parsing the arguments

We always have to be very cautious when parsing user input and imagine all the strange arguments that could be passed to the program by the user. All the error cases should be handled. In this case, I have to check that the number of arguments is exactly 3 and that they are all valid numbers. In all the program I'll use Scala `Try` to manage the errors.  

Parsing arguments being a very common task, the functions related to it could be placed in an other library, to make it usable by other programs. But for the scope of this problem I'll just put them in a dedicated Sacala package. Also, the functions should be generic in order to be more easily reusable. For instance, the parsing function should be able to parse any number of arguments, not just 3. This way, if in the future I want to parse the arguments for a square for example, I'll be able to use the same function.  

I'll have 2 functions: one that takes a `String` as input and parses it as a number, and one that takes a sequence of `String` as input and calls the previous function for each one. So I'll make sure that there are exactly 3 arguments in the `main`, because this is specific to this program, and then I'll call my generic parsing function with the 3 arguments.

### 2) Comparing the lengths

Right now I just want to check how many sides are equal, but later I might need to add other functionalities, like knowing if the triangle is rectangle. In order to make it easier for the code to evolve and in order to make it fully testable, I'll create a `Triangle` class that will contain all the "intelligence" related to a triangle. Again, in the case of shapes being used by several programs I could put this code in an other library but for now I'll create a Scala package for it.  

Then I have 2 possibilities:  

1. Compare the lengths when the triangle is created and store the result;
2. Compare the lengths only when the triangle's type is requested.  

In the first case the comparison will happen only once, but space is required to store the result. In the second case, no space is required to store the result but if the triangle's type is requested a lot of times, the same comparison will be performed again and again. The choice depends on the use cases of a triangle. I can assume than usually a triangle is created only once and then can be used for different purposes, with computations that can request the triangle's type. So I'll go with the first option.

### 3) Printing the triangle's type

To make the program more easily testable I'll override the `toString` function of `Triangle` so that it displays the type of a triangle. I'll also make this function more generic and print other information as well, like the sides' length.

## Implementation

### 1) Triangle's lengths

The key point now is to determine how I will handle triangle's lengths in all the program : with strings representing numbers or with actual numbers (`Double`). Both options have advantages and drawbacks.  

Storing the lengths as `Double` could be the logical choice given that the lengths are numbers. It would have 2 main advantages : first if I later need to do some computation with the lengths, like squaring them, it will be easy. And secondly I could directly use a Scala built-in function to parse the arguments from `String` to `Double`. However there would be one main drawback : it implies to set up a limitation on the numbers passed as arguments, because a `Double` cannot store exactly huuuge numbers or numbers with a looot of decimals. In this case the exact value would be approximated when parsing from `String` to `Double` by Scala and lengths that are close but different could have the same value once parsed and be considered equal whereas they are not.  

Storing the lengths as `String` however has the advantage that it doesn't require any limitation on the triangles's lengths. Even if for a reason sides have billions of billions of billions of decimals, I'll be able to handle it. But the main drawback with this approach is that `"1"`, `"1.0"` and `"001.00"` are not equal, so the string format must be managed manually.  

Given that in my case my only need is to compare the lengths, I'll choose the solution that has no limitation and I will store the lengths as `String`. If I later need to add some functionality to the program that requires computation with numbers, I'll need to study the trade-offs of each implementation again.

### 2) Companion object

As I said before, this code is designed to be reusable and some parts could be placed in separate libraries, like the `Triangle` class. In order to be sure that an other developer can't instantiate a new triangle with bad parameters, I choose to make the constructor `private` and make the use of a companion object compulsory to build a triangle. This way, I can manage errors and return `Failure` if at least one of the parameter is wrong. What's more, it makes it possible to have several "constructors" with different parameter types (i.e `Double`) for the same class, by adding other functions to the companion object.  

### 3) `null` handling

Although `null` is evil and shouldn't exist in functional programming, as I said before I don't know what people can do with the librairies so I will handle `null` cases.

## Usage

These commands have to executed in the directory containing the file `build.sbt`.

### Running the program

```
$ sbt "run 1 2 3"
```

Quotation marks are important!

### Testing

```
$ sbt test
```

There should be no red.
