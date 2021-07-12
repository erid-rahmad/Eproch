import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { Component, Mixins, Vue, Watch, Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import settings from '@/settings';

const BlacklistDetailProp = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
});

@Component
export default class VendorBlacklistDetail extends Mixins(AccessLevelMixin, BlacklistDetailProp) {
  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  private limit: number = 1;
  private action: string = "/api/c-attachments/upload";
  private accept: string = ".jpg, .jpeg, .png, .pdf";
  private file: any = {};

  fileList: any[] = [];
  
  //---------------------

  columnSpacing = 24;

  mainForm :any = {
    documentAction: null,
    documentStatus: null
  };

  filterPersonal = {
    id: null,
    username: null
  };

  filterShareHolder = {
    id: null,
    username: null
  };
  
  blacklistTypes = [
    {
      id: 1,
      name: 'Blacklist',
      value: 'B'
    },
    {
      id: 2,
      name: 'Whitelist',
      value: 'W'
    }
  ];

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  get readOnly() {
    return this.mainForm.documentStatus === 'RVW';
  }

  vendors = [];
  categoryOptions: any[] = [];

  userOptions:any = [];
  shareholderOptions = [];

  loadingVendorList = false;

  //-------------------------
  
  @Watch('data')
  onDataChanged(data: any) {
    this.mainForm = data;
  }

  created(){
    this.onDataChanged(this.data);

    if(!!(<any>this.mainForm).id) {
      this.updateVendorList();
      this.onVendorChange();
      this.loadLine();

      if(this.mainForm.attachmentId){
        this.fileList.push({ "name": this.mainForm.fileName, "url": this.mainForm.downloadUrl });
      }
    }

    this.commonService("/api/c-business-categories")
    .retrieve({
      criteriaQuery: [
        'active.equals=true'
      ],
      paginationQuery: {
        page: 0,
        size: 1000,
        sort:['id']
      }
    })
    .then(res => {
      this.categoryOptions = res.data.map((item: any) => {
        return {
          key: item.id,
          value: item.name,
          code: item.code
        };
      });
    })
    .catch(err => {
      console.error('Failed getting the record. %O', err);
      this.$message({
        type: 'error',
        message: err.detail || err.message
      });
    });
  }

  loadLine(){
    this.commonService("/api/m-blacklist-lines").retrieve({
      criteriaQuery: [
        'active.equals=true',
        `blacklistId.equals=${this.mainForm.id}`
      ],
      paginationQuery: {
        page: 0,
        size: 1000,
        sort:['id']
      }
    }).then((res)=>{
      console.log(res.data);
      this.mainForm.users = (<any[]>res.data).filter((elem)=>{
        return elem.functionaryId==null;
      })
      
      this.mainForm.shareholders = (<any[]>res.data).filter((elem)=>{
        return elem.picId==null;
      })

      this.mainForm.users.forEach((elem)=>{
        elem.picName = `${elem.picFirstName} ${elem.picLastName}`;
        return elem;
      });
    })
  }

