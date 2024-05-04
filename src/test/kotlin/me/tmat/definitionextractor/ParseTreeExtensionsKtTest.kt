package me.tmat.definitionextractor

import org.jetbrains.kotlin.spec.grammar.tools.parseKotlinCode
import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class ParseTreeExtensionsKtTest {
    private val definition = "fun foo(baz: Int): String { fun bar() {}; return baz.toString() }"
    private val tree = parseKotlinCode(definition)

    @Test
    fun toCode() {
        assertEquals("fun foo ( baz : Int ) : String { fun bar ( ) { } ; return baz . toString ( ) }", tree.children[2].toCode())
        assertEquals("fun", tree.children[2].children[0].children[0].children[0].toCode())
        assertEquals(":", tree.children[2].children[0].children[0].children[3].toCode())
        assertEquals("", tree.children[0].toCode())  // "packageHeader"
    }

    @Test
    fun findDescendant() {
        assertEquals("return", tree.findDescendant("RETURN")?.toCode())
        assertEquals("( )", tree.findDescendant("callSuffix")?.toCode())
        assertNull(tree.findDescendant("nonexistent"))
    }

    @Test
    fun findDescendants() {
        val declarations = tree.findDescendants("functionDeclaration")

        assertEquals(2, declarations.size)
        assertEquals(tree.findDescendant("functionDeclaration"), declarations[0])

        assertEquals(0, tree.findDescendants("nonexistent").size)
    }

    @Test
    fun findNotNestedDescendents() {
        val declarations = tree.findNotNestedDescendents("functionDeclaration")

        assertEquals(1, declarations.size)
        assertEquals(tree.findDescendant("functionDeclaration"), declarations[0])

        assertEquals(0, tree.findDescendants("nonexistent").size)
    }
}