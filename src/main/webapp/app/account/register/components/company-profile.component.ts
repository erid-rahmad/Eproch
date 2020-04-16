import Vue from 'vue';
import Component from 'vue-class-component';
import { ElForm } from 'element-ui/types/form';
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store';

const CompanyProps = Vue.extend({
  props: {
    eventBus: {
      type: Object,
      default: () => {}
    },
    company: {
      type: Object,
      default: () => {}
    }
  }
});

@Component
export default class CompanyProfile extends CompanyProps {
  data() {
    return {
      columnSpacing: 32,
      rules: {
        website: {
          type: 'url'
        }
      },
      typeOptions: [
        {
          label: 'Company',
          value: 'C'
        },
        {
          label: 'Professional',
          value: 'P'
        }
      ],
      countryOptions: [
        {
          label: 'Indonesia',
          value: 'ID'
        },
        {
          label: 'Malaysia',
          value: 'MY'
        },
        {
          label: 'Singapore',
          value: 'SG'
        }
      ],
      regionOptions: [
        {
          label: 'DKI Jakarta',
          value: 'JKT'
        },
        {
          label: 'Jawa Barat',
          value: 'JBR'
        },
        {
          label: 'Jawa Tengah',
          value: 'JTG'
        }
      ],
      cityOptions: ['Jakarta Barat', 'Jakarta Pusat', 'Jakarta Selatan', 'Jakarta Timur', 'Jakarta Utara', 'Kep. Seribu']
    };
  }

  mounted() {
    this.eventBus.$on('validate-form', this.validate);
  }

  public handleTypeChange(value: string) {
    registrationStore.setVendorType(value);
  }

  validate(formIndex: number) {
    if (formIndex === 1) {
      (this.$refs.company as ElForm).validate((passed, errors) => {
        this.eventBus.$emit('step-validated', { passed, errors });
      });
    }
  }
}