  updateVendorList(): void {
    this.loadingVendorList = true;
    const filterQuery = [
      'active.equals=true'
    ];

    if (!!(<any>this.mainForm).businessCategoryId) {
      console.log((<any>this.mainForm).businessCategoryId)
      filterQuery.push(`businessCategoryId.equals=${(<any>this.mainForm).businessCategoryId}`);
    }

    if (!!(<any>this.mainForm).subBusinessCategoryId) {
      console.log((<any>this.mainForm).subBusinessCategoryId)
      filterQuery.push(`subBusinessCategoryId.equals=${(<any>this.mainForm).subBusinessCategoryId}`)
    }

    this.commonService('/api/c-vendor-business-cats')
      .retrieve({
        criteriaQuery: filterQuery,
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['vendorId']
        }
      })
      .then(res => {
        let vendorIds:number[] = res.data.map((item)=>{return item.vendorId});
        let uniqueIds = [...new Set(vendorIds)];

        let vendorFilter: any[] = ['active.equals=true'];
        uniqueIds.forEach((id)=>{
          vendorFilter.push(`id.in=${id}`)
        });

        this.commonService("/api/c-vendors").retrieve({
          criteriaQuery: vendorFilter,
          paginationQuery: {
            page: 0,
            size: 1000,
            sort: ['id']
          }
        })
        .then(res => {
          this.vendors = res.data.map((item: any) => {
            return {
              key: item.id,
              value: item.name,
              code: item.code
            };
          });
          this.loadingVendorList = false
        })
        .catch(err => {
          console.error('Failed getting the record. %O', err);
          this.$message({
            type: 'error',
            message: err.detail || err.message
          });
          this.loadingVendorList = false
        });
      })
      .catch(err => {
        console.log('Failed to get vendor from categories. %O', err);
        this.$message.error('Failed to get vendor from categories');
        this.loadingVendorList = false
      });
  }

  onVendorChange() {
    if(!!(<any>this.mainForm).vendorId){
      this.commonService("api/ad-users").retrieve({
        criteriaQuery: [
          'active.equals=true',
          `cVendorId.equals=${(<any>this.mainForm).vendorId}`
        ],
        paginationQuery: {
          page: 0,
          size: 1000,
          sort:['id']
        }
      }).then((res)=>{
        console.log(res.data);
        this.userOptions = res.data.map((item: any) => {
          return {
            key: item.id,
            value: item.name,
            code: item.id
          };
        });
      })
      this.commonService("api/c-functionaries").retrieve({
        criteriaQuery: [
          'active.equals=true',
          `vendorId.equals=${(<any>this.mainForm).vendorId}`
        ],
        paginationQuery: {
          page: 0,
          size: 1000,
          sort:['id']
        }
      }).then((res)=>{
        console.log(res.data);
        this.shareholderOptions = res.data.map((item: any) => {
          return {
            key: item.id,
            value: item.name,
            code: item.id
          };
        });
      })
    } else {
      this.shareholderOptions = [];
      this.userOptions = [];
    }
  }

  addPersonal() {    
    if(!this.mainForm.users) this.mainForm.users = [];

    if(!this.filterPersonal.id) {
      this.$message.warning("Pilih PIC terlebih dahulu.")
      return;
    }
    let dupeCheck = this.mainForm.users.find((elem)=>elem.picId === this.filterPersonal.id);
    if(dupeCheck){
      this.$message.warning("PIC sudah dimasukkan ke daftar.")
      return;
    }

    this.mainForm.users.push({
      notes: null,
      adOrganizationId: this.mainForm.adOrganizationId,
      picId: this.filterPersonal.id,
      picName: this.filterPersonal.username
    })
    this.filterPersonal = {id: null, username: null};
  }

  addShareholder() {
    if(!this.mainForm.shareholders) this.mainForm.shareholders = [];

    if(!this.filterShareHolder.id) {
      this.$message.warning("Pilih PIC terlebih dahulu.")
      return;
    }
    let dupeCheck = this.mainForm.shareholders.find(elem=>elem.functionaryId === this.filterShareHolder.id);
    if(dupeCheck){
      this.$message.warning("PIC sudah dimasukkan ke daftar.")
      return;
    }

    this.mainForm.shareholders.push({
      notes: null,
      adOrganizationId: this.mainForm.adOrganizationId,
      functionaryId: this.filterShareHolder.id,
      functionaryName: this.filterShareHolder.username
    })
    this.filterShareHolder = {id: null, username: null};
  }

  assignName(isPersonal?:boolean){
    if(isPersonal){
      this.filterPersonal.username = this.userOptions.find(user => user.key === this.filterPersonal.id)?.value;
    } else{
      this.filterShareHolder.username = this.shareholderOptions.find(user => user.key === this.filterShareHolder.id)?.value;
    } 
  }

  removeRow(row: any, index: number, isPersonal?:boolean){
    console.log(row, index);
    if(!!row.id){
      if(!this.mainForm.deleteLineIds) this.mainForm.deleteLineIds = [];
      this.mainForm.deleteLineIds.push(row.id);
    }

    if(isPersonal){
      this.mainForm.users.splice(index, 1);
    } else this.mainForm.shareholders.splice(index, 1);
  }

  //-------------------------
  
  onUploadChange(file: any) {
    this.file = file;
    this.fileList = [file];
  }

  handlePreview(file) {
    window.open(file.response?file.response.downloadUri:file.url, '_blank');
  }

  handleRemove(files, fileList) {
    this.file = {};
    this.fileList = [];
    this.mainForm.attachment = null;
    this.mainForm.attachmentId = null;
  }

  onUploadError(err: any) {
    console.log('Failed uploading a file ', err);
    this.$notify({
      title: 'Error',
      message: "Failed uploading a file",
      type: 'error',
      duration: 3000
    });
  }

  onUploadSuccess(response: any, file) {
      console.log('File uploaded successfully ', response);
      this.mainForm.attachment = response.attachment;
      this.mainForm.attachmentId = response.attachment.id;
      this.file = file;
      this.fileList = [file];
  }

  handleExceed(files, fileList) {
    if (files.length >= 1) {
      this.$notify({
        title: 'Warning',
        message: "Up to 1 file(s) are allowed",
        type: 'warning',
        duration: 3000
      });
      return false;
    }
  }

  handleBeforeUpload(file: any) {
    // File size limitation
    const isLt2M = file.size / 1024 / 1024 < 2;
    if (!isLt2M) {
      this.$notify({
          title: 'Warning',
          message: "File size must be less than 2Mb",
          type: 'warning',
          duration: 3000
      });
      return isLt2M;
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
      return false;
    }
  }
}