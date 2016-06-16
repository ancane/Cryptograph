package snakefish.crypto
package cipher.historical

import FourSquare._
import PolybiusSquare._

class FourSquareTest extends BaseTest {
  
  private val plaintext  = "Hello stranger"
  private val ciphertext = "ивййлспсвйибрф"
  
  private val cipherSquare = PolybiusSquare(
    Array(Array('а', 'б', 'в', 'г', 'д'),
          Array('е', 'ё', 'ж', 'з', 'и'),
          Array('й', 'к', 'л', 'м', 'н'),
          Array('о', 'п', 'р', 'с', 'т'),
          Array('у', 'ф', 'х', 'ц', 'ч')))                                     
                                           
  private val notFilledLastRowSquare = PolybiusSquare(
    Array(Array('а', 'б', 'в', 'г', 'д'),
          Array('е', 'ё', 'ж', 'з', 'и'),
          Array('й', 'к', 'л', 'м', 'н'),
          Array('о', 'п', 'р', 'с', 'т'),
          Array('у', 'ф', 'х', 'ц'))) 
          
  private val nonStrictCipher = FourSquare(LATIN, cipherSquare, cipherSquare, 'X')
  private val strictCipher = FourSquare(LATIN, cipherSquare, cipherSquare, 'X', true)
  
  ".apply" must "throw an exception if any 2 squares have different size" in {
    an [SquaresDifferentSizeException] must be thrownBy FourSquare(LATIN, RUSSIAN_SHORT, LATIN, 'x')
    an [SquaresDifferentSizeException] must be thrownBy FourSquare(LATIN, LATIN, RUSSIAN_SHORT, 'x')
  }
  
  ".apply" must "throw an exception if any square has not filled last row" in {
    an [WrongSquareSizeException] must be thrownBy FourSquare(notFilledLastRowSquare, cipherSquare, cipherSquare, 'x')
    an [WrongSquareSizeException] must be thrownBy FourSquare(cipherSquare, notFilledLastRowSquare, cipherSquare, 'x')
    an [WrongSquareSizeException] must be thrownBy FourSquare(cipherSquare, cipherSquare, notFilledLastRowSquare, 'x')
  }
  
  ".apply" must "throw an exception if placeholder is missing in plain Polybius square" in {
    an [PlaceholderNotInSquareException] must be thrownBy FourSquare(LATIN, cipherSquare, cipherSquare, 'щ')
  }
  
  ".encrypt" must "correctly encrypt plaintext using provided parameters" in {
    val _ciphertext = nonStrictCipher.encrypt(plaintext)
    _ciphertext must be (ciphertext)
  }
  
  ".decrypt" must "correctly decrypt ciphertext using provided parameters" in {
    val _plaintext = nonStrictCipher.decrypt(ciphertext)
    _plaintext must be ("hellostranger")
  }
  
  ".encrypt(strictMode)" must "throw an exception if plaintext contains char that is missing in plain Polybius square" in {
    val ex = the [DataCharNotInSquareException] thrownBy strictCipher.encrypt(plaintext)
    ex.position must be (plaintext.indexOf(' '))
  }
  
  ".decrypt(strictMode)" must "throw an exception if ciphertext contains char that is missing in relevant cipher Polybius squares" in {
    val ex = the [DataCharNotInSquareException] thrownBy strictCipher.decrypt("абв гд")
    ex.position must be (3)
  }
  
  ".decrypt" must "throw an exception if ciphertext length is odd" in {
    an [OddCiphertextLengthException] must be thrownBy nonStrictCipher.decrypt("и")
  }
  
}
