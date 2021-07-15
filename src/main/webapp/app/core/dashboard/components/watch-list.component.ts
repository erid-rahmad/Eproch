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

const baseApiVendor ='api/c-vendors';
const baseApiBidding ='api/m-biddings';
const baseApiVendorConfirmation ='api/pa-dashboards/vendorConfirmation';
enum DisplayType {
  Card = 'card',
  List = 'list'
}

const WatchListProps = Vue.extend({
  props: {
    id: Number,
    name: String,
    title: String
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

  items: IAdWatchListItem[] = [];
  listColor=
  [
    //yellow
    {staticColor: '#ecec04', gradientColor: 'linear-gradient(315deg, #fbb034 0%, #ffdd00 74%);'}, 
    //red
    {staticColor: '#f5365c', gradientColor: 'linear-gradient(45deg, rgb(255, 83, 112), rgb(255, 134, 154));'}, 
    //green
    {staticColor: '#2dce89', gradientColor: 'linear-gradient(87deg, rgb(45, 206, 137) 0px, rgb(45, 206, 204) 100%);'}, 
    // blue
    {staticColor: '#11cdef', gradientColor: 'linear-gradient(87deg, rgb(17, 205, 239) 0px, rgb(17, 113, 239) 100%);'}
  ]

  get isCard() {
    return this.type === DisplayType.Card;
  }

  get isList() {
    return this.type === DisplayType.List;
  }

  get type() {
    return /*this.items.length > 4 ? DisplayType.List : */DisplayType.Card;
  }

  /*mounted(){
    setInterval(() => {this.updateCountWatchList()}, 15000);
  }*/

  @Watch('items', {deep: true})
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

    console.log("this vendor", accountStore.userDetails.vendor)

    /*this.retrieveVendor();
    this.retrieveVendorConfirmation();
    this.retrieveBidding();*/
    this.retrieveWatchListItems(this.name);
  }

  async onCardClicked(card: IAdWatchListItem) {
    card.actionUrl = await this.commonService('/api/ad-menus/full-path').find(card.adMenu.id);
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
    var randomNumber = Math.floor(Math.random() * 4);
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
          this.items.forEach((x) => {
            if(!x.accentColor) x.accentColor = this.getRandomColor().gradientColor;
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

  /*retrieveVendor(){
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

  private retrieveWatchListCounter(item: IAdWatchListItem, index?: number) {
    this.commonService(item.restApiEndpoint)
      .count([
        item.filterQuery.split('&'),
        accountStore.userDetails.vendor ? `vendorId.equals=${accountStore.userDetails.cVendorId}` : null
      ])
      .then(count => {
        this.$set(this.items[index], 'count', count);
      });
  }
}
