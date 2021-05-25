import Vue from 'vue';
import { format, parseISO } from 'date-fns';
import { TranslationStoreModule as tranlationStore } from '@/shared/config/store/translation-store';

export const DATE_FORMAT = 'yyyy-MM-dd';
export const DATE_TIME_FORMAT = 'yyyy-MM-dd HH:mm';
export const DATE_TIME_SECONDS_FORMAT = 'yyyy-MM-dd HH:mm:ss';

export const DATE_TIME_LONG_FORMAT = "yyyy-MM-dd'T'HH:mm";
export const INSTANT_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
export const ZONED_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXXXX";

export function initFilters() {
  Vue.filter('formatDate', function(value: string, dateOnly: boolean) {
    if (value) {
      return format(parseISO(value), dateOnly ? DATE_FORMAT : DATE_TIME_FORMAT);
    }
    return '';
  });
  Vue.filter('formatDateTime', function(value: string, seconds: boolean) {
    if (value) {
      return format(parseISO(value), seconds ? DATE_TIME_FORMAT : DATE_TIME_SECONDS_FORMAT);
    }
    return '';
  });
  Vue.filter('formatMillis', function(value) {
    if (value) {
      return format(new Date(value), DATE_TIME_FORMAT);
    }
    return '';
  });
  Vue.filter('formatCurrency', function(value: number, locales?: string | string[], defaultValue?: number) {
    const formatter = new Intl.NumberFormat(locales || tranlationStore.currentLanguage, {
      minimumFractionDigits: 2
    });

    if (value !== void 0) {
      return formatter.format(value);
    }

    return defaultValue || null;
  });
}
