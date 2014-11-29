package net.RevTut.Skywars.libraries.language;

/**
 * Languages Enum.
 *
 * <P>All the languages available in Minecraft.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public enum Language {

    /**
     * American Language
     */
    AMERICAN("American", "en_US"),

    /**
     * Afrikaans Language
     */
    AFRIKAANS("Afrikaans", "af_ZA"),

    /**
     * Arabic Language
     */
    ARABIC("العربية", "ar_SA"),

    /**
     * Argentino Spanish Language
     */
    ARGENTINEAN_SPANISH("Español Argentino", "es_AR"),

    /**
     * Armenian Language
     */
    ARMENIAN("Հայերեն", "hy_AM"),

    /**
     * Australian English Language
     */
    AUSTRALIAN_ENGLISH("Australian English", "en_AU"),

    /**
     * Bulgarian Language
     */
    BULGARIAN("Bulgarian", "bg_BG"),

    /**
     * Canadan English Language
     */
    CANADIAN_ENGLISH("Canadian English", "en_CA"),

    /**
     * Spanish Catalan Language
     */
    CATALAN("Català", "ca_ES"),

    /**
     * Croatian Language
     */
    CROATIAN("Hrvatski", "hr_HR"),

    /**
     * Cymraeg Language
     */
    CYMRAEG("Cymraeg", "cy_GB"),

    /**
     * Czech Language
     */
    CZECH("Čeština", "cs_CZ"),

    /**
     * Danish Language
     */
    DANISH("Dansk", "da_DK"),

    /**
     * Dutch Language
     */
    DUTCH("Nederlands", "nl_NL"),

    /**
     * English Language
     */
    ENGLISH("English", "en_GB"),

    /**
     * Esperanto Language
     */
    ESPERANTO("Esperanto", "eo_EO"),

    /**
     * Estonian Language
     */
    ESTONIAN("Eesti", "et_EE"),

    /**
     * Euskara Language
     */
    EUSKARA("Euskara", "eu_ES"),

    /**
     * Finnish Language
     */
    FINNISH("Suomi", "fi_FI"),

    /**
     * French Language
     */
    FRENCH("Français", "fr_FR"),

    /**
     * Canada French Language
     */
    FRENCH_CA("Français", "fr_CA"),

    /**
     * Gaeilge Language
     */
    GAEILGE("Gaeilge", "ga_IE"),

    /**
     * Galician Spanish Language
     */
    GALICIAN("Galego", "gl_ES"),

    /**
     * Georgian Language
     */
    GEORGIAN("Georgian", "ka_GE"),

    /**
     * German Language
     */
    GERMAN("Deutsch", "de_DE"),

    /**
     * Greek Language
     */
    GREEK("Ελληνικά", "el_GR"),

    /**
     * Hebrew Language
     */
    HEBREW("עברית", "he_IL"),

    /**
     * Hungarian Language
     */
    HUNGARIAN("Magyar", "hu_HU"),


    /**
     * Icelandic Language
     */
    ICELANDIC("Icelandic", "is_IS"),

    /**
     * Indian Language
     */
    INDIAN("Indian", "hi_IN"),

    /**
     * Indonesia Language
     */
    INDONESIA("Bahasa Indonesia", "id_ID"),

    /**
     * Italian Language
     */
    ITALIAN("Italiano", "it_IT"),

    /**
     * Japanese Language
     */
    JAPANESE("日本語", "ja_JP"),

    /**
     * Kernewek Language
     */
    KERNEWEK("Kernewek", "kw_GB"),

    /**
     * Korean Language
     */
    KOREAN("한국어", "ko_KR"),

    /**
     * Kyrgyzstan Language
     */
    KYRGYZSTAN("Kyrgyzstan", "ky_KG"),

    /**
     * Letzebuergesch Language
     */
    LETZEBUERGESCH("Lëtzebuergesch", "lb_LU"),

    /**
     * Latin Language
     */
    LINGUA_LATINA("Lingua latina", "la_LA"),

    /**
     * Lithuanian Language
     */
    LITHUANIAN("Lietuvių", "lt_LT"),

    /**
     * Latvian Language
     */
    LATVIAN("Latviešu", "lv_LV"),

    /**
     * Malaysia Language
     */
    MALAY_MY("Bahasa Melayu", "ms_MY"),

    /**
     * Malti Language
     */
    MALTI("Malti", "mt_MT"),

    /**
     * Mexican Spanish Language
     */
    MEXICO_SPANISH("Español México", "es_MX"),

    /**
     * New Zealand Language
     */
    NEW_ZEALAND("Bahasa Melayu", "mi_NZ"),

    /**
     * Norway Language
     */
    NORWAY("Norsk", "nb_NO"),

    /**
     * Norway Language
     */
    NORWAY1("Norway", "nn_NO"),

    /**
     * Norwegian Language
     */
    NORWEGIAN("Norwegian", "no_NO"),

    /**
     * Occitan French Language
     */
    OCCITAN("Occitan", "oc_FR"),

    /**
     * Persian Language
     */
    PERSIAN("زبان انگلیسی", "fa_IR"),

    /**
     * Pirate English Language
     */
    PIRATE_SPEAK("Pirate Speak", "en_PT"),

    /**
     * Polish Language
     */
    POLISH("Polski", "pl_PL"),

    /**
     * Brazil Portuguese Language
     */
    PORTUGUESE_BR("Português", "pt_BR"),

    /**
     * Portuguese Language
     */
    PORTUGUESE_PT("Português", "pt_PT"),

    /**
     * Quenya Language
     */
    QUENYA("Quenya", "qya_AA"),

    /**
     * Romanian Language
     */
    ROMANIAN("Română", "ro_RO"),

    /**
     * Russian Language
     */
    RUSSIAN("Russian", "ru_RU"),

    /**
     * Serbian Language
     */
    SERBIAN("Serbian", "sr_SP"),

    /**
     * Slovakia Language
     */
    SLOVAKIA("Slovakia", "sk_SK"),

    /**
     * Slovenian Language
     */
    SLOVENIAN("Slovenian", "sl_SI"),

    /**
     * Simplified Chinese Language
     */
    SIMPLIFIED_CHINESE("简体中文", "zh_CN"),

    /**
     * Spanish Language
     */
    SPANISH("Español", "es_ES"),

    /**
     * Swedish Language
     */
    SWEDISH("Svenska", "sv_SE"),

    /**
     * Tagalog Language
     */
    TAGALOG("Tagalog", "fil_PH"),

    /**
     * Thailand Language
     */
    THAILAND("ภาษาไทย", "th_TH"),

    /**
     * Traditional Chinese Language
     */
    TRADITIONAL_CHINESE("Traditional Chinese", "zh_TW"),

    /**
     * Turkish Language
     */
    TURKISH("Türkçe", "tr_TR"),

    /**
     * Ukrainian Language
     */
    UKRAINIAN("Ukrainian", "uk_UA"),

    /**
     * Uruguay Spanish Language
     */
    URUGUAY_SPANISH("Español Uruguay", "es_UY"),

    /**
     * Venezuela Spanish Language
     */
    VENEZUELA_SPANISH("Español Venezuela", "es_VE"),

    /**
     * Vietnamese Language
     */
    VIETNAMESE("Tiếng Việt", "vi_VI");

    /**
     * Name of the language
     */
    private final String name;

    /**
     * Code of the language
     */
    private final String code;

    /**
     * Constructor of Language
     *
     * @param name name of the language
     * @param code code of the language
     */
    Language(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /**
     * Get the name of the language
     *
     * @return name of the language
     */
    public String getName() {
        return name;
    }

    /**
     * Get the code of the language
     *
     * @return code of the language
     */
    public String getCode() {
        return code;
    }
}