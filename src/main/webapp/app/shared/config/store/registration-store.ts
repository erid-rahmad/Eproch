import { VuexModule, Module, Mutation, Action, getModule } from 'vuex-module-decorators'
import store from '@/shared/config/store'

export interface IRegistrationState {
    vendorType: string
    businessCategories: Set<number>
}

@Module({ dynamic: true, store, name: 'registrationStore', namespaced: true })
class RegistrationStore extends VuexModule implements IRegistrationState {
    public vendorType = ''
    public businessCategories = new Set<number>()
    
    @Mutation
    private SET_VENDOR_TYPE(type: string) {
        this.vendorType = type;
    }

    @Mutation
    private ADD_BUSINESS_CATEGORY(category: number) {
        this.businessCategories.add(category)
    }

    @Action
    public setVendorType(type: string) {
        this.SET_VENDOR_TYPE(type)
    }

    @Action
    public addBusinessCategory(category: number) {
        this.ADD_BUSINESS_CATEGORY(category)
    }
}

export const RegistrationStoreModule = getModule(RegistrationStore)
