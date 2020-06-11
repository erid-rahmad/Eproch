import { VuexModule, Module, Mutation, Action, getModule } from 'vuex-module-decorators'
import store from '@/shared/config/store'

interface IErrorLog {
  err: Error
  vm: any
  info: string
  url: string
}

export interface IErrorLogState {
  logs: IErrorLog[]
}

@Module({ dynamic: true, store, name: 'errorLogStore', namespaced: true })
class ErrorLogStore extends VuexModule implements IErrorLogState {
  public logs: IErrorLog[] = []

  @Mutation
  private ADD_LOG(log: IErrorLog) {
    this.logs.push(log)
  }

  @Mutation
  private CLEAR_LOG() {
    this.logs.splice(0)
  }

  @Action
  public async add(log: IErrorLog) {
    this.ADD_LOG(log)
  }

  @Action
  public async clear() {
    this.CLEAR_LOG()
  }
}

export const ErrorLogStoreModule = getModule(ErrorLogStore)
