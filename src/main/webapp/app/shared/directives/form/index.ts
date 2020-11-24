import Inputmask from 'inputmask'
import { isObject } from 'lodash'
import { DirectiveOptions } from 'vue'

export const inputmask: DirectiveOptions = {
  inserted(el, binding) {
    const { value } = binding
    const inputs = el.getElementsByTagName('INPUT')

    if (value && isObject(value) && inputs.length) {
      const input = inputs[inputs.length - 1]
      new Inputmask({
        autoUnmask: true,
        ...value
      }).mask(input)
    }
  }
}
