package helper;

import java.util.Arrays;
import java.util.List;

public enum Lang {
    /**
     * ko<->en, ko<->zh-CN, ko<->zh-TW, ko<->es, ko<->fr, ko<->vi, ko<->th, ko<->id, en<->ja, en<->fr 조합만 가능
     *
     * Korean
     * English
     * Japanese
     * Simplified Chinese
     * Traditional Chinese
     * Spanish
     * French
     * Vietnamese
     * Teguk
     * Indonesian
     */

    KO(0, "ko", "Korean", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)),
    EN(1, "en", "English", Arrays.asList(0, 2, 6)),
    JA(2, "ja", "Japanese", Arrays.asList(0, 1)),
    ZH_CN(3, "zh-CN", "Simplified Chinese", Arrays.asList(0)),
    ZH_TW(4, "zh-TW", "Traditional Chinese", Arrays.asList(0)),
    ES(5, "es", "Spanish", Arrays.asList(0)),
    FR(6, "fr", "French", Arrays.asList(0, 1)),
    VI(7, "vi", "Vietnamese", Arrays.asList(0)),
    TH(8, "th", "Teguk", Arrays.asList(0)),
    ID(9, "id", "Indonesian", Arrays.asList(0));

    private int idx;
    private String code;
    private String language;
    private List<Integer> translatableLanguageIndexies;

    Lang(int idx, String code, String language, List<Integer> translatableLanguageIndexies) {
        this.idx = idx;
        this.code = code;
        this.language = language;
        this.translatableLanguageIndexies = translatableLanguageIndexies;
    }

    public static Lang getInstance(int idx) {
        Lang rtn = null;
        for (Lang lang : Lang.values()) {
            if (lang.getIdx() == idx) {
                rtn = lang;
                break;
            }
        }
        return rtn;
    }

    public boolean isTranslatable(Lang lang) {
        return translatableLanguageIndexies.contains(lang.getIdx());
    }

    public int getIdx() {
        return idx;
    }

    public String getCode() {
        return code;
    }

    public String getLanguage() {
        return language;
    }
}
