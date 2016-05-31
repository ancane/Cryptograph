package snakefish.crypto.cipher.historical

import snakefish.crypto.BaseTest
import PolybiusSquare._
import snakefish.crypto.data.Alphabet

class AdfgxTest extends BaseTest {

  private val square = PolybiusSquare(
    Array(
      Array('F', 'N', 'H', 'E', 'Q'),
      Array('R', 'D', 'Z', 'O', 'C'),
      Array('I', 'S', 'A', 'G', 'U'),
      Array('B', 'V', 'K', 'P', 'W'),
      Array('X', 'M', 'Y', 'T', 'L')
    ),
    Map('J' -> 'I')
  )
  val cipherText = "FFFFFFFFGFDDXGDAXDXGGXGX"

  ".encode" should "correctly encode data using provided parameters" in {
    val encoded = Adfgx.encode("ATTACK AT DAWN", "BATTLE", square)
    encoded must equal (cipherText)
  }

  ".decode" should "correctly decode data using provided parameters" in {
    val plainText = Adfgx.decode(cipherText.toCharArray, "RUSSIAN", square)
    plainText must be ("dynamitewinterpalace".toCharArray)
  }
  
}
