package com.ghozimahdi.jsontodart.ui

import com.google.gson.JsonElement

object JsonToDartConverter {
    fun generateDartClass(json: JsonElement, className: String): String {
        val fields = mutableListOf<String>()

        if (json.isJsonObject) {
            val jsonObject = json.asJsonObject
            jsonObject.entrySet().forEach { entry ->
                val type = when {
                    entry.value.isJsonPrimitive -> "String"
                    entry.value.isJsonArray -> "List<dynamic>"
                    entry.value.isJsonObject -> "Map<String, dynamic>"
                    else -> "dynamic"
                }
                fields.add("  final $type ${entry.key};")
            }
        }

        return """
        class $className {
        ${fields.joinToString("\n")}
        
          $className({${fields.joinToString(", ") { "required this.${it.split(" ").last()}" }}});

          factory $className.fromJson(Map<String, dynamic> json) {
            return $className(
              ${fields.joinToString(",\n              ") { "${it.split(" ").last()}: json['${it.split(" ").last()}']" }}
            );
          }

          Map<String, dynamic> toJson() {
            return {
              ${fields.joinToString(",\n              ") { "'${it.split(" ").last()}': ${it.split(" ").last()}" }}
            };
          }
        }
        """.trimIndent()
    }
}