import axios from 'axios';
import VueI18n from 'vue-i18n';
import { Store } from 'vuex';
import { TranslationStoreModule as translationStore } from '@/shared/config/store/translation-store'
import elementUiLocale from 'element-ui/lib/locale/lang/en'
import ElementLocale from 'element-ui/lib/locale'

export default class TranslationService {
  private store: Store<{}>;
  private i18n: VueI18n;

  constructor(store: Store<{}>, i18n: VueI18n) {
    this.store = store;
    this.i18n = i18n;
  }

  public get internationalization() { return this.i18n }

  public refreshTranslation(newLanguage: string) {
    let currentLanguage = translationStore.language;
    currentLanguage = newLanguage ? newLanguage : 'en';
    if (this.i18n && !this.i18n.messages[currentLanguage]) {
      this.i18n.setLocaleMessage(currentLanguage, {});
      axios.get('i18n/' + currentLanguage + '.json').then(res => {
        if (res.data) {
          const message = {...res.data, ...elementUiLocale};
          this.i18n.setLocaleMessage(currentLanguage, message);
          this.i18n.locale = currentLanguage;
          ElementLocale.i18n((key: string, value: VueI18n.Values) => this.i18n.t(key, value));
          translationStore.setLanguage(currentLanguage);
        }
      });
    } else if (this.i18n) {
      this.i18n.locale = currentLanguage;
      translationStore.setLanguage(currentLanguage)
    }
  }
}
