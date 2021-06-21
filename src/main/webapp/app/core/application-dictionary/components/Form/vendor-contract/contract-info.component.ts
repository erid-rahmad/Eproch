import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import AdInputList from '@/shared/components/AdInput/ad-input-list.vue';
import AdInputLookup from '@/shared/components/AdInput/ad-input-lookup.vue';
import { ElForm } from 'element-ui/types/form';
import { ElInput } from 'element-ui/types/input';
import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';

const baseApiContract = 'api/m-contracts';

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

        this.loading = true;
        this.commonService(baseApiContract)
          [newRecord ? 'create' : 'update'](this.contract)
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