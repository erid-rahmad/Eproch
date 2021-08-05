import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import AccountService from '@/account/account.service';
import { ElTable } from 'element-ui/types/table';
import { Component, Inject, Mixins } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import { WindowStoreModule as windowStore } from "@/shared/config/store/window-store";
import lodash from 'lodash';
import moment from 'moment';
import axios from 'axios';

const getApproveVendor= "api/c-vendors";
const approveVendor= "api/c-vendors/approval/";


@Component
export default class VendorApproval extends Mixins(AccessLevelMixin){
    @Inject('accountService')
    private accountService: () => AccountService;

    @Inject('dynamicWindowService')
    private commonService: (baseApiUrl: string) => DynamicWindowService;

    loading: boolean=false;
    vendors: any[]= [];
    vendorDocuments: any[] = [];
    vendor: any={};
    openViewDocument: boolean= false;
    documentLoading: boolean= false;

    gridSchema = {
        defaultSort: {},
        emptyText: 'No Records Found',
        maxHeight: 500,
        height: 500
    };

    itemsPerPage = 10;
    queryCount: number = null;
    page = 1;
    previousPage = 1;
    propOrder = 'id';
    reverse = false;
    totalItems = 0;
    summary: string="";

    showSummary: boolean= false;
    formattedDate: string="";

    // summaryParams: any= [
    //     {
    //         "prop" : ""
    //     }
    // ]


    created(){
        this.loadApproveVendor();
    }

    getVerificationAge(row: any, column): string{

        let diff= moment.duration(moment().diff(moment(row.createdDate)));
        let diffDays= diff.asDays();
        let diffHour= diff.asHours();

        return `${Math.round(diffDays)} Days and ${Math.round(diffHour%24)} Hour`;``
    }

    getVendorSummary(row: any, column): string{
        return this.generateVendorSummary(row);
    }

    getFormattedDate(){
        let formattedDate= moment(new Date()).format("MMMM DD, YYYY hh:mm:ss A");
        // console.log("FORMATTED DATE ", formattedDate);
        return  `${formattedDate} WIB (Approval Process)`;
    }

    loadApproveVendor(){
        this.loading = true;
        const paginationQuery = {
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        };

        this.commonService(getApproveVendor)
        .retrieve({
            criteriaQuery: this.updateCriteria([
                'documentStatus.equals=SMT'
            ]),
            paginationQuery
        })
        .then(res => {

            this.vendors= res.data;
            this.totalItems = Number(res.headers['x-total-count']);
            this.queryCount = this.totalItems;

            if (this.vendors.length) {
                this.setRow(this.vendors[0]);
            }
        })
        .catch(err => {
            console.error('Failed getting the record. %O', err);
            this.$message({
            type: 'error',
            message: err.detail || err.message
            });
        })
        .finally(() => {
            this.loading = false;
        });
    }

    private generateVendorSummary(model: any): string{
        let s: string[]= [];
        
        if(lodash.isEmpty(model)) return;

        if(!lodash.isEmpty(model.name)) s.push(model.name);
        if(!lodash.isEmpty(model.address)) s.push(model.address);
        if(!lodash.isEmpty(model.user)) s.push(`User : ${model.user}`);
        if(!lodash.isEmpty(model.userEmail)) s.push(`Email : ${model.userEmail}`);
        if(!lodash.isEmpty(model.businessClassification)) 
            s.push(`Business Classification : ${model.businessClassification}`);
        if(!lodash.isEmpty(model.type)) s.push(`Supplier Type : ${model.type}`);

        return s.join("; ");
    }

