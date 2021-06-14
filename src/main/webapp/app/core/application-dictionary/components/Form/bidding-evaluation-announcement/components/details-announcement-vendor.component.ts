import {Component, Inject, Vue} from "vue-property-decorator";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
import {mixins} from "vue-class-component";
import AlertMixin from "@/shared/alert/alert.mixin";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import axios from "axios";


const baseApiBiddingResult='api/m-bidding-results/';
const baseApiBiddingSchedule='api/m-bidding-schedules';

  const EventAnnouncement = Vue.extend({
    props: {
      pickRow: {
        type: Object,
        default: () => {}
      }
    }
  });

@Component
export default class DetailsAnnouncementForm extends  mixins(EventAnnouncement, AlertMixin,AccessLevelMixin) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  winerTableVisible = false;
  private email:any={};
  private biddingSchedule:any={};

  created(){
    this.getemail(this.pickRow.id);
    this.getSchedule(this.pickRow.biddingId);
  }

  downloadAttachment() {
    window.open(this.email.attachmentUrl, '_blank');
  }

  getemail(id) {
    axios
      .get(`${baseApiBiddingResult}${id}`)
      .then(response => {
        this.email = response.data;
        console.log("this email",this.email)
      });

  }
  getSchedule(id){
    this.commonService(baseApiBiddingSchedule)
  .retrieve({
      criteriaQuery: this.updateCriteria([
        `biddingId.equals=${id}`
      ])
    })
      .then(res => {
        res.data.forEach(schedule=>{
          console.log("this schedule",schedule);
          if (schedule.formType=="AN"){
            if(schedule.actualStartDate){
              this.$set(this.biddingSchedule,'startDate',schedule.actualStartDate);
            }else {
              this.$set(this.biddingSchedule,'startDate',schedule.startDate);
            }
          }
          if (schedule.eventTypeLineName=="Vendor Confirmation"){
            if(schedule.actualEndDate){
              this.$set(this.biddingSchedule,'endDate',schedule.actualEndDate);
            }else {
              this.$set(this.biddingSchedule,'endDate',schedule.endDate);
            }
          }
        })
      })
      .catch(err => this.$message.error('Failed to get bidding announcement'))
  }
  back() {
    this.$emit("back")
  ;
  }
}
