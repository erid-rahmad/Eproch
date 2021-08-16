import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import { IAdWatchListItem, AdWatchListActionType } from '@/shared/model/ad-watch-list-item.model';
import { IAdWatchList } from '@/shared/model/ad-watch-list.model';
import VueCountTo from "vue-count-to";
import {Component, Inject, Mixins, Vue, Watch} from 'vue-property-decorator';
import buildCriteriaQueryString from '@/shared/filter/filters';
import { AccountStoreModule as accountStore } from "@/shared/config/store/account-store";
import { WindowStoreModule as windowStore } from "@/shared/config/store/window-store";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import Accordion from './accordion.vue';
import { defaults } from 'lodash';

const baseApiVendor ='api/c-vendors';
const baseApiBidding ='api/m-biddings';
const baseApiVendorConfirmation ='api/pa-dashboards/vendorConfirmation';
enum DisplayType {
  Card = 'card',
  List = 'list'
}

const WatchListProps = Vue.extend({
  props: {
    id: String,
    name: String,
    title: String,
    listColor: Array
  }
})

@Component({
  components: {
    'count-to': VueCountTo,
    Accordion
  }
})
export default class WatchList extends  Mixins(AccessLevelMixin,WatchListProps) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  private data:any={};
  dasbordItem:any={};

  //isVendor:boolean;
  screenWidth: number = window.outerWidth;
  items: IAdWatchListItem[] = [];

  getWidthClass(id: string){
    let defaultClass = 'md-large-size-20 md-medium-size-25 md-small-size-50';
    let width = document.getElementById(id).offsetWidth;

    if((this.screenWidth / 2) > width){
      defaultClass = 'md-large-size-33 md-medium-size-50 md-small-size-100';
    }

    return defaultClass;
  }

  mounted(){
    window.addEventListener('resize', () => {
      this.screenWidth = window.outerWidth;
    });
  
    //setInterval(() => {this.updateCountWatchList()}, 15000);
  }

  /*@Watch('items', {deep: true})*/
  async onItemsChanged(items: IAdWatchListItem[]) {
    await items.forEach((item, index) => {
      this.retrieveWatchListCounter(item, index);
    });
    /*await items.forEach(item=>{
      this.$set(this.data,item.code, item.count);
      this.$set(this.data,item.code+"data", item);
    })*/
  }

  async created() {

    console.log("this vendor", accountStore.userDetails.isVendor)

    /*this.retrieveVendor();
    this.retrieveVendorConfirmation();
    this.retrieveBidding();*/
    this.retrieveWatchListItems(this.name);
  }

  async onCardClicked(card: IAdWatchListItem) {
    card.actionUrl = await this.commonService('/api/ad-menus/full-path').find(card.adMenu.id);
    console.log(card.actionUrl);
    if (card.actionUrl) {
      const timestamp = Date.now();
      windowStore.setWatchlistQuery(card.filterQuery)
        .then(() => {
          this.$router.push({
            path: card.actionUrl,
            query: {
              t: `${timestamp}`
            }
          });
        });
    }
  }

  public refresh() {
    this.onItemsChanged(this.items);
  }

  private getRandomColor(){
    var randomNumber = Math.floor(Math.random() * this.listColor.length);
    return this.listColor[randomNumber];
  }

  retrieveWatchListItems(name: string) {
    this.commonService('/api/ad-watch-lists')
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `name.equals=${name}`
        ],
        paginationQuery: {
          page: 0,
          size: 1,
          sort: ['id']
        }
      })
      .then(res => {
        if (res.data.length > 0) {
          const watchList: IAdWatchList = res.data[0];
          this.items = watchList.adWatchListItems;
          this.items.forEach((x, index) => {
            if(!x.accentColor) x.accentColor = this.getRandomColor()['gradientColor'];
            this.$set(this.items[index], 'isLoading', true);
          });
        }
      })
      .catch(err => {
        console.log('Failed to get the watch list.', err);
        this.$message({
          message: 'Failed to get the watch list',
          type: 'warning'
        });
      });
  }

  private retrieveWatchListCounter(item: IAdWatchListItem, index?: number) {
    this.commonService(item.restApiEndpoint)
      .count([
        item.filterQuery,
        accountStore.userDetails.vendor ? `vendorId.equals=${accountStore.userDetails.cVendorId}` : null
      ])
      .then(count => {
        this.$set(this.items[index], 'count', count);
        this.$set(this.items[index], 'isLoading', false);
      }).
      finally(() => {
        this.$set(this.items[index], 'isLoading', false);
      });
  }

  /*
  get isCard() {
    return this.type === DisplayType.Card;
  }

  get isList() {
    return this.type === DisplayType.List;
  }

  get type() {
    return his.items.length > 4 ? DisplayType.List : DisplayType.Card;
  }

  retrieveVendor(){
    this.commonService(baseApiVendor)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        let a =0;
        res.data.forEach(data=>{
          const currentTime = new Date();
          const currentTime1 = new Date(data.createdDate);
          currentTime.setDate(currentTime.getDate()-30);
          if (currentTime1>currentTime){
            a++
          }
        })
        this.dasbordItem.vendorNew=a;
      })
      .finally();
  }

  retrieveBidding(){
    this.commonService(baseApiBidding)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        let a =0;
        res.data.forEach(data=>{
          const currentTime = new Date();
          const currentTime1 = new Date(data.createdDate);
          currentTime.setDate(currentTime.getDate()-360);
          if (currentTime1>currentTime){
            a++
          }
        })
        this.dasbordItem.eventNew=a;
      })
      .finally();
  }

  retrieveVendorConfirmation(){
    this.commonService(baseApiVendorConfirmation)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.dasbordItem.vendorConfirmation=res.data.vendorConfirmation;
      })
      .finally();
  }*/

}
