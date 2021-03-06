package snakefish.crypto
package cipher.actual.symmetric

import OneTimePad._

class OneTimePadTest extends BaseTest {
  
  private val bytePlaintext  = Array(byte("01010111"), byte("01101001"), byte("01101011"), byte("01101001"))
  private val byteKey        = Array(byte("11110011"), byte("11110011"), byte("11110011"), byte("11110011"), byte("11110011"))
  private val byteCiphertext = Array(byte("10100100"), byte("10011010"), byte("10011000"), byte("10011010"))
  
  private val charPlaintext  = "attackatdawn"
  private val charKey        = "lemonlemonlemon"
  private val charCiphertext = "lxfopvefrnhr"
  
  private val cipher = OneTimePad(Alphabet.ENGLISH)
  
  ".crypt(Array[Byte], Array[Byte])" must "correctly XOR 2 byte arrays" in {
    OneTimePad.crypt(byteKey, bytePlaintext) must be (byteCiphertext)
    OneTimePad.crypt(byteKey, byteCiphertext) must be (bytePlaintext)
  }
  
  ".crypt(Array[Byte], Array[Byte])" must "throw an exception if key length < data length" in {
    an [KeyLengthInsuffisientException] must be thrownBy OneTimePad.crypt(Array(byte("11110011")), bytePlaintext)
  }
  
  ".encrypt(CharSequence, CharSequence, Alphabet)" must "correctly encrypt plaintext using provided key and alphabet" in {
    val _charCiphertext = cipher.encrypt(charKey, charPlaintext)
    _charCiphertext must be (charCiphertext.toCharArray)
  }
  
  ".decrypt(CharSequence, CharSequence, Alphabet)" must "correctly decrypt ciphertext using provided key and alphabet" in {
    val _charPlaintext = cipher.decrypt(charKey, charCiphertext)
    _charPlaintext must be (charPlaintext.toCharArray)
  }
  
  ".encrypt(CharSequence, CharSequence, Alphabet)" must "throw an exception if key length < data length" in {
    an [KeyLengthInsuffisientException] must be thrownBy cipher.encrypt("lemon", charPlaintext)
  }
  
  ".decrypt(CharSequence, CharSequence, Alphabet)" must "throw an exception if key length < data length" in {
    an [KeyLengthInsuffisientException] must be thrownBy cipher.decrypt("lemon", charCiphertext)
  }
  
  ".encrypt(CharSequence, CharSequence, Alphabet)" must "throw an exception if plaintext contains char that is missing in alphabet" in {
    val ex = the [DataCharNotInAlphabetException] thrownBy cipher.encrypt(charKey, " " + charPlaintext)
    ex.position must be (0)
  }
  
  ".decrypt(CharSequence, CharSequence, Alphabet)" must "throw an exception if ciphertext contains char that is missing in alphabet" in {
    val ex = the [DataCharNotInAlphabetException] thrownBy cipher.decrypt(charKey, " " + charCiphertext)
    ex.position must be (0)
  }
  
  ".encrypt(CharSequence, CharSequence, Alphabet)" must "throw an exception if key contains char that is missing in alphabet" in {
    val ex = the [KeyCharNotInAlphabetException] thrownBy cipher.encrypt(" " + charKey, charPlaintext)
    ex.position must be (0)
  }
  
  ".decrypt(CharSequence, CharSequence, Alphabet)" must "throw an exception if key contains char that is missing in alphabet" in {
    val ex = the [KeyCharNotInAlphabetException] thrownBy cipher.decrypt(" " + charKey, charCiphertext)
    ex.position must be (0)
  }
  
}
