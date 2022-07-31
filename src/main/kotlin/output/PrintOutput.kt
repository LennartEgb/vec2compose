package output

class PrintOutput : Output {
    override fun write(content: String) {
        println("------------------------------")
        println(content)
        println("------------------------------")
    }
}