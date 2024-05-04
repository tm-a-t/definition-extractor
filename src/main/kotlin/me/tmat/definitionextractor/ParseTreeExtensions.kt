package me.tmat.definitionextractor

import org.jetbrains.kotlin.spec.grammar.tools.KotlinParseTree

/**
 * Concatenates all tokens in the subtree.
 */
fun KotlinParseTree.toCode(): String {
    return this.text ?: this.children
            .map { it.toCode() }
            .filter { it.isNotBlank() }
            .joinToString(separator = " ")
}

/**
 * Finds a descendant with the given name.
 */
fun KotlinParseTree.findDescendant(name: String): KotlinParseTree? {
    return if (this.name == name) {
        this
    } else {
        this.children.firstNotNullOfOrNull { it.findDescendant(name) }
    }
}

/**
 * Finds all descendants with the given name.
 */
fun KotlinParseTree.findDescendants(name: String): List<KotlinParseTree> {
    val nextDescendants = this.children
        .map { it.findDescendants(name) }
        .flatten()

    return if (this.name == name) listOf(this) + nextDescendants else nextDescendants
}

/**
 * Finds the descendants with the given name which are not inside other descendants with this name.
 */
fun KotlinParseTree.findNotNestedDescendents(name: String): List<KotlinParseTree> {
    return if (this.name == name) {
        listOf(this)
    } else {
        this.children
            .map { it.findNotNestedDescendents(name) }
            .flatten()
    }
}
