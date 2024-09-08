import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    startKoin { modules(cliModule) }
    Application(indentation = "    ")
        .run(Arguments(args))
}
