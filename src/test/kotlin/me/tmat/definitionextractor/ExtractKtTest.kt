package me.tmat.definitionextractor

import org.jetbrains.kotlin.spec.grammar.tools.parseKotlinCode
import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class ExtractKtTest {
    @Test
    fun extractFunctions() {
        val tree = parseKotlinCode(
            """
            fun main() {
                fun foo(bar: Int, baz: Int): Int {
                    return bar + baz
                }
            
                println(foo(5, 5))
            }
            """.trimIndent()
        )

        val expected = listOf(
            FunctionDeclaration(
                type = "function",
                name = "main",
                parameters = listOf(),
                returnType = "Unit",
                body = "fun main ( ) { fun foo ( bar : Int , baz : Int ) : Int { return bar + baz } println ( foo ( 5 , 5 ) ) }",
                declarations = listOf(
                    FunctionDeclaration(
                        type = "function",
                        name = "foo",
                        parameters = listOf(
                            FunctionParameter(name = "bar", type = "Int"),
                            FunctionParameter(name = "baz", type = "Int"),
                        ),
                        returnType = "Int",
                        body = "fun foo ( bar : Int , baz : Int ) : Int { return bar + baz }",
                        declarations = listOf()
                    )
                )
            )
        )
        assertEquals(expected, extractFunctions(tree))
    }
}
