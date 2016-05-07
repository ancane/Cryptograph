package snakefish.crypto

package object data {
  
  trait Data extends ToFile
  
  case class NotInAlphabetException() extends Exception("Data contains symbols that are missing in provided alphabet")
  
}