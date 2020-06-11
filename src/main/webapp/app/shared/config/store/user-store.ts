import { VuexModule, Module, Action, Mutation, getModule } from 'vuex-module-decorators'
// import { login, logout, getUserInfo } from '@/api/users'
import { getToken, setToken, removeToken } from '@/utils/cookies'
// import router, { resetRouter } from '@/router'
import { PermissionStoreModule } from './permission-store'
import { TagsViewStoreModule } from './tags-view-store'
import store from '@/shared/config/store'

export interface IUserState {
  token: string
  name: string
  avatar: string
  introduction: string
  roles: string[]
  email: string
}

@Module({ dynamic: true, store, name: 'userStore', namespaced: true })
class UserStore extends VuexModule implements IUserState {
  public token = getToken() || ''
  public name = ''
  public avatar = ''
  public introduction = ''
  public roles: string[] = []
  public email = ''

  @Mutation
  private SET_TOKEN(token: string) {
    this.token = token
  }

  @Mutation
  private SET_NAME(name: string) {
    this.name = name
  }

  @Mutation
  private SET_AVATAR(avatar: string) {
    this.avatar = avatar
  }

  @Mutation
  private SET_INTRODUCTION(introduction: string) {
    this.introduction = introduction
  }

  @Mutation
  private SET_ROLES(roles: string[]) {
    this.roles = roles
  }

  @Mutation
  private SET_EMAIL(email: string) {
    this.email = email
  }

  // @Action
  // public async Login(userInfo: { username: string, password: string}) {
  //   let { username, password } = userInfo
  //   username = username.trim()
  //   const { data } = await login({ username, password })
  //   setToken(data.accessToken)
  //   this.SET_TOKEN(data.accessToken)
  // }

  // @Action
  // public ResetToken() {
  //   removeToken()
  //   this.SET_TOKEN('')
  //   this.SET_ROLES([])
  // }

  // @Action
  // public async GetUserInfo() {
  //   if (this.token === '') {
  //     throw Error('GetUserInfo: token is undefined!')
  //   }
  //   const { data } = await getUserInfo({ /* Your params here */ })
  //   if (!data) {
  //     throw Error('Verification failed, please Login again.')
  //   }
  //   const { roles, name, avatar, introduction, email } = data.user
  //   // roles must be a non-empty array
  //   if (!roles || roles.length <= 0) {
  //     throw Error('GetUserInfo: roles must be a non-null array!')
  //   }
  //   this.SET_ROLES(roles)
  //   this.SET_NAME(name)
  //   this.SET_AVATAR(avatar)
  //   this.SET_INTRODUCTION(introduction)
  //   this.SET_EMAIL(email)
  // }

  // @Action
  // public async ChangeRoles(role: string) {
  //   // Dynamically modify permissions
  //   const token = role + '-token'
  //   this.SET_TOKEN(token)
  //   setToken(token)
  //   await this.GetUserInfo()
  //   resetRouter()
  //   // Generate dynamic accessible routes based on roles
  //   PermissionStoreModule.GenerateRoutes(this.roles)
  //   // Add generated routes
  //   router.addRoutes(PermissionStoreModule.dynamicRoutes)
  //   // Reset visited views and cached views
  //   TagsViewStoreModule.delAllViews()
  // }

  // @Action
  // public async LogOut() {
  //   if (this.token === '') {
  //     throw Error('LogOut: token is undefined!')
  //   }
  //   await logout()
  //   removeToken()
  //   resetRouter()

  //   // Reset visited views and cached views
  //   TagsViewStoreModule.delAllViews()
  //   this.SET_TOKEN('')
  //   this.SET_ROLES([])
  // }
}

export const UserStoreModule = getModule(UserStore)
