import {
  ElForm
} from 'element-ui/types/form';
import AlertMixin from '@/shared/alert/alert.mixin';
import {
  mixins
} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import Vue from 'vue';
import Component from 'vue-class-component';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
import {
  log
} from 'util';

const BiddingScheduleProp = Vue.extend({

})

@Component
export default class BiddingSchedule extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, BiddingScheduleProp) {

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 200,
    height: 200
  };
  rules = {}

  fullscreenLoading = false;
  processing = false;
  dialogConfirmationVisible: boolean = false;

  public eventScheduleOptions: any = {};




  projectinformation = [];
  dummy = [{
    no: '1',
    dockument: 'Pendidikan ',
    dockument1: 'wajib',
    input: 'yes',



  }, {
    no: '1',
    dockument: 'Sertifikat Keahlian ',
    dockument1: 'wajib',
    input: 'yes',

  }, {
    no: '1',
    dockument: 'Pengalaman ',
    dockument1: 'wajib',
    input: 'yes',

  }];
  dummy1 = [{
    no: '1',
    dockument: 'Metoda Pelaksanaan Pekerjaan ',
    dockument1: 'wajib',
    dockument3: 'yes',


    input: '',
  },];
  
  dummy2 = [{
    no: '1',
    dockument: 'Pengalaman ',
    dockument1: 'wajib',
    dockument3: 'yes',


    input: '',
  }, {
    no: '1',
    dockument: 'Pendidikan ',
    dockument1: 'wajib',
    dockument3: 'yes',

  }, ];




  created() {

  }

  back() {
    this.$emit("back")
  }



}
