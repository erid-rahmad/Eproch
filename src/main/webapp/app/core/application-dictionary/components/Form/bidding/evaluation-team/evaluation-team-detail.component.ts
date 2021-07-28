import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import { Component, Mixins, Vue, Inject, Watch } from 'vue-property-decorator';
import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';

const EvaluationTeamDetailProp = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    },
  }
});

@Component
export default class EvaluationTeamDetail extends Mixins(AccessLevelMixin, EvaluationTeamDetailProp) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  editMode: boolean = false;
  currentRow: any = {};
  tmpRow: any = null;

  mainForm = {
    biddingNo: 'BN-00001',
    biddingTitle: 'Pengadaan Kendaraan Operasional',
    members: [],
    deletedLines: []
  }

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 256,
    height: 200
  };

  positions: any[] = [
    {name:'Evaluator Administrasi', id:'E'},
    {name:'Reviewer', id:'R'},
    {name:'Approver', id:'A'}
  ];

  users: any[] = [
  ];

  userOptions: any[] = [];

  validationSchema = {};

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  @Watch('data')
  onDataChanged(data: any) {
    this.mainForm = data;
  }

  onCurrentRowChanged(row: any) {
    if (!this.editMode) {
      this.currentRow = row;
    }
  }

  onUserChanged(row: any, value?: number) {
    if (value) {
      const user = this.userOptions.find(u => u.id === value);
      console.log(user);

      row.adUserName = user.name;
      row.adUserPosition = user.position;
    } else {
      row.adUserName = null;
      row.adUserPosition = null;
    }
  }

  created() {
    this.mainForm = this.data;
    this.userOptions = this.users;

    this.commonService("/api/m-bidding-eval-team-lines").retrieve({
      criteriaQuery: this.updateCriteria([
      'active.equals=true',
      `evaluationTeamId.equals=${this.data.id}`]),
      paginationQuery: {
        page: 0,
        size: 100,
        sort: ['id']
      }
    }).then((res)=>{
      this.mainForm.members = res.data.map((item)=>{
        item.adUserName = `${item.adUserName?item.adUserName:""} ${item.adUserLastName?item.adUserLastName:""}`
        item.editMode = false
        return item;
      });;
    })
  }

  retrieveUsers(query?: string) {
    console.log('retrieve users. query:', query);
    if (query !== '') {
      setTimeout(() => {
        this.commonService("/api/ad-users").retrieve({
          criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `userName.contains=${query}`,
          `employee.equals=true`]),
          paginationQuery: {
            page: 0,
            size: 100,
            sort: ['id']
          }
        }).then((res)=>{
          this.userOptions = res.data.map((item)=>{
            return {
              id: item.id,
              name: `${item.firstName?item.firstName:''} ${item.lastName?item.lastName:''}`,
              position: item.position,
              email: item.email
            }
          })
        })
      }, 200);
    } else {
      this.userOptions = this.users;
    }
  }

  addMember() {
    this.editMode = true;
    this.mainForm.members.push({
      position: null,
      adUserId: null,
      adUserName: null,
      adUserPosition: null,
      editMode: true
    });
  }

  cancelEdit(index: number) {
    if (this.tmpRow) {
      this.mainForm.members.splice(index, 1, { ...this.tmpRow });
      this.tmpRow = null
    } else {
      this.mainForm.members.splice(index, 1);
    }

    this.editMode = false;
  }

  deleteMember(index: number) {
    if(this.mainForm.members[index].id)
      this.mainForm.deletedLines.push(this.mainForm.members[index]);
    this.mainForm.members.splice(index, 1);
  }

  editMember(row: any) {
    this.tmpRow = { ...row };
    this.editMode = true;
    row.editMode = true;
  }

  saveMember(row: any) {
    this.tmpRow = null;
    row.editMode = false;
    this.editMode = false;
  }

  formatPosition(val){
    return this.positions.find(status => status.id === val)?.name;
  }
}