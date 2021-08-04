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

    //printedProperty: string[]= ["adOrganizationName", "name", "paymentCategory", "taxIdName", "location"];
    printedProperty: object[] = [
        {
            "prop" : "adOrganizationName",
            "label" : "Organization Name"
        },
        {
            "prop" : "name",
            "label" : "Name"
        },
        {
            "prop" : "paymentCategory",
            "label" : "Payment Category"
        },
        {
            "prop" : "taxIdName",
            "label" : "Tax Name"
        },
        {
            "prop" : "location",
            "label" : "Location"
        }
    ]


    // get getSummary(){
    //     return this.model.summary;
    // }
    created(){
        this.loadApproveVendor();
    }

    getVerificationAge(row: any, column): string{

        let diff= moment.duration(moment().diff(moment(row.createdDate)));
        let diffDays= diff.asDays();
        let diffHour= diff.asHours();

        return `${Math.round(diffDays)} Days and ${Math.round(diffHour%24)} Hour`;``
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
            // this.vendors = res.data.map((d: any) => {
            //     d.age= this.getVerificationAge(d);
            //     return d;
            // });
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

    sort(): Array<any> {
        const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.propOrder !== 'id') {
          result.push('id');
        }
        return result;
    }

    selectVendor(vendor: any){
        this.vendor= vendor;
        let convertedVendor= (this.convertVendor());
        this.summary= JSON.stringify(convertedVendor);
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

    private convertVendor(): any[]{
        let res: any[]= new Array() ;

        let prop: string[]= this.printedProperty.map(p => String(p["prop"]));
        Object.entries(this.vendor).forEach((k, v) => {
            let f: object= lodash.find(this.printedProperty, [ "prop", String(k[0])]);
            if(!lodash.isEmpty(f)){
                res.push(k);
            }
        })

        return res;
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

    viewDocument(){

        let targetUrl: string= '/supplier-management/suppliers';
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
                // console.log("Response data : " ,res.data);

                // this.pdfResource= res.data;

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