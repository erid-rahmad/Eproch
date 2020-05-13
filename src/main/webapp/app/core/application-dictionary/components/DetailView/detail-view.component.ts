import Component from 'vue-class-component';
import { Vue, Inject } from 'vue-property-decorator';
import VueFormJsonSchema from 'vue-form-json-schema'
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';

@Component({
  components: {
    VueFormJsonSchema
  }
})
export default class DetailView extends Vue {
  private baseApiUrl: string = '';

  @Inject('dynamicWindowService')
  private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;
  
  model = {};
  state = {};
  valid = false;
  schema = {};
  uiSchema: Array<any> = [];
  options = {
    castToSchemaType: true
  };
  loaded = false;
  delay = 500;

  created() {
    this.baseApiUrl = this.$route.meta.baseApiUrl;
    this.getForm();
  }

  onChangeState(value: object) {
    this.state = value;
  }

  onValidated(value: boolean) {
    this.valid = value;
  }

  private async getForm() {
    // Reset properties
    this.uiSchema = [];
    this.schema = {};
    this.model = {};

    // Set form as not loaded
    this.loaded = false;

    // Get the data from the API
    const schemaId = this.$route.meta?.schemaId;
    const service = this.dynamicWindowService(this.baseApiUrl);
    return Promise.all([
      service.getUiSchemaFromAPI(schemaId),
      service.getSchemaFromAPI(schemaId),
      service.getDataFromAPI(schemaId)
    ]).then(([uiSchema, schema, model]) => {
      // Update the form properties with data from the API
      this.uiSchema = uiSchema as Array<any>
      this.schema = schema as object
      this.model = model as object

      // Set form as loaded
      this.loaded = true;
    })
  }

}