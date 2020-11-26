import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store';
import { ElForm } from 'element-ui/types/form';
import Vue from 'vue';
import Component from 'vue-class-component';
import { Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../../core/application-dictionary/components/DynamicWindow/dynamic-window.service';

const CompanyProps = Vue.extend({
  props: {
    eventBus: {
      type: Object,
      default: () => {}
    },
    company: {
      type: Object,
      default: () => {
        return null;
      }
    },

  }
})

@Component
export default class CompanyProfile extends CompanyProps {

  @Inject('dynamicWindowService')
  private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;

  public countryOptions: any = {};
  public regionOptions: any = {};
  public cityOptions: any = {};

  public countryOptionsNpwp: any = {};
  public regionOptionsNpwp: any = {};
  public cityOptionsNpwp: any = {};

  public typeOptions: any = {};
  public locationOptions: any = {};
  public companyType: string = "companyType";
  public companyLocation: string = "companyLocation";
  public companyTaxLocation: string = "DMS";

  private limit: number = 1;
  private action: string = "/api/c-attachments/upload";
  private accept: string = ".jpg, .jpeg, .png, .pdf, .doc, .docx";

  private npwp: boolean = true;
  private tax: boolean = false;
  private professional: boolean = false;
  private sameAddress: boolean = true;
  private withRegion1: boolean = true;
  private withRegion2: boolean = true;
  private columnSpacing = 32;
  private rules = {
    website: {
      type: 'url'
    }
  }

  created() {
    this.retrieveReferences(this.companyType);
    this.retrieveReferences(this.companyLocation);
    this.retrieveCountry();
  }

  mounted() {
    this.eventBus.$on('validate-form', this.validate)
  }

  public handleTypeChange(value: string) {
    registrationStore.setVendorType(value);
    console.log('type changed', value);
    if (value.toUpperCase() === 'PRF') {
      this.professional = true;
    } else {
      this.professional = false;
    }
    console.log('isProfessional', this.professional);
  }

  public handleLocationChange(value: string){
    registrationStore.setVendorLocation(value);
    this.tax = true;
    if(value == this.companyTaxLocation){
      this.npwp = true;
    }else{
      this.npwp = false;
    }
  }

  validate(formIndex: number) {
    if (formIndex === 1) {
      (this.$refs.company as ElForm).validate((passed, errors) => {
        this.company.countryName = this.getJsonField(this.company.country, 'name');
        this.company.regionName = this.getJsonField(this.company.region, 'name');
        this.company.cityName = this.getJsonField(this.company.city, 'name');
        this.company.cityId = this.getJsonField(this.company.city, 'id');

        this.company.npwpCountryName = this.getJsonField(this.company.npwpCountry, 'name');
        this.company.npwpRegionName = this.getJsonField(this.company.npwpRegion, 'name');
        this.company.npwpCityName = this.getJsonField(this.company.npwpCity, 'name');
        this.company.npwpCityId = this.getJsonField(this.company.npwpCity, 'id');

        this.company.sameAddress = this.sameAddress;

        if (this.companyTaxLocation == this.company.location) {
          this.company.tin = "";
        } else {
          this.company.npwp = "";
          this.company.npwpName = "";
          this.company.file = "";
          this.company.fileId = "";
        }

        this.eventBus.$emit('step-validated', { passed, errors })
      });
    }
  }

  jsonEncode(data: any, fields: string[]) {
    const record: Record<string, any> = {};
    for (const field of fields) {
      record[field] = data[field];
    }
    return JSON.stringify(record);
  }

  private getJsonField(json: string, field: string) {
    if (json) {
      const data = JSON.parse(json);
      return data[field];
    }
    return null;
  }

  onAddressChange(value: string) {
    if (this.sameAddress) {
      this.company.npwpAddress = value;
    }
  }

  onCityChange(value: string) {
    if (this.sameAddress) {
      this.company.npwpCity = value;
    }
  }

  onPostalCodeChange(value: string) {
    if (this.sameAddress) {
      this.company.npwpPostalCode = value;
    }
  }

  onSameAddressChange(value: boolean) {
    if (value) {
      this.company.npwpAddress = this.company.address;
      this.company.npwpCountry = this.company.country;
      this.company.npwpPostalCode = this.company.postalCode;

      this.withRegion2 = this.withRegion1;
      this.company.npwpRegion = this.company.region;
      this.regionOptionsNpwp = this.regionOptions;

      this.company.npwpCity = this.company.city;
      this.cityOptionsNpwp = this.cityOptions;
    }
  }

  private retrieveReferences(param: string) {
    this.dynamicWindowService('/api/ad-references')
      .retrieve({
        criteriaQuery: [`value.contains=` + param]
      })
      .then(res => {
        let references = res.data.map(item => {
          return {
            id: item.id,
            value: item.value,
            name: item.name
          };
        });
        this.retrieveReferenceLists(references);
      });
  }

  private retrieveReferenceLists(param: any) {
    this.dynamicWindowService('/api/ad-reference-lists')
      .retrieve({
        criteriaQuery: [`adReferenceId.equals=` + param[0].id]
      })
      .then(res => {
        let referenceList = res.data.map(item => {
          return {
            key: item.value,
            value: item.name
          };
        });

        if (param[0].value == this.companyType) {
          this.typeOptions = referenceList;
        } else if (param[0].value == this.companyLocation) {
          this.locationOptions = referenceList;
        }
      });
  }

  private retrieveCountry() {
    this.dynamicWindowService('/api/c-countries')
      .retrieve({
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['name']
        }
      })
      .then(res => {
        this.countryOptions = res.data;
      });
  }

  /**
   * Fetch the region for the selected country.
   * @param value JSON-encoded value contains: id, name, withRegion
   * @param i 1 for the main address, 2 for the NPWP address.
   */
  retrieveRegion(value: string, i: number) {
    if (!value) {
      return;
    }

    const country = JSON.parse(value);
    this[`withRegion${i}`] = country.withRegion;
    this.onCountryClear(i);

    if (this.sameAddress) {
      this.company.npwpCountry = value;
      this.withRegion2 = this.withRegion1;
    }

    // Country without region should immediatelly retrieve the related cities.
    if (!this[`withRegion${i}`]) {
      this.retrieveCity(null, country.id, i);
      return;
    }

    if (i === 1) {
      this.company.region = '';
      this.company.city = '';
    } else {
      this.company.npwpRegion = '';
      this.company.npwpCity = '';
    }

    if (this.sameAddress) {
      this.company.npwpCountry = value;
    }

    this.dynamicWindowService('/api/c-regions')
      .retrieve({
        criteriaQuery: [`countryId.equals=${country.id}`],
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['name']
        }
      })
      .then(res => {
        if (i === 1) {
          this.regionOptions = res.data;
        }
        if (i === 2 || this.sameAddress) {
          this.regionOptionsNpwp = res.data;
        }
      });
  }

  private retrieveCity(value: string, countryId: number, i: number) {
    if (!countryId && !value) {
      return;
    }

    let filterQuery: string;
    this.onRegionClear(i);

    if (countryId) {
      filterQuery = `countryId.equals=${countryId}`;
    } else if (value !== null) {
      const region = JSON.parse(value);
      filterQuery = `regionId.equals=${region.id}`;
    }

    if (i === 1) {
      this.$set(this.company, 'city', null);
      if (this.sameAddress)
        this.$set(this.company, 'npwpCity', null);
    } else {
      this.$set(this.company, 'npwpCity', null);
    }

    if (this.sameAddress) {
      this.company.npwpRegion = value;
    }

    this.dynamicWindowService('/api/c-cities')
      .retrieve({
        criteriaQuery: filterQuery || null,
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['name']
        }
      })
      .then(res => {
        if (i === 1) {
          this.cityOptions = res.data;
        }
        if (i === 2 || this.sameAddress) {
          this.cityOptionsNpwp = res.data;
        }
      });
  }

  onCountryClear(i: number) {
    if (i === 1) {
      this.clearField('region');
      this.onRegionClear(1);
      this.$set(this.company, 'region', null);
      this.$set(this.company, 'city', null);

      if (this.sameAddress) {
        this.clearField('npwpCountry');
        this.onCountryClear(2);
      }
    } else {
      this.onRegionClear(2);
    }
  }

  onRegionClear(i: number) {
    if (i === 1) {
      if (this.sameAddress) {
        this.clearField('npwpRegion');
      }

      this.clearField('city');
      this.onCityClear();
    } else {
      this.clearField('npwpCity');
    }
  }

  onCityClear() {
    if (this.sameAddress) {
      this.clearField('npwpCity');
    }
  }

  private clearField(field: string) {
    this.$set(this.company, field, null);
  }

  get fileList() {
    if ( ! this.company.file) return [];
    return [this.company.file];
  }

  onUploadChange(file: any) {
    this.company.file = file;
  }

  handleRemove(files, fileList) {
    this.company.file = "";
  }

  onUploadError(err: any) {
    console.log('Failed uploading a file ', err);
  }

  onUploadSuccess(response: any) {
      console.log('File uploaded successfully ', response);
      this.company.fileId = response.attachment.id;
      //(this.$refs.company as ElForm).validate(this.company.file);
      (this.$refs.company as ElForm).validate((passed, errors) => {
        this.company.file != '';
      });
  }

  handleExceed(files, fileList) {
    if (files.length >= 1) {
      this.$notify({
        title: 'Warning',
        message: "Up to 1 files are allowed",
        type: 'warning',
        duration: 3000
      });
      return false;
    }
  }

  handleBeforeUpload(file: any) {
    // File size limitation
    const isLt5M = file.size / 1024 / 1024 < 5;
    if (!isLt5M) {
      this.$notify({
          title: 'Warning',
          message: "files with a size less than 5Mb",
          type: 'warning',
          duration: 3000
      });
      return isLt5M;
    }

    // File type restriction
    const name = file.name ? file.name : '';
    const ext = name
      ? name.substr(name.lastIndexOf('.') + 1, name.length)
      : true;
    const isExt = this.accept.indexOf(ext) < 0;
    if (isExt) {
      this.$notify({
        title: 'Warning',
        message: "Please upload the correct format type",
        type: 'warning',
        duration: 3000
      });
      return !isExt;
    }

  }

}
