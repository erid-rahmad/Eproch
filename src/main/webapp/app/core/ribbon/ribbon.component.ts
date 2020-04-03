import { Component, Vue } from 'vue-property-decorator';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';

@Component
export default class Ribbon extends Vue {
  public get ribbonEnv(): string {
    return accountStore.ribbonOnProfiles;
  }

  public get ribbonEnabled(): boolean {
    return accountStore.ribbonOnProfiles && accountStore.activeProfiles.indexOf(accountStore.ribbonOnProfiles) > -1;
  }
}
