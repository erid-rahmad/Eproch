import { VuexModule, Module, Mutation, Action, getModule } from 'vuex-module-decorators'
import store from '@/shared/config/store'

export interface ITranslationState {
  currentLanguage: string
  languages: object
}

@Module({ dynamic: true, store, name: 'translationStore', namespaced: true })
class TranslationStore extends VuexModule implements ITranslationState {
  currentLanguage = localStorage.getItem('currentLanguage') || 'en';
  languages = {
    en: { name: 'English' },
    in: { name: 'Bahasa Indonesia' }
    // jhipster-needle-i18n-language-key-pipe - JHipster will add/remove languages in this object
  };

  public get language() { return this.currentLanguage }
  public get allLanguages() { return this.languages }

  @Mutation
  private SET_LANGUAGE(language: string) {
    this.currentLanguage = language
    localStorage.setItem('currentLanguage', language)
  }

  @Action
  public async setLanguage(language) {
    this.SET_LANGUAGE(language)
  }
}

export const TranslationStoreModule = getModule(TranslationStore)
