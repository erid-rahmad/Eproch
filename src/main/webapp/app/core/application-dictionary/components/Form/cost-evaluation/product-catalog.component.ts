import AlertMixin from '@/shared/alert/alert.mixin';
import {
  mixins
} from 'vue-class-component';
import {
  Component
} from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";
import CatalogGrid from './components/catalog-grid.vue';
import ProductInformation from './product-information.vue';

@Component({
  components: {
    CatalogGrid,
    ProductInformation
  }
})
export default class Catalog extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor) {
  index: boolean = true;
  refresh: string = "";
  private tabTitleOptions = [];
  setRows = [];
  setRow = {};

  data() {
    return {
      tableData: [
        {
          0:'CE-0001',
          1: 'BN -00001',
          2: 'Pengaandaan Kendaraan Operasional',
          3: 'Tender Goods',
          4: 'View Schejule',
          5: 'In progres',
          re: '3',
        7: '22/22/2021',
        8: 'Admin Tender'
        }, {
          0:'CE-0002',
          1: 'BN -00002',
          2: 'Pengaandaan Office Supply',
          3: 'Tender Goods',
          4: 'View Schejule',
          5: 'In progres',
          re: '5',
        7: '22/22/2021',
        8: 'Admin Tender'
        }, {
          0:'CE-0003',
          1: 'BN -00003',
          2: 'Pengaandaan Office Equipment',
          3: 'Tender Goods',
          4: 'View Schejule',
          5: 'Submitted',
          re: '4',
        7: '22/22/2021',
        8: 'Admin Tender'
        }, {
          0:'CE-0004',
          1: 'BN -00004',
          2: 'Pengaandaan Kendaraan Jabatan',
          3: 'Tender Goods',
          4: 'View Schejule',
          5: 'Submitted',
          re: '3',
        7: '22/22/2021',
        8: 'Admin Tender'
        },
      ],
      gridData: [{
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
      }],
      dialogTableVisible: false,

      dialogFormVisible: false,
    }
  };


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

  private accept: string = ".csv";
  /*public chooseBookTypeImport = [
    { id: '1', type: '.csv', name: "CSV" },
    { id: '2', type: '.xls', name: "XLS" },
  ]*/

  created() {
    const token = localStorage.getItem('jhi-authenticationToken') || sessionStorage.getItem('jhi-authenticationToken');
    this.importHeaders['Authorization'] = `Bearer ${token}`;

    this.retrieveGetReferences(this.keyReferenceProductCatalog);
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

  dialogButtonAction(dialogType) {
    if (dialogType == "remove") {
      //console.log(this.setRows);
      this.removeCatalog();
    }

  }

  closeProductInformation() {
    this.index = true;
    this.setRows = [];
    this.setRow = {};
  }

  private retrieveGetReferences(param: string) {
    this.dynamicWindowService(this.baseApiUrlReference)
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
        this.retrieveGetReferenceLists(references);
      });
  }

  private retrieveGetReferenceLists(param: any) {
    this.dynamicWindowService(this.baseApiUrlReferenceList)
      .retrieve({
        criteriaQuery: [`adReferenceId.equals=` + param[0].id]
      })
      .then(res => {
        let referenceList = res.data.map(item => {
          return {
            value: item.value,
            name: item.name
          };
        });

        if (param[0].value == this.keyReferenceProductCatalog) {
          this.tabTitleOptions = referenceList;
        }
      });
  }

  selectedRow(value) {
    this.index = false;
    this.setRow = value;
  }

  selectedRows(value) {
    this.setRows = value;
  }

  //handle import
  submitImport() {
    ( < any > this.$refs.import).submit();
  }

  handleImportError(err, file, fileList) {
    const message = "Import catalog error";
    this.$notify({
      title: 'Error',
      message: message.toString(),
      type: 'error',
      duration: 3000
    });
  }

  onUploadChange(file: any) {
    this.importProductCatalog = file;
  }

  handlePreview(file) {
    window.open(file.response.downloadUri, '_blank');
  }

  handleRemove(files, fileList) {
    this.importProductCatalog = "";
  }

  onUploadError(err: any) {
    console.log('Failed uploading a file ', err);
  }

  onUploadSuccess(response: any) {
    console.log('File uploaded successfully ', response);

    this.importProductCatalog = "";
    this.dialogConfirmationVisible = false;
    this.activeName = "ALL";
    ( < any > this.$refs.catalogGrid[0]).retrieveAllRecords();
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
    const ext = name ?
      name.substr(name.lastIndexOf('.') + 1, name.length) :
      true;
    /*const isExt = this.chooseBookTypeImport.indexOf(ext) < 0;
    if (isExt) {
      this.$notify({
        title: 'Warning',
        message: "Please upload the correct format type",
        type: 'warning',
        duration: 3000
      });
      return !isExt;
    }*/

  }

  removeCatalog() {
    Promise.allSettled(this.setRows.map((row: any) => {
        return this.dynamicWindowService(this.baseApiUrlCatalog).delete(row.id);
      })).then((results) => {
        const deletedCount = results.filter(res => res.status === 'fulfilled').length

        if (deletedCount) {
          const message = this.$t(`opusWebApp.applicationDictionary.recordsDeleted`, {
            tabName: "Product Catalog",
            count: deletedCount
          });

          //this.retrieveAllRecords();
          this.importProductCatalog = "";
          this.dialogConfirmationVisible = false;
          this.activeName = "ALL";
          ( < any > this.$refs.catalogGrid[0]).retrieveAllRecords();

          this.$notify({
            title: 'Success',
            message: message.toString(),
            type: 'success',
            duration: 3000
          });
        } else {
          const rejectedCount = results.filter(res => res.status === 'rejected').length;
          if (rejectedCount) {
            console.log('Failed deleting the record(s)');
            this.$notify({
              title: 'Error',
              message: 'Failed deleting the record(s)',
              type: 'error'
            })
          }
        }
      })
      .finally(() => {
        this.setRows = [];
        this.$emit('rows-deleted');
      });
  }

}
