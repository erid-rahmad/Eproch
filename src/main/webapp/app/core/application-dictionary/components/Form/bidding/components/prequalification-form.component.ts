import { Component, Vue, Watch ,Inject} from "vue-property-decorator";
import { Mixins } from 'vue-property-decorator';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';

const PrequalificationFormProps = Vue.extend({
  props: {
    // readOnly: Boolean,
    pickrow: {
      type: Object,
      default: () => {}
    } ,
  }
});

@Component
export default class PrequalificationForm extends Mixins(AccessLevelMixin, PrequalificationFormProps)  {

  testing() {
    console.log("help");
    
  }

  mainForm = {};
  

  testing2(row) {
    console.log(row);    
  }

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;
  private evaluationMethodCriteria: any = {};
  requerment: any = {};

  mounted() {
    console.log("this.pickrow",this.pickrow);    
    this.getEvaluationMethodCriteria(this.pickrow.id);
    
  }

  @Watch('pickrow')
  updatedata() {
    console.log("this.pickrow",this.pickrow);    
    this.getEvaluationMethodCriteria(this.pickrow.id);    
  }
  
  @Watch('evaluationMethodCriteria',{deep:true})
  updatedataevaluationMethodCriteria(value) {
    console.log("change",value);   
    }
    
  private getEvaluationMethodCriteria(lineId) {
    this.commonService('/api/c-evaluation-method-criteria')
      .retrieve({
        criteriaQuery: [
          `evaluationMethodLineId.equals=${lineId}`
          
        ],
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.evaluationMethodCriteria = res.data;
        console.log("this.evaluationMethodCriteria", this.evaluationMethodCriteria);
      });
  }




  // methods = [
  //   {
  //     id: 1,
  //     name: 'Metode Prakualifikasi 2021'
  //   }
  // ];




  // requirements = [
  //   {
  //     code: 'O',
  //     name: 'Optional'
  //   },
  //   {
  //     code: 'M',
  //     name: 'Required'
  //   }
  // ]
 

  // formData = {
  //   method: 1,
  //   criteria: [
  //     {
  //       id: 1,
  //       name: 'Pengelolaan K3L',
  //       subCriteria: [
  //         {
  //           id: 1,
  //           name: 'Faktor Utama',
  //           questions: [
  //             {
  //               question: 'Policy Statement - apakah perusahaan memiliki kebijakan K3L dalam menjalankan usahanya?',
  //               requirement: 'M'
  //             },
  //             {
  //               question: 'Emergency Response Procedures - apakah perusahaan memiliki prosedur tanggap darurat?',
  //               requirement: 'M'
  //             },
  //             {
  //               question: 'Basic Safety Rules - apakah perusahaan memiliki peraturan dasar keselamatan kerja?',
  //               requirement: 'M'
  //             }
  //           ]
  //         },
  //         {
  //           id: 2,
  //           name: 'Faktor Pendukung',
  //           questions: [
  //             {
  //               question: 'Professional Safety Support - Bagaimana penanganan/pengelolaan professional safety support? ',
  //               requirement: 'M'
  //             },
  //             {
  //               question: 'Enviromental - Sejauh mana perusahaan anda mengelola kebijakan tentang lingkungan kerja?',
  //               requirement: 'M'
  //             }
  //           ]
  //         }
  //       ]
  //     },
  //     {
  //       id: 2,
  //       name: 'Organisasi dan Manajemen',
  //       subCriteria: [
  //         {
  //           id: 3,
  //           name: 'Organisasi dan Manajemen',
  //           questions: [
  //             {
  //               question: 'Apakah pengurus telah menetapkan struktur organisasi perusahaan?',
  //               requirement: 'M'
  //             },
  //             {
  //               question: 'Apakah pengurus menetapkan kebijakan pengelolaan usaha dan pengendalian kegiatan usaha perusahaan?',
  //               requirement: 'M'
  //             }
  //           ]
  //         }
  //       ]
  //     }
  //   ]
  // };
}