import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import AdInputList from '@/shared/components/AdInput/ad-input-list.vue';
import AdInputLookup from '@/shared/components/AdInput/ad-input-lookup.vue';
import { ElForm } from 'element-ui/types/form';
import { ElInput } from 'element-ui/types/input';
import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';

const baseApiContract = 'api/m-contracts';
const baseApiContractAll = 'api/m-contracts-All';
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

  async average(){
    let total = 0;
    await this.contractLine.forEach(line=>{
      line.totalCeilingPrice=line.ceilingPrice*line.quantity;
      total+=line.totalCeilingPrice;
    })
    await this.$set(this.contract, 'price', total);


  }

  deleteRow(row) {

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
    this.loading = true;
    this.commonService(baseApiContractLine)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `contractId.equals=${ContracId}`
        ]),
      })
      .then(res => {
        this.contractLine=res.data
      })
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

  public save() {
    (<ElForm>this.$refs.contractInfoForm).validate(passed => {
      if (passed) {
        const newRecord = !this.contract.id;
        this.$set(this.contract, 'lineDTOList', this.contractLine);
        this.loading = true;
        this.commonService(baseApiContractAll)
          .create(this.contract)
          .then(res => {
            this.contract = {...this.contract, ...res};
            this.$message.success(`Contract has been ${newRecord ? 'created' : 'updated'} successfully`);
            this.$emit('saved', res);
          })
          .catch(err => {
            console.error('Failed to save the contract', err);
            this.$message.error('Failed to save the contract');
          })
          .finally(() => this.loading = false);
      }
    })
  }
}