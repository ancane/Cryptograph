package snakefish.crypto.test

import java.io.File
import org.scalatest.FlatSpec
import org.scalatest.MustMatchers
import org.scalatest.BeforeAndAfterEach
import org.scalatest.BeforeAndAfter

class BaseTest extends FlatSpec with MustMatchers with BeforeAndAfter {
  
  val TEST_FILE = """D:\test"""
  val TEST_FILE2 = """D:\test2"""
  val NON_DEFAULT_CHARSET = "KOI8_R"
  
  after {
    new File(TEST_FILE).delete()
    new File(TEST_FILE2).delete()
  }
  
}
