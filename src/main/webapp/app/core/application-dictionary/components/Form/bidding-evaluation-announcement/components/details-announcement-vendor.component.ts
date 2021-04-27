import { Component, Vue } from "vue-property-decorator";


const EventAnnouncement = Vue.extend({
  props: {
    page: {
      type: Number,
      default: () => {
        return 0;
      }
    }
  }
});

@Component
export default class DetailsAnnouncementForm extends EventAnnouncement {
  winerTableVisible = false;
  // private announcement: any = {};
  announcement = 
    {
      start: "06 Desember 2019 16:00",
      end: "11 Desember 2020 18:51 ",
      announcement: "",
      doc: "Proposal.pdf",
      
      
    }
    winerTable= [{
    email: 'westcon@gmail.com',
    name: 'WESTCON INTERNATIONAL INDONESIA',
    status: 'lolos'
  }, 
];
  
  moreinfo1= [
    { JudulPengadaan: 'Pengadaan 1' },
    { JudulPengadaan: 'Pengadaan 2' },
      
  ];
  moreinfo2= [
    { namaperusahaan: 'PT Eproch', tanggaldaftar: '28-12-2021' },
    { namaperusahaan: 'PT Eproch', tanggaldaftar: '28-12-2021' },
      
  ];

  moreinfo3= [
    { namaperusahaan: 'PT Eproch', tanggaldaftar: '28-12-2021',alasan:'tolak' },
    { namaperusahaan: 'PT Eproch', tanggaldaftar: '28-12-2021',alasan:'tolak' },
      
  ];

  moreinfo4= [
    { namaperusahaan: 'PT Eproch', tanggaldaftar: '28-12-2021' },
    { namaperusahaan: 'PT Eproch', tanggaldaftar: '28-12-2021' },
      
  ];

  back() {
    this.$emit("back")
  ;
    
  
    
  }
  




}