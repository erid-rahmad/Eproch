import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";
import EmailGrid from './evaluation-announcement-email-grid.vue';
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import ScheduleEventMixin from "@/core/application-dictionary/mixins/ScheduleEventMixin";


@Component({
  components: {
    EmailGrid,
  }
})
export default class EventAnnouncement extends mixins(ScheduleEventMixin) {


}
