import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import AdInputList from '@/shared/components/AdInput/ad-input-list.vue';
import AdInputLookup from '@/shared/components/AdInput/ad-input-lookup.vue';
import { ElForm } from 'element-ui/types/form';
import { ElInput } from 'element-ui/types/input';
import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';

const baseApiContract = 'api/m-contracts';
const baseApiContractAll = 'api/m-contracts-All';
const baseApiContractGeneratePo = 'api/m-contracts-generatePO';
const baseApiContractLine = 'api/m-contract-lines';

const ContractInfoProps = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
})

@Component({
  components: {
    AdInputList,
    AdInputLookup
  }
})
export default class ContractInfo extends Mixins(AccessLevelMixin, ContractInfoProps) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loading: boolean = false;
  generatePA: boolean = false;

  contract: any = {};
  contractLine:any=[];


  validationSchema: any = {
    name: {
      required: true,
      message: 'Title is required'
    },
    picUserId: {
      required: true,
      message: 'PiC is required'
    },
    costCenterId: {
      required: true,
      message: 'Department is required'
    },
    vendorId: {
      required: true,
      message: 'Vendor Name is required'
    },
    startDaet: {
      required: true,
      message: 'Start Date is required'
    }
  }

  gutterSize: number = 24;

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  get editMode() {
    return !!this.contract.id;
  }

  get formSettings() {
    return settings.form;
  }

  get readOnly() {
    return this.contract.documentStatus && this.contract.documentStatus !== 'DRF';
  }

  created() {
    this.contract = {...this.data};
    this.retriveContracLine(this.contract.id);
  }

  documentStatuses = {
    APV: 'Approved',
    DRF: 'Draft',
    RJC: 'Rejected',
    RVS: 'Revised',
    SMT: 'Submitted',
  }

  hierarchicalType= [{
    value: 'Stand Alone Agreement',
    label: 'Stand Alone Agreement'
  }, {
    value: 'Master Agreement',
    label: 'Master Agreement'
  }, {
    value: 'Sub Agreement',
    label: 'Sub Agreement'
  }]

  termType= [{
    value: 'Fixed',
    label: 'Fixed'
  },]

  printStatus(status: string) {
    return this.documentStatuses[status];
  }

  async average(){
    let total = 0;
    await this.contractLine.forEach(line=>{
      line.totalCeilingPrice=line.ceilingPrice*line.quantity;
      total+=line.totalCeilingPrice;
    })
    await this.$set(this.contract, 'price', total);
  }

  deleteRow(row) {
    this.contractLine.splice(row.$index,1)
    this.delleteContactLine(row.row.id);
  }

  addLine(){
    var line = {
      lineNo : null,
      quantity : null,
      ceilingPrice : null,
      totalCeilingPrice : null,
      deliveryDate : null,
      remark : null,
      active : true,
      contractId : this.contract.id,
      adOrganizationId : this.contract.adOrganizationId,
      costCenterId : null,
      productId : null,
      uomId : null
    }
    this.contractLine.unshift(line);
  }

  retriveContracLine(ContracId){
    try {
      this.loading = true;
      this.commonService(baseApiContractLine)
        .retrieve({
          criteriaQuery: this.updateCriteria([
            'active.equals=true',
            `contractId.equals=${ContracId}`
          ]),
          paginationQuery: {
            page: 0,
            size: 1000,
            sort: ['id']
          }
        })
        .then(res => {
          this.contractLine=res.data
        })
        .finally(() => this.loading = false);
    }catch (e) {}
  }

  delleteContactLine(Id){
    this.loading = true;
    this.commonService(baseApiContractLine)
      .delete(Id)
      .then()
      .catch(err => {
        console.error('Failed getting the record. %O', err);
        this.$message.error(err.detail || err.message);
      })
      .finally(() => this.loading = false);
  }

  mounted() {
    this.$nextTick(() => {
      (<ElInput>this.$refs.documentNo).focus();
    });
  }

  public submit() {
    this.contract.documentStatus='SMT'
    this.save()
  }

  public approve() {
    this.contract.documentStatus='APV'
    this.save()
  }

  public reject() {
    this.contract.documentStatus='RJC'
    this.save()
  }

  generatePoAction(){
    this.generatePA=true;
  }

  public save() {
    (<ElForm>this.$refs.contractInfoForm).validate(passed => {
      if (passed) {
        const newRecord = !this.contract.id;
        this.$set(this.contract, 'lineDTOList', this.contractLine)
        this.loading = true
        this.commonService(baseApiContractAll)
          .create(this.contract)
          .then(res => {
            this.contract = {...this.contract, ...res}
            this.$message.success(`Contract has been ${newRecord ? 'created' : 'updated'} successfully`)
            this.$emit('saved', res)
          })
          .catch(err => {
            console.error('Failed to save the contract', err);
            this.$message.error('Failed to save the contract');
          })
          .finally(() => this.loading = false);
      }
    })
  }

  public generatePo() {
    (<ElForm>this.$refs.contractInfoForm).validate(passed => {
      if (passed) {
        let data={
          mContractDTO:this.contract,
          mContractLineDTOS:this.contractLine
        }
        this.loading = true
        this.commonService(baseApiContractGeneratePo)
          .create(data)
          .then(res => {
            this.contract = {...this.contract, ...res}
            this.$emit('saved', res)
          })
          .catch(err => {
            console.error('Failed to save the contract', err);
            this.$message.error('Failed to save the contract');
          })
          .finally(() => {
            this.loading = false
            this.generatePA=false;
          });
      }
    })
  }

}
