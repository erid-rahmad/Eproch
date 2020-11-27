import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import { ElForm } from 'element-ui/types/form';
import Vue from 'vue';
import Component from 'vue-class-component';
import { Inject } from 'vue-property-decorator';

const AddressEditorProps = Vue.extend({
  props: {
    value: Number
  }
})

@Component
export default class AddressEditor extends AddressEditorProps {

  @Inject('dynamicWindowService')
  private service: (baseApiUrl: string) => DynamicWindowService;

  private baseApiUrl: string = '/api/c-locations';

  // Used in Vue template.
  loading: boolean = false;
  validationSchema = {};
  columnSpacing = 24;
  location: Record<string, any> = {};
  visible: boolean = false;
  countryHasRegion = true;
  cities: any[] = [];
  countries: any[] = [];
  regions: any[] = [];
  country = null;
  region = null;

  created() {
    this.retrieveCountries();
    this.initForm();
  }

  onPopoverShow() {
    this.initForm();
  }

  onCountryChange(value: any) {
    this.region = null;

    if (!value) {
      this.clearField('cityId');
      return;
    }

    this.clearField('cityId');
    const country = JSON.parse(value);
    this.countryHasRegion = country.withRegion;
    if (country.withRegion) {
      this.retrieveRegions(country.id);
    } else {
      this.retrieveCities(country.id, false);
    }
  }

  onRegionChange(value: any) {
    if (!value) {
      this.clearField('cityId');
      return;
    }

    this.clearField('cityId');
    const region = JSON.parse(value);
    this.retrieveCities(region.id, true);
  }

  onSave() {
    (<ElForm>this.$refs.form).validate(passed => {
      if (passed) {
        this.loading = true;
        this.location.adOrganizationId = accountStore.property('adOrganizationId');
        const svc = this.service(this.baseApiUrl)
        let promise: Promise<any>;

        if (this.location.id) {
          promise = svc.update(this.location);
        } else {
          promise = svc.create(this.location);
        }

        promise.then(res => {
          this.$message({
            message: `Location has been ${this.location.id ? 'updated' : 'created'} successfully`
          });
          this.$emit('input', res.id);
          this.$set(this.location, 'name', res.name);
          this.visible = false;
        }).catch(error => {
          console.log('Error saving location', error);
          this.$message('Failed saving the location');
        }).finally(() => {
          this.loading = false;
        });
      }
    });
  }

  private clearField(fieldName) {
    this.$set(this.location, fieldName, null);
  }

  private initForm() {
    if (this.value) {
      this.loading = true;
      this.service(this.baseApiUrl)
        .find(this.value)
        .then(res => {
          this.location = res;
          this.retrieveCity(res.cityId);
        })
        .finally(() => {
          this.loading = false;
        });
    }
  }

  private retrieveCountries() {
    this.service('/api/c-countries')
      .retrieve({
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['name']
        }
      })
      .then(res => {
        this.countries = res.data;
      });
  }

  private retrieveRegions(countryId: number) {
    this.service('/api/c-regions')
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `countryId.equals=${countryId}`
        ],
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['name']
        }
      })
      .then(res => {
        this.regions = res.data;
      });
  }

  private retrieveCities(id: number, withRegion: boolean) {
    this.service('/api/c-cities')
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `${withRegion ? 'regionId' : 'countryId'}.equals=${id}`
        ],
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['name']
        }
      })
      .then(res => {
        this.cities = res.data;
      });
  }

  private retrieveCity(id: number) {
    this.service('/api/c-cities')
      .find(id)
      .then(res => {
        if (res.regionId) {
          this.retrieveCities(res.regionId, true);
          this.retrieveRegions(res.region.countryId);
          this.country = this.jsonEncode(res.region.country, ['id', 'name', 'withRegion']);
          this.region = this.jsonEncode(res.region, ['id', 'name']);
        } else {
          this.retrieveCities(res.countryId, true);
          this.country = this.jsonEncode(res.country, ['id', 'name', 'withRegion']);
        }
      });
  }

  jsonEncode(data: any, fields: string[]) {
    const record: Record<string, any> = {};
    for (const field of fields) {
      record[field] = data[field];
    }
    return JSON.stringify(record);
  }
}