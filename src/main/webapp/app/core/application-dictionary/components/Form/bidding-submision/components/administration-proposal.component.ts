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
    dockument: 'Policy Statment - apakah perusahaan memiliki kebijakan K3L dalam menjalankan usahanya ? ',
    dockument1: 'wajib',
    input: 'yes',



  }, {
    no: '1',
    dockument: 'Emergency Response procedure - apakah perusahaan memiliki prosedur tanggap darurat ? ',
    dockument1: 'wajib',
    input: 'yes',

  }, {
    no: '1',
    dockument: 'Basic Safety Rules - apakah perusahaan memiliki peraturan dasar keselamatan kerja ? ',
    dockument1: 'wajib',
    input: 'yes',

  }];
  dummy1 = [{
    no: '1',
    dockument: 'Profesional safety support - Bagaimana penanganan / pengelolaan profesional safety supportt ? ',
    dockument1: 'wajib',
    dockument3: 'yes',


    input: '',
  }, {
    no: '1',
    dockument: 'Enviromental - Sejauh mana perusahaan anda mengelola kebijakan lingkungan kerja ',
    dockument1: 'wajib',
    dockument3: 'yes',

  }, ];
  dummy2 = [{
    no: '1',
    dockument: 'Apakah pengurus telah menetapkan struktur organisasi perusahaan ',
    dockument1: 'wajib',
    dockument3: 'yes',


    input: '',
  }, {
    no: '1',
    dockument: 'apakah pengurus menetapkan kebijakan pengelolaan usaha dan perngendalian kegiatan usaha perusahaan ',
    dockument1: 'wajib',
    dockument3: 'yes',

  }, ];




  created() {

  }

  back() {
    this.$emit("back")
      ;
  }



}
