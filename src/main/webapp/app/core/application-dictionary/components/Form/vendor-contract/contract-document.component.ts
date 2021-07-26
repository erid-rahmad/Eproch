import AccountService from '@/account/account.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { ElForm } from 'element-ui/types/form';
import { Component, Inject, Mixins, Vue } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import settings from '@/settings';
import AdInputLookup from '@/shared/components/AdInput/ad-input-lookup.vue';
import AdInputList from "@/shared/components/AdInput/ad-input-list.vue";


const baseApiClauseLine = 'api/c-clause-lines';
const baseApiContractDocument = 'api/m-contract-clause-documents';

const ContractDocumentProps = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
})

@Component({
  components: {
    AdInputLookup
  }
})
export default class ContractDocument extends Mixins(AccessLevelMixin, ContractDocumentProps) {


    list= [
      { id: 1, name: "Abby", sport: "basket" },
      { id: 2, name: "Brooke", sport: "foot" },
      { id: 3, name: "Courtenay", sport: "volley" },
      { id: 4, name: "David", sport: "rugby" }
    ]



  @Inject('accountService')
  private accountService: () => AccountService;

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loading: boolean = false;
  documentFormVisible: boolean = false;
  contractTextVisible:boolean=false;


  contractText='';
  x=0;
  documents: any[] = [];
  clauses:any[]=[];
  clausesOption:any[]=[];
  Title='';


  get formSettings() {
    return settings.form;
  }

  get readOnly() {
    return this.data.documentStatus && this.data.documentStatus !== 'DRF';
  }


  created() {
    this.retrieveDocument(this.data.id);
  }

  addClause(){
    console.log("this x",this.x)
   this.x+=1;
    let clause={
      title:'',
      clause:'',
      clauseLine:'',
      no:this.x,
    }
    this.clauses.push(clause);
  }

  deleteClause(row) {
    this.clauses.splice(row.$index,1)
  }
  retrieveClauseLine(row){
    console.log("this row",row)
    this.commonService(baseApiClauseLine)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `clauseId.equals=${row.clause}`,
        ])
      })
      .then(res => {
        return this.clausesOption=res.data;
      })
      .catch(err => {
        console.log('Failed to retrieveClauseLine. %O', err);
        this.$message.error('Failed to retrieveClauseLine');
      })
      .finally(() => this.loading = false);

  }

  deleteRow(row) {
    this.documents.splice(row.$index,1)
    this.delleteDocContactLine(row.id);
  }

  delleteDocContactLine(Id){
    this.loading = true;
    this.commonService(baseApiContractDocument)
      .delete(Id)
      .then(data=>{
        this.retrieveDocument(this.data.id);
      })
      .catch(err => {
        console.error('Failed getting the record. %O', err);
        this.$message.error(err.detail || err.message);
      })
      .finally(() => this.loading = false);
  }

  retrieveDocument(contractId){
    this.commonService(baseApiContractDocument)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `contractId.equals=${contractId}`,
        ])
      })
      .then(res => {
        this.documents=res.data
      })
      .catch(err => {
        console.log('Failed to retrieveClauseLine. %O', err);
        this.$message.error('Failed to retrieveClauseLine');
      })
      .finally(() => this.loading = false);

  }

  addNew(){
    this.documentFormVisible = true,
      this.Title=null;
    this.clauses=[];
  }

  view(row){
    console.log("this row",row.description)
    this.contractText=row.description;
    this.contractTextVisible=true;

  }

  async saveDocument() {
    let text ='';

    function compare(a, b) {
      // Use toUpperCase() to ignore character casing
      const bandA = a.no;
      const bandB = b.no;
      let comparison = 0;
      if (bandA < bandB) {
        comparison = 1;
      } else if (bandA > bandB) {
        comparison = -1;
      }
      return comparison;
    }
    await this.clauses.sort(compare);

    await this.clauses.forEach(value => {
      let paragraph='<p>'+value.clauseLine+'</p>';
      text=paragraph+text+'<p>';
    })

     let document= {
      name:this.Title,
      description:text,
       contractId:this.data.id,
       adOrganizationId:this.data.adOrganizationId,
       active:true

    }

    await this.documents.push(document);
    this.documentFormVisible = false;
  }

  save(){
    console.log("documentts",this.documents)
    this.documents.forEach(value => {
      this.commonService(baseApiContractDocument)
        .create(value)
        .then(res => {
          console.log("this res.data",res.data)
          this.retrieveDocument(this.data.id);
        })
        .catch(err => {
          console.error('Failed to save the contract', err);
          this.$message.error('Failed to save the contract');
        })
        .finally(() => this.loading = false);
    })

  }


}
