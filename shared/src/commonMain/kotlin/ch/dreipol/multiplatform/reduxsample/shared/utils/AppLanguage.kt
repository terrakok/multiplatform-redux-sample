package ch.dreipol.multiplatform.reduxsample.shared.utils

enum class AppLanguage(val shortName: String, val descriptionKey: String) {
    GERMAN("de", "settings_language_de"), ENGLISH("en", "settings_language_en");

    companion object {
        fun fromValue(shortName: String?): AppLanguage {
            return if (shortName.equals(GERMAN.shortName, ignoreCase = true)) {
                GERMAN
            } else {
                ENGLISH
            }
        }

        fun fromSettingsOrDefault(): AppLanguage {
            return SettingsHelper.getLanguage()?.let { fromValue(it) } ?: fromLocale()
        }
    }
}

expect fun AppLanguage.Companion.fromLocale(): AppLanguage