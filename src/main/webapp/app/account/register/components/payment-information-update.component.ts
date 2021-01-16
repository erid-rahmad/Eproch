import Vue from 'vue'
import Component from 'vue-class-component'
import { ElForm } from 'element-ui/types/form'
import { Inject, Watch, Model } from 'vue-property-decorator'
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store';
import DynamicWindowService from '../../../core/application-dictionary/components/DynamicWindow/dynamic-window.service';
//import idb from 'idb';

const PaymentInformationUpdateProps = Vue.extend({
    props: {
        eventBus: {
            type: Object,
            default: () => {}
        },
        pays: {
            type: Object,
            default() {
                return {};
            }
        },
    }
})

@Component
export default class PaymentInformationUpdate extends PaymentInformationUpdateProps {

    @Inject('dynamicWindowService')
    private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;

    public rules = {};
    public banks = [];
    public currencies = [];
    public pay:any = {};
    private limit: number = 1;
    private action: string = "/api/c-attachments/upload";
    private accept: string = ".jpg, .jpeg, .png, .pdf, .doc, .docx";

    @Watch('pays')
    setPayment(payment){
        this.pay = payment;
    }

    created(){
        this.setPayment(this.pays);
    }

    mounted() {
        this.retrieveBank();
        this.retrieveCurrency();
        this.eventBus.$on('save-payment', this.save);
        this.eventBus.$on('reset-payment-form', this.reset);
    }

    beforeDestroy() {
        this.eventBus.$off('save-payment', this.save);
        this.eventBus.$off('reset-payment-form', this.reset);
    }

    private reset() {
        (<ElForm>this.$refs.pay).resetFields();
        this.pay = {};
    }

    private save() {
        (<ElForm>this.$refs.pay).validate((passed, errors) => {
            if (passed) {
                const data = { ...this.pay };
                this.eventBus.$emit('push-payment', data);
                this.reset();
            } else {
                this.eventBus.$emit('payment-validation-failed', {passed, errors});
            }
        });
    }

    private retrieveBank() {
        this.dynamicWindowService('/api/c-banks')
            .retrieve({
                criteriaQuery: 'active.equals=true',
                paginationQuery: {
                    page: 0,
                    size: 1000,
                    sort: ['name']
                }
            })
            .then(res=>{
                this.banks = res.data;
            });
    }

    private retrieveCurrency() {
        this.dynamicWindowService('/api/c-currencies')
            .retrieve({
                criteriaQuery: 'active.equals=true',
                paginationQuery: {
                    page: 0,
                    size: 1000,
                    sort: ['code']
                }
            })
            .then(res=>{
                this.currencies = res.data;
            });

    }

    get fileList() {
        if ( ! this.pay.supportingfile) return [];
        return [this.pay.supportingfile];
    }

    onUploadChange(file: any) {
        this.pay.supportingfile = file;
    }

    handlePreview(file) {
      window.open(file.response.downloadUri, '_blank');
    }

    handleRemove(files, fileList) {
        this.pay.supportingfile = "";
    }

    onUploadError(err: any) {
        console.log('Failed uploading a file ', err);
    }

    onUploadSuccess(response: any) {
        console.log('File uploaded successfully ', response);
        this.pay.fileId = response.attachment.id;
        (<ElForm>this.$refs.pay).validate((passed, errors) => {
            this.pay.supportingfile != '';
        });
    }

    handleExceed(files, fileList) {
        if (fileList.length >= 1) {
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
