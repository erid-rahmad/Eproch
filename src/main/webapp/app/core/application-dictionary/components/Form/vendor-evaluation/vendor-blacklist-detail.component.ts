import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { Component, Mixins } from 'vue-property-decorator';

@Component
export default class VendorBlacklistDetail extends Mixins(AccessLevelMixin) {

  columnSpacing = 24;

  filterPersonal = {
    username: null
  };

  filterShareHolder = {
    username: null
  };

  users = [
    {
      username: 'Putra',
      code: '123456',
      position: 'Staff',
      note: 'Kinerja buruk'
    }
  ];

  shareHolders = [];

}