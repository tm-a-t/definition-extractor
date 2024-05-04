package me.tmat.definitionextractor

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.kotlin.spec.grammar.tools.KotlinParseTree

/**
 * Returns the JSON representation of function declarations in the given tree.
 */
fun extract(parseTree: KotlinParseTree): String {
    val declaration = FileDeclaration(declarations = extractFunctions(parseTree))
    return Json.encodeToString(declaration)
}

/**
 * Builds function declarations from the given tree.
 */
fun extractFunctions(parseTree: KotlinParseTree): List<FunctionDeclaration> {
    val functions = parseTree.findNotNestedDescendents("functionDeclaration")
    return functions.map { function ->
        FunctionDeclaration(
            type = "function",
            name = function.findDescendant("Identifier")?.text!!,  // first identifier must be function identifier?
            parameters = function.findDescendant("functionValueParameters")!!.findDescendants("functionValueParameter")
                .map {
                    FunctionParameter(
                        it.findDescendant("simpleIdentifier")!!.toCode(),
                        it.findDescendant("type")!!.toCode(),
                    )
                },
            returnType = function.children.find { it.name == "type" }?.toCode() ?: "Unit",
            body = function.toCode(),
            declarations = extractFunctions(function.findDescendant("functionBody")!!),
        )
    }
}

/**
 * Top-level serialization structure.
 */
@Serializable
data class FileDeclaration(val declarations: List<FunctionDeclaration>)

@Serializable
data class FunctionDeclaration(
    val type: String,
    val name: String,
    val parameters: List<FunctionParameter>,
    val returnType: String,
    val body: String,
    val declarations: List<FunctionDeclaration>,
)

@Serializable
data class FunctionParameter(val name: String, val type: String)
