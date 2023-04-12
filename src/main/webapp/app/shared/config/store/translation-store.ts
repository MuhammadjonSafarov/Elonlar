import { Module } from 'vuex';

export const translationStore: Module<any, any> = {
  state: {
    currentLanguage: localStorage.getItem('currentLanguage') || 'uz-Latn-uz',
    languages: {
      en: { name: 'English' },
      ru: { name: 'Русский' },
      'uz-Cyrl-uz': { name: 'Ўзбекча' },
      'uz-Latn-uz': { name: 'O`zbekcha' },
      // jhipster-needle-i18n-language-key-pipe - JHipster will add/remove languages in this object
    },
  },
  getters: {
    currentLanguage: state => state.currentLanguage,
    languages: state => state.languages,
  },
  mutations: {
    currentLanguage(state, newLanguage) {
      state.currentLanguage = newLanguage;
      localStorage.setItem('currentLanguage', newLanguage);
    },
  },
};
