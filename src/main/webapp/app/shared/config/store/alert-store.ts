import { VuexModule, Module, Mutation, Action, getModule } from 'vuex-module-decorators'
import store from '@/shared/config/store'

export interface IAlertState {
  dismissTimeout: number
  dismissCountDown: number
  type: string
  message: object
}

@Module({ dynamic: true, store, name: 'alertStore', namespaced: true })
class AlertStore extends VuexModule implements IAlertState {
  public dismissTimeout = 0
  public dismissCountDown = 0
  public type = ''
  public message = {}

  @Mutation
  private INIT_ALERT() {
    this.dismissTimeout = 0
    this.dismissCountDown = 0
    this.type = ''
    this.message = {}
  }

  @Mutation
  private SET_TYPE(type: string) {
    this.type = type
  }

  @Mutation
  private SET_MESSAGE(message: string) {
    this.dismissTimeout = 5
    this.dismissCountDown = 5
    this.message = message
  }

  @Mutation
  private SET_COUNT_DOWN(count: number) {
    this.dismissCountDown = count;
  }

  @Action
  public async init() {
    this.INIT_ALERT()
  }

  @Action
  public async setType(type: string) {
    this.SET_TYPE(type)
  }

  @Action
  public async setMessage(message: string) {
    this.SET_MESSAGE(message)
  }

  @Action
  public async setCountDown(count: number) {
    this.SET_COUNT_DOWN(count)
  }
}

export const AlertStoreModule = getModule(AlertStore)
