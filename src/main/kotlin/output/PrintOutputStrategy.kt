package output

class PrintOutputStrategy : OutputStrategy {
  override fun write(content: String) {
    println(content)
  }
}
