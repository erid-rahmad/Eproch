import AccountService from '@/account/account.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { ElForm } from 'element-ui/types/form';
import { Component, Inject, Mixins, Vue } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import settings from '@/settings';
import AdInputLookup from '@/shared/components/AdInput/ad-input-lookup.vue';
import createClause from './create-clause.vue'
import draggable from "vuedraggable";
import Sortable from "sortablejs";



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

// @ts-ignore
@Component({
  components: {
    AdInputLookup,createClause,draggable,
    Sortable
  }
})
export default class ContractDocument extends Mixins(AccessLevelMixin, ContractDocumentProps) {

  @Inject('accountService')
  private accountService: () => AccountService;

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loading: boolean = false;
  documentFormVisible: boolean = false;
  contractTextVisible:boolean=false;
  createClausa:boolean=false;


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

  headers= [
    { text: 'ID', value: 'id' },
    { text: 'Subject', value: 'subject' },
    { text: 'Assigned To', value: 'assignedTo' },
    { text: 'Date', value: 'date' },
    { text: 'Status', value: 'status' }
  ]


  created() {
    this.retrieveDocument(this.data.id);
    this.testing();
  }

  move(row){
    console.log("this row",row)
    function arraymove(arr, fromIndex, toIndex) {
      var element = arr[fromIndex];
      arr.splice(fromIndex, 1);
      arr.splice(toIndex, 0, element);
    }
  }

  async testing() {
    console.log("this header", this.headers)

    function arraymove(arr, fromIndex, toIndex) {
      var element = arr[fromIndex];
      arr.splice(fromIndex, 1);
      arr.splice(toIndex, 0, element);
    }

    await arraymove(this.headers, 1, 3);
    console.log("this header 2", this.headers)

  }

  addClause(){
    console.log("this x",this.x)
   this.x+=1;
    let clause={
      title:'',
      clause:'',
      clauseLine:'',
      no:this.x,
      list: [
      ],
    }
    this.clauses.push(clause);
  }
  Export2Word(element, filename = ''){

    var preHtml = "<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:w='urn:schemas-microsoft-com:office:word' xmlns='http://www.w3.org/TR/REC-html40'><head><meta charset='utf-8'><title>Export HTML To Doc</title></head><body>";
    var postHtml = "</body></html>";
    var html = element;

    var blob = new Blob(['\ufeff', html], {
      type: 'application/msword'
    });

    // Specify link url
    var url = 'data:application/vnd.ms-word;charset=utf-8,' + encodeURIComponent(html);

    // Specify file name
    filename = filename?filename+'.doc':'document.doc';

    // Create download link element
    var downloadLink = document.createElement("a");

    document.body.appendChild(downloadLink);

    if(navigator.msSaveOrOpenBlob ){
      navigator.msSaveOrOpenBlob(blob, filename);
    }else{
      // Create a link to the file
      downloadLink.href = url;

      // Setting the file name
      downloadLink.download = filename;

      //triggering the function
      downloadLink.click();
    }
    document.body.removeChild(downloadLink);
  }



  addSubClause(row){
    this.x+=1;
    console.log("this row",row);
    let satu =
      { no:this.x,clauseLine: "" }
    row.list.push(satu)
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
          // `clauseId.equals=${row.clause}`,
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
    row.id ? this.delleteDocContactLine(row.id):null;
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
    this.Export2Word(row.description,row.name)
    // this.contractTextVisible=true;

  }



  async saveDocument() {
    let text ='';
    const data= this.clauses.reverse();
    data.forEach( value => {
      const data=value.list.reverse();
       data.forEach(data=>{
        let paragraph = '\n' + data.clauseLine + '\n';
        text = paragraph + text;
      })
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
    this.save();
  }

  async addDocument(value) {
    console.log("this valuse",value)
    let document = {
      name: value.title,
      description: value.contract,
      contractId: this.data.id,
      adOrganizationId: this.data.adOrganizationId,
      active: true
    }

    await this.documents.push(document);
    this.save();
    this.createClausa=false;
  }

  cancle(){
    this.createClausa=false;
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