    sort(): Array<any> {
        const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.propOrder !== 'id') {
          result.push('id');
        }
        return result;
    }

    selectVendor(vendor: any){
        if(lodash.isEmpty(vendor)) return ;
        this.vendor= vendor;
        this.showSummary= false;
        this.formattedDate= this.getFormattedDate();

        this.retrieveVendorDetail(vendor)
        .then( e => {
            this.summary= this.generateVendorSummary(e);
        })
        .finally(()=> this.showSummary= true);
    }

    async retrieveVendorDetail(vendor: any): Promise<any>{

        return new Promise<any>(async (resolve, reject) => {

            const paginationQuery = {
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            };

            let vendorLocation= await this.commonService("api/c-vendor-locations")
                .retrieve({
                    criteriaQuery : this.updateCriteria([ 'cVendorId.equals=' + vendor.id ])
                    , paginationQuery})
                .then(res => {
                    if(res.data.length > 0){
                        let invoiceAddress= res.data.filter(a => a.invoiceAddress);
                        if(invoiceAddress.length == 0) invoiceAddress= res.data;
                        return {
                            address : invoiceAddress[0].locationName
                        }
                    }else {
                        return {}
                    }
                })

            let vendorUser= await this.commonService("api/ad-users")
                .retrieve({ 
                    criteriaQuery : this.updateCriteria([ 'cVendorId.equals=' + vendor.id ])
                    , paginationQuery })
                .then(res=> {
                    return {
                        user : res.data[0].name,
                        userEmail : res.data[0].email
                    }
                }).catch(e => {
                    reject(e);
                    console.log(e);
                    return {};
                }).finally(()=> {});

            let vendorBusinessClassification= await this.commonService('api/c-vendor-business-cats')
                .retrieve({ 
                    criteriaQuery : this.updateCriteria([ 'vendorId.equals='+ vendor.id ])
                    , paginationQuery})
                .then(res=> {
                    return {
                        businessClassification : JSON.stringify(res.data.map((r: any)=> {
                            return {
                                "Classification" : r.businessClassificationName,
                                "Business Category" : r.businessCategoryName,
                            }
                        }))
                    }
                }).catch(e => {
                    console.log(e);
                    reject(e);
                    return {}
                }).finally(()=>{});

            

            resolve({
                ...vendorLocation,
                ...vendor,
                ...vendorUser,
                ...vendorBusinessClassification
            });
        });
        

    }

    approve(approve: boolean){

        let msg= approve ? "Do you want to approve ?" : "Do you want to reject ?"; 
        this.$confirm(msg, {
            confirmButtonText: "Ok",
            cancelButtonText: "No"
        }).then(()=> {
            this.doApproval(approve);
        })
    }


    private doApproval(approve: boolean): void{
       
        this.loading=true;
        let approveModel= {
            "vendorId" : this.vendor.id,
            "approve" : approve
        };

        this.commonService(approveVendor)
            .update(approveModel)
            .then(() => {
                this.$message({
                    type: 'success',
                    message: approve ? 'Approval Complete !' : 'Rejection Complete'
                });
            })
            .catch(e => {
                console.log("ERROR ", e);
                this.$message({
                    type: 'error',
                    message: 'Process Fail !'
                });
            })
            .finally(()=>{
                this.loading=false;
                this.loadApproveVendor();
            });
    }

    private setRow(record: any) {
        (<ElTable>this.$refs.mainGrid).setCurrentRow(record);
    }

    async viewDocument(){

        //let targetUrl: string= '/supplier-management/suppliers';

        let menuId= await this.commonService("api/ad-menus")
            .retrieve({ criteriaQuery : this.updateCriteria([ 'path.equals=suppliers' ])})
            .then(res => {
                if(res.data.length > 0) return res.data[0].id ;
                return null;
            }).catch(e => {
                console.log(e);
            })
        
        let targetUrl: string= await this.commonService('/api/ad-menus/full-path').find(menuId);
        
        let filterQuery: string= 'id.equals=' + this.vendor.id;

        const timestamp = Date.now();
        windowStore.setWatchlistQuery(filterQuery)
        .then(() => {
          this.$router.push({
            path: targetUrl,
            query: {
              t: `${timestamp}`
            }
          });
        });
    }

    printDocument(){

        let viewDocumentResource: string= "api/c-vendors/pdf/" + this.vendor.id;
        
        let config: object= {
            method: "get",
            url: viewDocumentResource,
            responseType: 'blob',
            headers : {
                'Authorization': `Bearer ${this.accountService().token}`
            }
        }

        axios(config)
            .then(res=> {

                let uri= window.URL.createObjectURL(new Blob([res.data], { type : 'application/pdf'}));
                window.open(uri, "_blank");
                setTimeout(() => window.URL.revokeObjectURL(uri), 100);
            })
            .catch(e=> {
                console.log(e);
            })
            .finally(() => {
                console.log("request done");
            });
    }
}