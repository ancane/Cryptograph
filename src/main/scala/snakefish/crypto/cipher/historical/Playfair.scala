package snakefish.crypto
package cipher.historical

import Playfair._
import PolybiusSquare._
import scala.collection.mutable.ArrayBuffer

object Playfair {
  
  @throws(classOf[WrongSquareSizeException])
  @throws(classOf[PlaceholderNotInSquareException])
  def apply(square: PolybiusSquare, placeholder: Char, strictMode: Boolean = false) = 
    new Playfair(square, placeholder, strictMode)
}

class Playfair(val square: PolybiusSquare, private val _placeholder: Char, val strictMode: Boolean = false) {
  
  val placeholder = _placeholder.toLower
  
  if (!square.lastRowFilled)
    throw new WrongSquareSizeException("Last row of Polybius square is not filled")
  
  if (!square.contains(placeholder))
    throw new PlaceholderNotInSquareException()
  
  @throws(classOf[DataCharNotInSquareException])
  def encrypt(plaintext: CharSequence): String = {
    val inSquareChars = filter(plaintext, square, strictMode)
    val dataLen = inSquareChars.length
    val charsToComp = new StringBuilder(dataLen)
    
    var i = 0
    while (i < dataLen) {
      val ch1 = inSquareChars(i)
      charsToComp.append(ch1)
      if (i + 1 < dataLen) {
        val ch2 = inSquareChars(i + 1)
        if (ch1 == ch2) {
          charsToComp.append(placeholder)
        } else {
          charsToComp.append(ch2)
          i += 1
        }
      } else {
        charsToComp.append(placeholder)
      }
      i += 1
    }
    
    crypt(charsToComp, addByModulo).toString
  }
  
  @throws(classOf[OddCiphertextLengthException])
  @throws(classOf[DataCharNotInSquareException])
  def decrypt(ciphertext: CharSequence): String = {
    val charsToComp = filter(ciphertext, square, strictMode)
    
    if (charsToComp.length % 2 != 0)
      throw new OddCiphertextLengthException()
    
    val decrypted = crypt(charsToComp, subtractByModulo)
    val result = new StringBuilder(decrypted.length)

    for (i <- 0 until decrypted.length by 2) {
      val ch1 = decrypted(i)
      val ch2 = decrypted(i + 1)
      
      result += ch1
      
      val isPlaceholder = ch2 == placeholder && 
                          i + 2 < decrypted.length && 
                          decrypted(i + 2) == ch1       
      
      if (!isPlaceholder)
        result += ch2
    }
    
    result.toString
  }
  
  private def crypt(data: CharSequence, sameRowColFunc: (Int, Int, Int) => Int): StringBuilder = {
    val result = new StringBuilder(data.length)
    
    for (i <- 0 until data.length by 2) {
      val (row1, col1) = square.coords(data.charAt(i)).get
      val (row2, col2) = square.coords(data.charAt(i + 1)).get
      
      if (row1 == row2) {
        val col1New = sameRowColFunc(col1, 1, square.colsCount)
        val col2New = sameRowColFunc(col2, 1, square.colsCount)
        result += square(row1)(col1New)
        result += square(row2)(col2New)
      } else if (col1 == col2) {
        val row1New = sameRowColFunc(row1, 1, square.rowsCount)
        val row2New = sameRowColFunc(row2, 1, square.rowsCount)
        result += square(row1New)(col1)
        result += square(row2New)(col2)
      } else {
        result += square(row1)(col2)
        result += square(row2)(col1)
      }
    }
    
    result
  }
  
}
