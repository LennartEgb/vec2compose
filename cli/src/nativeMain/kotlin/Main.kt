import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    val arguments = Arguments(args)
    startKoin { modules(cliModule) }
    
    val indentation = "    "
    Application(indentation = indentation).run(arguments)
}
