import { DirectiveOptions } from 'vue'
import { UserStoreModule as userStore } from '@/shared/config/store/user-store'

export const permission: DirectiveOptions = {
  inserted(el, binding) {
    const { value } = binding
    const roles = userStore.roles
    if (value && value instanceof Array && value.length > 0) {
      const permissionRoles = value
      const hasPermission = roles.some(role => {
        return permissionRoles.includes(role)
      })
      if (!hasPermission) {
        el.parentNode && el.parentNode.removeChild(el)
      }
    } else {
      throw new Error(`need roles! Like v-permission="['admin','editor']"`)
    }
  }
}
