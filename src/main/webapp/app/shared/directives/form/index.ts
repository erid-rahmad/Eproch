import Inputmask from 'inputmask'
import { isObject } from 'lodash'
import { DirectiveOptions } from 'vue'

export const inputmask: DirectiveOptions = {
  inserted(el, binding) {
    const { value } = binding
    let element: HTMLElement;

    if (el.tagName === 'SPAN') {
      element = el;
    } else {
      const inputs = el.getElementsByTagName('INPUT')

      if (value && isObject(value) && inputs.length) {
        element = <HTMLElement>inputs[inputs.length - 1]
      }
    }

    if (element) {
      new Inputmask({
        autoUnmask: true,
        ...value
      }).mask(element)
    }
  }
}
