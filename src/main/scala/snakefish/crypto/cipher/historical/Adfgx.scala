package snakefish.crypto
package cipher.historical

import scala.collection.mutable.Map
import scala.collection.mutable.ListMap
import scala.collection.mutable.ArrayBuffer

object Adfgx {

  private val ADFGX = "ADFGX"

  def encode(data: CharSequence, key: CharSequence, square: PolybiusSquare): Array[Char] = {
    val polibiusMap = Map[Char, String]()

    for(i <- 0 until square.colsCount; j <- 0 until square.rowsCount) {
      polibiusMap += square(i)(j) -> s"${ADFGX.charAt(i)}${ADFGX.charAt(j)}"
    }
    square.substitutes.foreach { case (k, v) =>
      polibiusMap.get(v).foreach { s =>
        polibiusMap += k -> s
      }
    }

    val substitutions = new StringBuilder()
    for (i <- 0 until data.length) {
      polibiusMap.get(data.charAt(i)).foreach { x =>
        substitutions.append(x)
      }
    }
    val split = splitAt(substitutions.toString, key.length)
    val transposed = new Array[StringBuffer](key.length)
    val indexedKey = key.toString.zipWithIndex
    indexedKey.foreach { case (ch, i) =>
      val arr = Option(transposed(i)).getOrElse(new StringBuffer())
      for (j <- 0 until split.length) {
        if (i < split(j).length) {
          transposed(i) = arr append split(j)(i)
        }
      }
    }

    indexedKey
      .sortBy(_._1)
      .flatMap(x => transposed(x._2).toString)
      .toArray
  }

  def decode(data: CharSequence, key: CharSequence, square: PolybiusSquare): Array[Char] = {
    new Array[Char](1)
  }

}
