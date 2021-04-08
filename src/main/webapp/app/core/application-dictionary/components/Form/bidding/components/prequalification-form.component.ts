import { Component, Vue } from "vue-property-decorator";

const PrequalificationFormProps = Vue.extend({
  props: {
    readOnly: Boolean
  }
});

@Component
export default class PrequalificationForm extends PrequalificationFormProps {
  methods = [
    {
      id: 1,
      name: 'Metode Prakualifikasi 2021'
    }
  ];

  requirements = [
    {
      code: 'O',
      name: 'Optional'
    },
    {
      code: 'M',
      name: 'Required'
    }
  ]

  formData = {
    method: 1,
    criteria: [
      {
        id: 1,
        name: 'Pengelolaan K3L',
        subCriteria: [
          {
            id: 1,
            name: 'Faktor Utama',
            questions: [
              {
                question: 'Policy Statement - apakah perusahaan memiliki kebijakan K3L dalam menjalankan usahanya?',
                requirement: 'M'
              },
              {
                question: 'Emergency Response Procedures - apakah perusahaan memiliki prosedur tanggap darurat?',
                requirement: 'M'
              },
              {
                question: 'Basic Safety Rules - apakah perusahaan memiliki peraturan dasar keselamatan kerja?',
                requirement: 'M'
              }
            ]
          },
          {
            id: 2,
            name: 'Faktor Pendukung',
            questions: [
              {
                question: 'Professional Safety Support - Bagaimana penanganan/pengelolaan professional safety support? ',
                requirement: 'M'
              },
              {
                question: 'Enviromental - Sejauh mana perusahaan anda mengelola kebijakan tentang lingkungan kerja?',
                requirement: 'M'
              }
            ]
          }
        ]
      },
      {
        id: 2,
        name: 'Organisasi dan Manajemen',
        subCriteria: [
          {
            id: 3,
            name: 'Organisasi dan Manajemen',
            questions: [
              {
                question: 'Apakah pengurus telah menetapkan struktur organisasi perusahaan?',
                requirement: 'M'
              },
              {
                question: 'Apakah pengurus menetapkan kebijakan pengelolaan usaha dan pengendalian kegiatan usaha perusahaan?',
                requirement: 'M'
              }
            ]
          }
        ]
      }
    ]
  };
}