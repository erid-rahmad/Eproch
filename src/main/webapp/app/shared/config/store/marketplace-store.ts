import store from '@/shared/config/store';
import { getModule, Module, VuexModule } from 'vuex-module-decorators';

export interface IMarketplaceState {
  cart: any;
}

@Module({ dynamic: true, store, name: 'marketplaceStore', namespaced: true })
class MarketplaceStore extends VuexModule implements IMarketplaceState {
  cart: any = {};
}

export const MarketplaceStoreModule = getModule(MarketplaceStore)
