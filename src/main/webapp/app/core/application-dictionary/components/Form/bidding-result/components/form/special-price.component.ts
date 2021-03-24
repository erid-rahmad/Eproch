import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../../../ContextVariableAccessor";
import Vue from 'vue';
import Component from 'vue-class-component';
import { ElForm } from 'element-ui/types/form';

const SpecialPriceProps = Vue.extend({
  props: {

    specialPrices: {
      type: Array,
      default: () => {
        return [];
      }
    },

  }
})

@Component
export default class SpecialPrice extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, SpecialPriceProps) {

  rules = {
    min: [
      { required: true, message: 'Please fill qty Min'},
    ],
    max: [
      { required: true, message: 'Please fill qty Max'},
    ],
    price: [
      { required: true, message: 'Please fill Price'},
    ]
  };

  //private list = [];
  private listLoading = true;
  private listQuery = {
    page: 1,
    limit: 10
  }

  private form: any = {};

  public dialogConfirmationVisible: boolean = false;
  editable: boolean = false;

  private dialogType = "";
  private dialogTitle = "";
  private dialogMessage = "";
  private dialogButton = "";
  private dialogButtonType = "";
  private dialogButtonIcon = "";

  created() {
    this.getList()
  }

  private async getList() {
    this.listLoading = true
		//this.specialPrices = [];

    //const { data } = await getArticles(this.listQuery)
    /*
    const items = {}
    this.list = items.map((v: any) => {
      this.$set(v, 'edit', false) // https://vuejs.org/v2/guide/reactivity.html
      v.originalTitle = v.title // will be used when user click the cancel botton
      return v
    })
    */

    // Just to simulate the time of the request
    setTimeout(() => {
      this.listLoading = false
    }, 0.5 * 1000)
  }

  private cancelEdit(row: any) {
    row.title = row.originalTitle
    row.edit = false
    this.$message({
      message: 'The title has been restored to the original value',
      type: 'warning'
    })
  }

  public addRow() {

      this.dialogType = 'add';
      this.dialogTitle = "Special Price";
      this.dialogMessage = "";
      this.dialogButton = "Save";
      this.dialogButtonType = "primary";
      this.dialogButtonIcon = "el-icon-check";

      //console.log(key + row + index);

      this.dialogConfirmationVisible = true;

  }

  saveRow(){
    this.specialPrices.push(this.form);
    this.dialogConfirmationVisible = false;
    this.form = {};
    //(this.$refs.formSpecialPrice as ElForm).clearValidate();
  }

  removeRow(row: any, index: number) {
    this.specialPrices.splice(index, 1);
  }



}
