import { Component, Vue } from "vue-property-decorator";
import axios from "axios";

  const EventAnnouncement = Vue.extend({
    props: {
      pickRow: {
        type: Object,
        default: () => {}
      }
    }
  });

@Component
export default class DetailsAnnouncementForm extends EventAnnouncement {
  winerTableVisible = false;
  private email:any={};

  created(){
    console.log(this.pickRow)
    this.getemail(this.pickRow.id)

  }

  downloadAttachment() {
    window.open(`/api/c-attachments/download/${this.email.attachmentId}-${this.email.attachmentName}`, '_blank');
  }

  getemail(id) {
    axios
      .get(`/api/m-bidding-results/${id}`)
      .then(response => (this.email = response.data));
    console.log("this info", this.email);
  }


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
