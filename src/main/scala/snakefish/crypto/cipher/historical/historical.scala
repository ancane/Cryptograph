package snakefish.crypto.cipher

package object historical {
  
  case class OddCiphertextLengthException()
      extends RuntimeException("Ciphertext length must be even")
  
  case class PlaceholderNotInSquareException()
      extends RuntimeException("Placeholder char is missing in Polybius square")
  
}
