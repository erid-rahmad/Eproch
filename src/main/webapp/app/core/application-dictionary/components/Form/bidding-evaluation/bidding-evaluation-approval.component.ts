import DocumentActionButton from '@/core/application-dictionary/components/DocumentAction/document-action-button.vue';
import DocumentActionConfirm from '@/core/application-dictionary/components/DocumentAction/document-action-confirm.vue';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { Component, Inject, Mixins } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';

@Component({
  components: {
    DocumentActionButton,
    DocumentActionConfirm,
  }
})
export default class BiddingEvaluationApproval extends Mixins(AccessLevelMixin) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  index: boolean = true;
  refresh: string = "";
  private tabTitleOptions = [];
  setRows = [];
  setRow = {};

  selectedDocumentAction: any = {};
  showDocumentActionConfirm = false;

  vendorScorings = [
    {
      evaluationEvent: 'Bidding Evaluation',
      criteria: 'Quality',
      subCriteria: 'Quality',
      percentage: 20,
      picUserName: 'Admin Quality',
    },
    {
      evaluationEvent: 'Bidding Evaluation',
      criteria: 'Cost',
      subCriteria: 'Cost',
      percentage: 40,
      picUserName: 'Admin Cost',
    },
    {
      evaluationEvent: 'Bidding Evaluation',
      criteria: 'Delivery',
      subCriteria: 'Timeline',
      percentage: 15,
      picUserName: 'Admin Timeline',
    },
    {
      evaluationEvent: 'Bidding Evaluation',
      criteria: 'Safety',
      subCriteria: 'Packaging',
      percentage: 15,
      picUserName: 'Admin Packaging',
    },
    {
      evaluationEvent: 'Bidding Evaluation',
      criteria: 'Morale',
      subCriteria: 'Morale',
      percentage: 10,
      picUserName: 'Admin Morale',
    },
  ];

  supportingDocument = {
    picUserName: 'Admin Tender',
    fileList: []
  }

  evaluationResult = [
    {
      vendorName: 'WESTCON INTERNATIONAL INDONESIA',
      proposedPrice: 29450000000,
      priceRanking: 3,
      attachments: [
        {
          id: 1000,
          documentName: 'Proposal Teknis'
        },
        {
          id: 2000,
          documentName: 'Quotation'
        }
      ],
      qualityScore: 2,
      costScore: 1,
      timelineScore: 2,
      packagingScore: 2,
      moraleScore: 2,
      totalScore: 9,
      scoreRanking: 3
    },
    {
      vendorName: 'SISTECH KHARISMA',
      proposedPrice: 29310000000,
      priceRanking: 1,
      attachments: [
        {
          id: 1000,
          documentName: 'Proposal Teknis'
        },
        {
          id: 2000,
          documentName: 'Quotation'
        }
      ],
      qualityScore: 3,
      costScore: 3,
      timelineScore: 2,
      packagingScore: 3,
      moraleScore: 3,
      totalScore: 14,
      scoreRanking: 1
    },
    {
      vendorName: 'INGRAM MICRO INDONESIA',
      proposedPrice: 29400000000,
      priceRanking: 2,
      attachments: [
        {
          id: 1000,
          documentName: 'Proposal Teknis'
        },
        {
          id: 2000,
          documentName: 'Quotation'
        }
      ],
      qualityScore: 2,
      costScore: 2,
      timelineScore: 2,
      packagingScore: 2,
      moraleScore: 2,
      totalScore: 10,
      scoreRanking: 2
    },
  ];
  gridData = [{
    date: '2016-05-02',
    name: 'John Smith',
    address: 'No.1518,  Jinshajiang Road, Putuo District'
  }, {
    date: '2016-05-04',
    name: 'John Smith',
    address: 'No.1518,  Jinshajiang Road, Putuo District'
  }, {
    date: '2016-05-01',
    name: 'John Smith',
    address: 'No.1518,  Jinshajiang Road, Putuo District'
  }, {
    date: '2016-05-03',
    name: 'John Smith',
    address: 'No.1518,  Jinshajiang Road, Putuo District'
  }];

  dialogTableVisible = false;
  dialogFormVisible = false;

  private baseApiUrlCatalog = "/api/m-product-catalogs";
  private baseApiUrlReference = "/api/ad-references";
  private baseApiUrlReferenceList = "/api/ad-reference-lists";
  private keyReferenceProductCatalog: string = "docStatProductCatalog";

  dialogTableVisible1: false;
  activeName = 'ALL';
  dialogConfirmationVisible: boolean = false;
  dialogTitle: string = "";
  dialogType: string = "";
  dialogMessage: string = "";
  dialogButtonIcon: string = "";
  dialogButtonType: string = "";
  dialogButton: string = "";

  importHeaders = {};
  importProductCatalog = "";
  bookType = "";

  bidding = {
    documentNo: 'BN-00001',
    documentTypeId: null,
    documentTypeName: null,
    name: 'Pengadaan Kendaraan Operasional',
    biddingTypeName: 'Tender Goods',
    adUserUserName: 'Admin Tender',
    costCenterName: 'Marketing',
    remark: null
  }

  get approved() {
    return false;
  }

  get defaultDocumentAction() {
    return 'APV';
  }

  onFileListChanged(_file, fileList) {
    this.$set(this.supportingDocument, 'fileList', fileList);
  }

  created() {
    const token = localStorage.getItem('jhi-authenticationToken') || sessionStorage.getItem('jhi-authenticationToken');
    this.importHeaders['Authorization'] = `Bearer ${token}`;
    this.retrieveDocumentType('Bidding Evaluation');
  }

  handleClick(tab, event) {
    //console.log(tab)
    this.activeName = tab.name;

  }

  onClick(value: string) {
    this.dialogType = value;

    if (value == 'import') {
      this.dialogConfirmationVisible = true;
      this.dialogTitle = "Import Catalog";
      this.dialogButtonIcon = "el-icon-upload2";
      this.dialogButtonType = "primary";
      this.dialogButton = "Import";

    } else if (value == 'export') {
      this.dialogConfirmationVisible = true;
      this.dialogTitle = "Export Catalog";
      this.dialogButtonIcon = "el-icon-download";
      this.dialogButtonType = "primary";
      this.dialogButton = "Export";

    } else if (value == 'filter') {
      this.activeName = "ALL";
      ( < any > this.$refs.catalogGrid[0]).retrieveAllRecords();
    } else if (value == 'add') {
      this.index = false;
      this.setRow = {};

    } else {
      if (this.setRows.length) {
        if (value == "remove") {
          this.dialogConfirmationVisible = true;
          this.dialogTitle = "Remove Catalog";
          this.dialogMessage = "Are you sure to delete the selected record(s)?";
          this.dialogButtonIcon = "el-icon-delete";
          this.dialogButtonType = "danger";
          this.dialogButton = "Remove";

        }
      } else {
        const message = "Please select at least one item";
        this.$notify({
          title: 'Warning',
          message: message.toString(),
          type: 'warning',
          duration: 3000
        });
      }
    }

  }

  onDocumentActionChanged(action: any) {
    this.selectedDocumentAction = action;
    this.showDocumentActionConfirm = true;
  }

  private retrieveDocumentType(name: string) {
    this.commonService('/api/c-document-types')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `name.equals=${name}`
        ]),
        paginationQuery: {
          page: 0,
          size: 1,
          sort: []
        }
      })
      .then(res => {
        if (res.data.length) {
          this.bidding.documentTypeId = res.data[0].id;
          this.bidding.documentTypeName = res.data[0].name;
        }
      })
  }

  selectedRow(value) {
    this.index = false;
    this.setRow = value;
  }

  selectedRows(value) {
    this.setRows = value;
  }
}
