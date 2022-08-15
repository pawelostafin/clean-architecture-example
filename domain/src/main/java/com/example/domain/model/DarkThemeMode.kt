package com.example.domain.model

enum class DarkThemeMode {
    ENABLED,
    DISABLED,
    AUTO;

    fun toRawValue(): String {
        return when (this) {
            ENABLED -> ENABLED_RAW
            DISABLED -> DISABLED_RAW
            AUTO -> AUTO_RAW
        }
    }

    companion object {
        private const val ENABLED_RAW = "ENABLED"
        private const val DISABLED_RAW = "DISABLED"
        private const val AUTO_RAW = "AUTO"

        fun fromRawValue(rawValue: String): DarkThemeMode? {
            return values().firstOrNull { it.toRawValue() == rawValue }
        }

    }

}