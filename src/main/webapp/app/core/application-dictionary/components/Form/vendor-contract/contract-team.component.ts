import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import { Component, Mixins, Vue, Inject, Watch } from 'vue-property-decorator';
import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';

const ContractTeamProp = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    },
    fromGrid: {
      type: Boolean,
      default: false
    }
  }
});

@Component
export default class ContractTeam extends Mixins(AccessLevelMixin, ContractTeamProp) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  editMode: boolean = false;
  currentRow: any = {};
  tmpRow: any = null;
  loading = false;

  mainForm:any = {
    biddingNo: 'BN-00001',
    biddingTitle: 'Pengadaan Kendaraan Operasional',
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

  teamData: any = {};

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
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
    this.loading = true;

    this.mainForm = this.data;
    this.mainForm.members = []
    this.mainForm.deletedLines = []
    
    this.userOptions = this.users;
    if(this.fromGrid) {
      this.retrieveTeam(this.data.id)
    } else {
      this.commonService("/api/m-contract-teams").retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `contractId.equals=${this.data.id}`]), 
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      }).then((res)=>{
        if(res.data.length) {
          this.teamData = res.data[0];
          this.retrieveTeam(this.teamData.id);
        } else {
          let newTeam = {
            adOrganizationId: this.data.adOrganizationId,
            contractId: this.data.id,
            status: 'U'
          }
          this.commonService('/api/m-contract-teams').create(newTeam).
          then((res)=>{
            this.teamData = res;
          }).catch((res)=>{
            this.$message.error(`Error during creating new contract team`)
          }).finally(()=>{
            this.loading = false;
          });
        }
      }).catch((err)=>{
        console.log(err);
        this.$message.error("Unable to load contract team.")
        this.loading = false;
      });
    }

    this.commonService(null).retrieveReferenceLists("cContractPosition")
      .then(res => this.positions = res)
      .catch(err => this.$message.warning('Failed to get positions.'));
  }

  retrieveTeam(contractTeamId){
    this.commonService("/api/m-contract-team-lines").retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true',
        `contractTeamId.equals=${contractTeamId}`]), 
      paginationQuery: {
        page: 0,
        size: 100,
        sort: ['id']
      }
    }).then((res)=>{
      this.mainForm.members = res.data.map((item)=>{
        item.adUserName = `${item.adUserName?item.adUserName:""} ${item.adUserLastName?item.adUserLastName:""}`
        return item;
      });
    }).catch((err)=>{
      console.log(err);
      this.$message.error("Unable to load contract team.")
    }).finally(()=>{
      this.loading = false;
    });
  }

  retrieveUsers(query?: string) {
    console.log('retrieve users. query:', query);
    setTimeout(() => {
      this.commonService("/api/ad-users").retrieve({
        criteriaQuery: this.updateCriteria([
        'active.equals=true',
        `userName.contains=${query}`,
        `cVendorId.equals=${this.data.vendorId}`]),
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
    row.editMode = true;
  }

  saveMember(row: any) {
    this.tmpRow = null;
    row.editMode = false;
    this.editMode = false;
  }

  formatPosition(val){
    return this.positions.find(status => status.value === val)?.name;
  }

  save(){
    console.log(this.fromGrid)
    if(this.fromGrid){
      this.commonService('/api/m-contract-teams')[this.mainForm.id?'update':'create'](this.mainForm).
      then((res)=>{
        this.$message.success(`Contract Team ${this.mainForm.id?'updated':'created'}.`)
      }).catch((res)=>{
        this.$message.error(`Error during ${this.mainForm.id?'updating':'creating'} contract team`)
      });
    } else {
      this.teamData.members = this.mainForm.members;
      this.teamData.deletedLines = this.mainForm.deletedLines;
      this.commonService('/api/m-contract-teams')[this.teamData.id?'update':'create'](this.teamData).
      then((res)=>{
        this.$message.success(`Contract Team ${this.teamData.id?'updated':'created'}.`)
      }).catch((res)=>{
        this.$message.error(`Error during ${this.teamData.id?'updating':'creating'} contract team`)
      });
    }
  }
}