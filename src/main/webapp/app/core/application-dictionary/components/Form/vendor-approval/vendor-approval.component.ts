import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { ElTable } from 'element-ui/types/table';
import { Component, Inject, Mixins } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import VendorEvaluationDetail from './detail.vue';
import { AccountStoreModule } from '@/shared/config/store/account-store';

const getApproveVendor= "api/c-vendors";
const approveVendor= "api/c-vendors/approval/"
@Component({
    components : {

    }
})
export default class VendorApproval extends Mixins(AccessLevelMixin){
    @Inject('dynamicWindowService')
    private commonService: (baseApiUrl: string) => DynamicWindowService;

    loading: boolean=false;
    vendors: any[]= [];
    vendor: any={};

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

    // get getSummary(){
    //     return this.model.summary;
    // }
    created(){
        this.loadApproveVendor();
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
            this.vendors = res.data;
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
        console.log(vendor);
        this.summary= JSON.stringify(vendor);
        this.vendor= vendor;
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
        //test this

        // this.$message({
        //     type: 'success',
        //     message: 'Approval Complete !'
        // });
        // return ;
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

    }

    printDocument(){

    }
}