import Vue from 'vue';
import Component from 'vue-class-component';

@Component
export default class BiddingRegistration extends Vue {
  data() {
    return {
      centerDialogVisible: false,
      reson:''
    }
  }


  tableData = [
    {
      0: '20-12-2021',
      1: 'Pengadaan Kendaraan Operasional',
      2: '20-12-2021',
      3: 'Terdaftar',
    }, 
    {
      0: '20-12-2021',
      1: 'Pengadaan Kendaraan Operasional',
      2: '20-12-2021',
      3: 'Belum Terdaftar',
    }, 
    {
      0: '20-12-2021',
      1: 'Pengadaan Kendaraan Operasional',
      2: '20-12-2021',
      3: 'Tidak Berminat',
    }, 
  ];



}
