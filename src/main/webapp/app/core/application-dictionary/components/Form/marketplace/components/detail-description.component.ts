import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import { Component } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
import Vue from 'vue';

const DetailDescriptionProp = Vue.extend({
  props: {
    detailDescription: {
      type: Object,
      default: () => {}
    }
  }
})

@Component
export default class DetailDescription extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, DetailDescriptionProp) {

  headerDescription: string = "";
  valueDescription: string = "";

  descriptionDetail = [
    {
      key: 1,
      value: "Resolusi Native: WXGA (1280X800)"
    }, {
      key: 2,
      value: "Brightness: 4000 Lumens"
    }, {
      key: 3,
      value: "Rasio Contras: 20.000:1"
    }, {
      key: 4,
      value: "Projection System: DLP Technology"
    }, {
      key: 5,
      value: "Unit Utama"
    }
  ]

  descriptionSpec = [
    {
      key: 1,
      name: "Speaker",
      value: "2W"
    }, {
      key: 2,
      name: "Input",
      value: "Computer In (D-sub 15pin Female) x1, Composite Video(RCA) x1, S-Video, HDMI-1, HDMI-2/MHL x1, Audio in(mini jack) x1, USB TypeA(1.5A power) x1, USB Type min B(For Page up/down and FW upgrade) x1, RS232 In (D-sub 9pin, male) x1, IR Receiver(Front+Top) x2"
    }, {
      key: 3,
      name: "Sistem Projector",
      value: "DLP Technology"
    }, {
      key: 4,
      name: "Output",
      value: "Audio out (Mini Jack) x 1"
    }, {
      key: 5,
      name: "Rasio Kontras",
      value: "2000:1"
    }, {
      key: 6,
      name: "Brightness",
      value: "4000 lumens"
    }
  ]

  created() {
    this.headerDescription = this.detailDescription.name;
    this.valueDescription = this.detailDescription.value;
  }

}
