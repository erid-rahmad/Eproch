import {
  ElForm
} from 'element-ui/types/form';
import AlertMixin from '@/shared/alert/alert.mixin';
import {
  mixins
} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import Vue from 'vue';
import Component from 'vue-class-component';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
import {
  log
} from 'util';

const BiddingScheduleProp = Vue.extend({
  props: {
    ItemDetail: {
      type: Object,
      default: () => {}
    },
    biddingSchedule: {
      type: Object,
      default: () => {}
    },
    biddingrow: {
      type: Object,
      default: () => {}
    },
  }
})

@Component
export default class BiddingSchedule extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, BiddingScheduleProp) {

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 200,
    height: 200
  };
  rules = {}

  fullscreenLoading = false;
  processing = false;
  dialogConfirmationVisible: boolean = false;

  public eventScheduleOptions: any = {};
  // public projectinformation: any = {};


  private baseApiUrlEventTypeLine = "/api/c-event-typelines";


  projectinformation = [{
    no: '1',
    information: 'Bidding proposal',
    atachment: 'Bidding proposal Doc',
  }];
  dummy = [{
    no: '1',
    dockument: 'Policy Statment - apakah perusahaan memiliki kebijakan K3L dalam menjalankan usahanya ? ',
    dockument1: 'wajib',
    input: 'yes',



  }, {
    no: '1',
    dockument: 'Emergency Response procedure - apakah perusahaan memiliki prosedur tanggap darurat ? ',
    dockument1: 'wajib',
    input: 'yes',

  }, {
    no: '1',
    dockument: 'Basic Safety Rules - apakah perusahaan memiliki peraturan dasar keselamatan kerja ? ',
    dockument1: 'wajib',
    input: 'yes',

  }];
  dummy1 = [{
    no: '1',
    dockument: 'Profesional safety support - Bagaimana penanganan / pengelolaan profesional safety supportt ? ',
    dockument1: 'wajib',
    dockument3: 'yes',


    input: '',
  }, {
    no: '1',
    dockument: 'Enviromental - Sejauh mana perusahaan anda mengelola kebijakan lingkungan kerja ',
    dockument1: 'wajib',
    dockument3: 'yes',

  }, ];
  dummy2 = [{
    no: '1',
    dockument: 'Apakah pengurus telah menetapkan struktur organisasi perusahaan ',
    dockument1: 'wajib',
    dockument3: 'yes',


    input: '',
  }, {
    no: '1',
    dockument: 'apakah pengurus menetapkan kebijakan pengelolaan usaha dan perngendalian kegiatan usaha perusahaan ',
    dockument1: 'wajib',
    dockument3: 'yes',

  }, ];


  private documentSchedule: any = {
    docEvent: "",
    vendorSubmission: "",
    vendorEvaluation: "",
    vendorSubmissionObj: "",
    vendorEvaluationObj: ""
  };

  created() {
    this.eventTypeLine(this.ItemDetail);
    this.projectinformationData();
    console.log("this item detail", this.ItemDetail);
    console.log("this item row", this.biddingrow);

  }

  private projectinformationData() {
    // this.dynamicWindowService(`/api/m-project-informationsby-bidding/${this.biddingrow.id}`)
    this.dynamicWindowService("/api/m-project-informationsby-bidding/1958806")

      .retrieve({
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.projectinformation = [{
            "createdBy": "admin",
            "createdDate": "2021-03-29T03:30:16.507924Z",
            "lastModifiedBy": "admin",
            "lastModifiedDate": "2021-03-29T03:30:16.507924Z",
            "id": 1958906,
            "uid": "7091e357-d56c-4397-a57a-eb28850f431c",
            "active": true,
            "name": "Bidding Proposal",
            "biddingId": 1958806,
            "biddingName": "Pengadaan Kendaraan Operasional",
            "adOrganizationId": 33851,
            "adOrganizationName": "Berca Hardayaperkasa",
            "attachment": {
              "createdBy": "anonymousUser",
              "createdDate": "2021-03-29T03:27:46.923768Z",
              "lastModifiedBy": "anonymousUser",
              "lastModifiedDate": "2021-03-29T03:27:46.923768Z",
              "id": 1958753,
              "type": "LOCAL",
              "fileName": "bhp132572448130081500.xlsx",
              "imageSmall": null,
              "imageMedium": null,
              "imageLarge": null,
              "mimeType": "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
              "documentType": "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
              "uploadDir": null,
              "uid": "effc6c2a-083a-428f-82ec-71a05f344e5f",
              "active": true,
              "adOrganizationId": 1,
              "adOrganizationName": "All"
            },
            "attachmentId": 1958753,
            "attachmentName": "bhp132572448130081500.xlsx"
          },
          {
            "createdBy": "admin",
            "createdDate": "2021-03-29T06:39:52.262102Z",
            "lastModifiedBy": "admin",
            "lastModifiedDate": "2021-03-29T06:39:52.262102Z",
            "id": 1959701,
            "uid": "44480ea2-65f8-4e28-8505-e8e6e78dfb25",
            "active": true,
            "name": "Data Sheet",
            "biddingId": 1958806,
            "biddingName": "Pengadaan Kendaraan Operasional",
            "adOrganizationId": 33851,
            "adOrganizationName": "Berca Hardayaperkasa",
            "attachment": {
              "createdBy": "admin",
              "createdDate": "2021-03-29T06:33:04.461847Z",
              "lastModifiedBy": "admin",
              "lastModifiedDate": "2021-03-29T06:33:04.461847Z",
              "id": 1959604,
              "type": "LOCAL",
              "fileName": "1616999584452_B Data Sheet.pdf.pdf",
              "imageSmall": null,
              "imageMedium": null,
              "imageLarge": null,
              "mimeType": "application/pdf",
              "documentType": "application/pdf",
              "uploadDir": null,
              "uid": "cfc99aa7-ce50-4e73-8cdb-c316972d1173",
              "active": true,
              "adOrganizationId": 1,
              "adOrganizationName": "All"
            },
            "attachmentId": 1959604,
            "attachmentName": "1616999584452_B Data Sheet.pdf.pdf"
          },
          {
            "createdBy": "admin",
            "createdDate": "2021-03-29T06:39:52.266820Z",
            "lastModifiedBy": "admin",
            "lastModifiedDate": "2021-03-29T06:39:52.266820Z",
            "id": 1959702,
            "uid": "718c5c47-f093-4b89-acf5-9ac0d8ec20f6",
            "active": true,
            "name": "Scope of Work",
            "biddingId": 1958806,
            "biddingName": "Pengadaan Kendaraan Operasional",
            "adOrganizationId": 33851,
            "adOrganizationName": "Berca Hardayaperkasa",
            "attachment": {
              "createdBy": "admin",
              "createdDate": "2021-03-29T06:33:24.175234Z",
              "lastModifiedBy": "admin",
              "lastModifiedDate": "2021-03-29T06:33:24.175234Z",
              "id": 1959605,
              "type": "LOCAL",
              "fileName": "1616999604171_B Scope of Work.pdf.pdf",
              "imageSmall": null,
              "imageMedium": null,
              "imageLarge": null,
              "mimeType": "application/pdf",
              "documentType": "application/pdf",
              "uploadDir": null,
              "uid": "8e0c67d7-f490-465f-93de-9b8251e8b31d",
              "active": true,
              "adOrganizationId": 1,
              "adOrganizationName": "All"
            },
            "attachmentId": 1959605,
            "attachmentName": "1616999604171_B Scope of Work.pdf.pdf"
          },

        ];
        this.projectinformation = res.data;


        console.log("this more information", this.projectinformation);
      });
  }


  downloadAttachment(row) {
    console.log(row);
    window.open(`/api/c-attachments/download/${row.attachmentid}-${row.attachmentName}`, '_blank');
  }

  private eventTypeLine(eventTypeId): void {
    if (!this.baseApiUrlEventTypeLine) {
      return;
    }

    this.dynamicWindowService(this.baseApiUrlEventTypeLine)
      .retrieve({
        criteriaQuery: `eventTypeId.equals=${eventTypeId}&sort=sequence`
      })
      .then(res => {

        this.$set(this.biddingSchedule, 'eventSchedule', res.data.map((item: any) => {
          return item;
        }));

        this.eventScheduleOptions = this.biddingSchedule.eventSchedule;
        //console.log(this.biddingInformation);

      })
      .catch(err => {
        console.error('Failed getting the record. %O', err);
        this.$message({
          type: 'error',
          message: err.detail || err.message
        });
      })
      .finally(() => {
        this.fullscreenLoading = false;
      });
  }

  addDocument() {
    this.dialogConfirmationVisible = true;
  }

  removeDocument(index) {
    this.biddingSchedule.documentSchedule.splice(index, 1);
  }

  saveDocument() {

    this.documentSchedule.vendorSubmissionObj = this.eventScheduleOptions.find(item => item.id === this.documentSchedule.vendorSubmission);
    this.documentSchedule.vendorEvaluationObj = this.eventScheduleOptions.find(item => item.id === this.documentSchedule.vendorEvaluation);

    //console.log(this.documentSchedule);
    //console.log(this.biddingSchedule);

    this.biddingSchedule.documentSchedule.push(this.documentSchedule);
    this.dialogConfirmationVisible = false;
    this.documentSchedule = {
      docEvent: "",
      vendorSubmission: "",
      vendorEvaluation: "",
      vendorSubmissionObj: "",
      vendorEvaluationObj: ""
    };
  }

  //=======================================================================

  validate() {
    (this.$refs.productCatalog as ElForm).validate((passed, errors) => {
      if (passed) {
        //this.submit();
      } else {
        console.log(errors);
      }

    });
  }

}
