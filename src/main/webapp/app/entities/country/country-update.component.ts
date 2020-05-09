import { Component, Vue, Inject } from 'vue-property-decorator';
import { Form } from 'element-ui';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import CurrencyService from '../currency/currency.service';
import { ICurrency } from '@/shared/model/currency.model';

import RegionService from '../region/region.service';
import { IRegion } from '@/shared/model/region.model';

import CityService from '../city/city.service';
import { ICity } from '@/shared/model/city.model';

import AlertService from '@/shared/alert/alert.service';
import { ICountry, Country } from '@/shared/model/country.model';
import CountryService from './country.service';
import { CrudEventBus } from '@/core/event/crud-event-bus';

//import { TagsViewModule, ITagView } from '@/store/modules/tags-view'

/*const validations: any = {
  country: {
    name: {
      required
    },
    code: {
      required
    }
  }
};*/

@Component
//({
//  validations
//})
export default class CountryUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;

  @Inject('countryService') private countryService: () => CountryService;
  public country: ICountry = new Country();

  @Inject('currencyService') private currencyService: () => CurrencyService;
  public currencies: ICurrency[] = [];

  @Inject('regionService') private regionService: () => RegionService;
  public regions: IRegion[] = [];

  @Inject('cityService') private cityService: () => CityService;
  public cities: ICity[] = [];

  public isSaving = false;
  //public tempTagView?: ITagView
  public status = ''
  public textMap = {
    edit: 'Edit Country',
    create: 'Create Country'
  }
  public rules = {
    name: [{ 
      required: true, 
      trigger: 'blur' 
    }],
    code: [{
      pattern: '^[A-Z]{2}$',
      required: true, 
      trigger: 'change' 
    }],
    //currency: [{ 
      //required: true, 
      //trigger: 'change' 
    //}]
  };

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.countryId) {
        vm.status = 'edit'
        //console.log("1")
        vm.retrieveCountry(to.params.countryId);
        
      }else{
        vm.status = 'create'
        //console.log("2")
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    
    (this.$refs.country as Form).validate(async(valid) => {
      if (valid) {
        this.isSaving = true;
        if (this.country.id) {
          this.countryService()
            .update(this.country)
            .then(param => {
              this.isSaving = false;
              this.$router.go(-1);
              const message = this.$t('opusWebApp.country.updated', { param: param.id });
              CrudEventBus.$emit('country-update-success')
              this.$notify({
                title: 'Success',
                message: message.toString(),
                type: 'success',
                duration: 3000
              });
            })
            .catch(()=>{
              const message = "Error, data already exist/ .........";
              this.$notify({
                title: 'Error',
                message: message.toString(),
                type: 'error',
                duration: 3000
              });
              this.isSaving = false;
            });
        } else {
          this.countryService()
            .create(this.country)
            .then(param => {
              this.isSaving = false;
              this.$router.go(-1);
              const message = this.$t('opusWebApp.country.created', { param: param.id });
              CrudEventBus.$emit('country-update-success')
              this.$notify({
                title: 'Success',
                message: message.toString(),
                type: 'success',
                duration: 3000
              });
            })
            .catch(()=>{
              const message = "Error, data already exist/ .........";
              this.$notify({
                title: 'Error',
                message: message.toString(),
                type: 'error',
                duration: 3000
              });
              this.isSaving = false;
            });
        }
      }
    })
  }

  public retrieveCountry(countryId): void {
    this.countryService()
      .find(countryId)
      .then(res => {
        this.country = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.currencyService()
      .retrieve()
      .then(res => {
        this.currencies = res.data;
      });
    this.regionService()
      .retrieve()
      .then(res => {
        this.regions = res.data;
      });
    this.cityService()
      .retrieve()
      .then(res => {
        this.cities = res.data;
      });
  }
}
