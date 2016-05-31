package snakefish.crypto
package cipher.historical

import scala.collection.mutable.Map
import scala.collection.mutable.ArrayBuffer

object Adfgx {

  private val ADFGX = Array('A', 'D', 'F', 'G', 'X')

  def encode(data: CharSequence, key: CharSequence, square: PolybiusSquare): Array[Char]= {
    val polibiusMap = Map[Char, (Char, Char)]()

    for(i <- 0 until square.colsCount; j <- 0 until square.rowsCount) {
      polibiusMap += square(i)(j) -> (ADFGX.charAt(i) -> ADFGX.charAt(j))
    }
    square.substitutes.foreach { case (k, v) =>
      polibiusMap.get(v).foreach { s =>
        polibiusMap += k -> s
      }
    }

    val substitutions = ArrayBuffer[(Char, Char)]()
    for (i <- 0 until data.length) {
      polibiusMap.get(data.charAt(i)).foreach { x =>
        substitutions += x
      }
    }

    

    println(polibiusMap.toList)
    println("substitutions " + substitutions.toList)

    Array()
  }

  def decode(data: CharSequence, key: CharSequence, square: PolybiusSquare): Array[Char] = {
    Array()
  }

}
