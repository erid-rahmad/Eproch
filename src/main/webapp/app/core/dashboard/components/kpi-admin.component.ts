import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import {Component, Inject, Mixins} from 'vue-property-decorator';

import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";




@Component
export default class WatchList extends  Mixins(AccessLevelMixin) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

}
