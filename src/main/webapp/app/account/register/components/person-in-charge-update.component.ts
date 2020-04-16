import Vue from 'vue';
import Component from 'vue-class-component';
import { ElForm } from 'element-ui/types/form';
import { Inject } from 'vue-property-decorator';
import BusinessCategoryService from '@/entities/business-category/business-category.service';
import { PersonInCharge } from '@/shared/model/person-in-charge.model';
import { CompanyFunctionary } from '@/shared/model/company-functionary.model';

const PersonInChargeUpdateProps = Vue.extend({
  props: {
    eventBus: {
      type: Object,
      default: () => {}
    },
    contact: Boolean, // Whether or not the current form is represents PIC.
    person: {
      type: Object,
      default() {
        return this.contact ? new PersonInCharge() : new CompanyFunctionary();
      }
    }
  }
});

@Component
export default class PersonInChargeUpdate extends PersonInChargeUpdateProps {
  @Inject('businessCategoryService')
  private businessCategoryService: () => BusinessCategoryService;

  public rules = {};
  public businessCategories = [];

  mounted() {
    this.eventBus.$on('save-person', this.save);
    this.eventBus.$on('reset-person-form', this.reset);
  }

  beforeDestroy() {
    this.eventBus.$off('save-person', this.save);
    this.eventBus.$off('reset-person-form', this.reset);
  }

  private reset() {
    (<ElForm>this.$refs.person).resetFields();
  }

  private save() {
    (<ElForm>this.$refs.person).validate((passed, errors) => {
      if (passed) {
        setTimeout(() => {
          const data = { ...this.person };
          this.eventBus.$emit('push-person', data);
          this.reset();
        }, 1000);
      } else {
        this.eventBus.$emit('person-validation-failed', { passed, errors });
      }
    });
  }
}
