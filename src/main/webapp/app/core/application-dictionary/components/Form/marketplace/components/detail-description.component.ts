import { Component, Vue } from 'vue-property-decorator';

const DetailDescriptionProp = Vue.extend({
  props: {
    title: String,
    content: String,
    contentType: String
  }
})

@Component
export default class DetailDescription extends DetailDescriptionProp {
  get noContentMessage() {
    return `${this.title} is not provided`;
  }
}
