import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import Vue from 'vue';
import { Component, Mixins } from 'vue-property-decorator';

const TechnicalPropsalProp = Vue.extend({

})

@Component
export default class TechnicalPropsal extends Mixins(AccessLevelMixin, TechnicalPropsalProp) {

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
