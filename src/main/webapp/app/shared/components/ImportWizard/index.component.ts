import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import UploadExcel from '@/shared/components/UploadExcel/index.vue';
import { generateRandomString } from '@/utils/string-utils';
import { isEmpty } from 'lodash';
import { Component, Inject, Vue, Watch } from 'vue-property-decorator';
import { Container, Draggable } from 'vue-smooth-dnd';

interface ICsvImportParameter {
  delimiter: string;
  fieldsMap: Record<string, ICsvFieldsMap>;
  maxRows: number;
  tableName: string;
}

interface ICsvFieldsMap {
  targetName: string;
  type: string;
}

const ImportWizardProps = Vue.extend({
  props: {
    tableName: String
  }
});

@Component({
  components: {
    UploadExcel,
    'drop-container': Container,
    'draggable-item': Draggable
  }
})
export default class ImportWizard extends ImportWizardProps {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  private dragging: boolean = false;

  private excludedColumns = new Set([
    'id', 'created_by', 'created_date', 'last_modified_by', 'last_modified_date',
    'uid'
  ]);

  loadingColumnDefinitions: boolean = false;
  columnDefinitions: any[] = [];

  csv: ICsvImportParameter = {
    delimiter: ',',
    fieldsMap: {},
    maxRows: -1,
    tableName: null
  };

  delimiterOptions = [
    { id: 1, label: 'Comma', value: ',' },
    { id: 2, label: 'Semicolon', value: ';'}
  ];

  tableData: any[] = [];
  tableHeaders: any[] = [];

  @Watch('tableName')
  onTableNameChanged(tableName: string) {
    this.$set(this.csv, 'tableName', tableName);
  }

  created() {
    this.onTableNameChanged(this.tableName);
  }

  beforeUpload(file: File) {
    const isLt1M = file.size / 1024 / 1024 < 100
    if (isLt1M) {
      return true
    }
    this.$message({
      message: 'Please do not upload files larger than 100m in size.',
      type: 'warning'
    })
    return false
  }

  onDropToHeader(headerIndex: number, result: any) {
    console.log('header column index', headerIndex);

    this.tableHeaders = this.tableHeaders.map((header, index) => {
      if (index === headerIndex) {
        header.targetColumns = this.applyDrag(header.targetColumns, result);
        header.highlight = false;
      }
      return header;
    });
    
    this.dragging = false;
  }

  onDropToList(result: any) {
    this.columnDefinitions = this.applyDrag(this.columnDefinitions, result);
  }

  onFileSet({ results, headers }: { results: any[], headers: string[]}) {
    this.tableData = results
    this.tableHeaders = headers.map(header => {
      let targetColumn = this.columnDefinitions.find(def => {
        const colName = def.name === 'value' ? 'code' : def.name;
        return colName === header.toLowerCase();
      });

      if (targetColumn) {
        this.columnDefinitions.splice(this.columnDefinitions.indexOf(targetColumn), 1);
      }

      return {
        id: generateRandomString(10),
        highlight: false,
        name: header,
        targetColumns: [].concat(targetColumn || [])
      };
    });
  }

  highlightCell(index: number, highlight: boolean = true) {
    if (this.dragging) {
      console.log('over', index);
      // Highlight the hovered column.
      const newHeader = {...this.tableHeaders[index]};
      newHeader.highlight = highlight;
      this.tableHeaders.splice(index, 1, newHeader);
    }
  }

  retrieveTabTree(id: number) {
    this.loadingColumnDefinitions = true;
    this.commonService(`/api/ad-tabs/tree/${id}`)
      .retrieve()
      .then(res => {
        this.columnDefinitions = this.mapColumnDefinition(res.data);
      })
      .catch(err => {
        console.log('Failed getting column defs.', err);
        this.$message({
          message: 'Failed getting the column definitions',
          type: 'error'
        });
      })
      .finally(() => {
        this.loadingColumnDefinitions = false;
      });
  }

  private mapColumnDefinition(item: Record<string, any>, prefix: string = '') {
    let defs = [];
    for (const key in item) {
      if (key === 'tableName' || key === 'children') {
        continue;
      }

      const value = item[key];

      if (key.includes('@')) {
        const linkedDefs = this.mapColumnDefinition(value, key);
        defs = defs.concat(linkedDefs);
      } else {
        if (this.excludedColumns.has(key)) {
          continue;
        }

        let name = key;
        let label = key;

        if (prefix) {
          name = `${prefix}.${key}`;
          label = `<em class="linked-table">${prefix.split('@')[1]}</em>_${key}`;
        }

        defs.push({
          id: generateRandomString(10),
          name,
          label,
          type: value
        });
      }
    }
    return defs;
  }

  getHeaderPayload(headerIndex: number, mappedTargetColumnIndex: number) {
    return this.tableHeaders[headerIndex].targetColumns[mappedTargetColumnIndex];
  }

  getListPayload(index: number) {
    return this.columnDefinitions[index];
  }

  reset() {
    this.columnDefinitions = [];
    this.tableData = [];
    this.tableHeaders = [];
    this.csv.fieldsMap = {};
  }

  shouldHeaderAcceptDrop(source: any, payload: any) {
    return true;
  }

  shouldListAcceptDrop(source: any, payload: any) {
    return true
  }

  submit() {
    for (const [index, tableHeader] of this.tableHeaders.entries()) {
      if (tableHeader.targetColumns.length) {
        const targetColumn = tableHeader.targetColumns[0];

        this.csv.fieldsMap[`${index}`] = {
          targetName: targetColumn.name,
          type: targetColumn.type
        };
      }
    }

    if (isEmpty(this.csv.fieldsMap)) {
      this.$message({
        message: 'Please create mapping between the source and target columns.',
        type: 'error'
      });

      return;
    }


    const file: File = (<any>this.$refs.inputFile).file;
    const formData = new FormData();
    formData.append('file', file);
    formData.append('config', JSON.stringify(this.csv));
    this.commonService(null)
      .import(formData)
      .then(() => {
        this.$emit('submitted');
      })
      .catch(err => {
        this.$emit('error', err);
      });
  }
  
  private applyDrag(arr: any[], dragResult: any) {
    const { removedIndex, addedIndex, payload } = dragResult;

    console.log('removed: %s, added: %s, payload: %O', removedIndex, addedIndex, payload);
    if ((removedIndex === null && addedIndex === null) || removedIndex === addedIndex) {
      return arr;
    }

    const result = [...arr];
    let itemToAdd = payload;

    if (removedIndex !== null) {
      itemToAdd = result.splice(removedIndex, 1)[0];
    }

    if (addedIndex !== null) {
      result.splice(addedIndex, 0, itemToAdd);
    }

    return result;
  }
}