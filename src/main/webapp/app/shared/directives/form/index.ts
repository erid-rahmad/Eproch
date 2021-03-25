import Inputmask from 'inputmask'
import { isObject } from 'lodash'
import { DirectiveOptions } from 'vue'
import Cleave from 'cleave.js'

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
};

export const cleave: DirectiveOptions = {
  inserted: (el, binding) => {
    const input = el.querySelector('input.el-input__inner');
    (<any>input)._cleave = new Cleave(input, binding.value || {});
  },
  update: (el) => {
    const input = el.querySelector('input.el-input__inner');
    const event = new Event('input', {bubbles: true});
    setTimeout(function () {
      (<any>input).value = (<any>input)._cleave?.properties?.result;
      input.dispatchEvent(event);
    }, 100);
  },
  unbind: (el) => {
    const input = el.querySelector('input.el-input__inner');
    (<any>input)._cleave.destroy();
  }
};
